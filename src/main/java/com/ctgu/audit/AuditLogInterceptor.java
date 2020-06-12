package com.ctgu.audit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.ctgu.audit.annotation.Audited;
import com.ctgu.entity.User;
import com.ctgu.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用Mybatis拦截器实现数据审计<br>
 * 默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：<br>
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)<br>
 * ParameterHandler (getParameterObject, setParameters)<br>
 * ResultSetHandler (handleResultSets, handleOutputParameters)<br>
 * StatementHandler (prepare, parameterize, batch, update, query)<br>
 */
@Intercepts({//
		@Signature(//
				type = Executor.class, // 表示拦截的接口类型, 这里的 Executor 是负责执行底层映射语句的内部对象
				method = "update", // 插件将会拦截在 Executor 实例中所有的update方法调用
				args = { MappedStatement.class, Object.class }) //
})
@Slf4j
public class AuditLogInterceptor implements Interceptor
{
	private Properties properties = new Properties();

	/**
	 * interceptor方法用于处理代理类的执行; <br>
	 * 通过判断实体类是否有注解@Audited来决定是否拦截并将拦截的数据存入审计表中
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable
	{
		// 获取当前用户ID
		// SysUser token = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Object target = invocation.getTarget();// 被代理对象
		Method method = invocation.getMethod();// 代理方法
		Object[] args = invocation.getArgs();// 方法参数

		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object targetObject = args[1];

		// 没有审计注解的类，不拦截
		if (targetObject.getClass()
				.getAnnotation(Audited.class) == null)
		{
			return invocation.proceed();
		}

		Object result = null;
		String typeName = mappedStatement.getSqlCommandType()
				.name();
		if ("insert".equalsIgnoreCase(typeName))
		{
			doAudit(targetObject, OperationType.getName(0));
			result = invocation.proceed();
		}
		else if ("update".equalsIgnoreCase(typeName))
		{
			result = invocation.proceed();
			doAudit(targetObject, OperationType.getName(1));
		}
		else if ("delete".equalsIgnoreCase(typeName))
		{
			doAudit(targetObject, OperationType.getName(2));
			result = invocation.proceed();
		}
		else
		{
			// 执行真正的操作
			result = invocation.proceed();
		}

		return result;
	}

	/**
	 * 执行审计过程
	 */
	private void doAudit(Object targetObject, String operationType) throws Exception
	{
		// 1、检查并自动建审计表
		// 可以注释掉这段代码，改成自己手动建表
		String sql = ObjectToTableUtil.checkAndCreateTable(targetObject);
		if (sql != null)
		{
			log.info("{}对应的审计表不存在，自动建表过程已被执行", targetObject.getClass()
					.getSimpleName());
		}
		log.info("{}对应的审计表已存在，即将插入审计数据...", targetObject.getClass()
				.getSimpleName());

		// 2、数据入库
		doDB(targetObject, operationType);
	}

	/**
	 * 审计数据保存到数据库
	 */
	private void doDB(Object targetObject, String operationType) throws Exception
	{
		String tableName = ObjectToTableUtil.getTableName(targetObject);

		StringBuffer sb = new StringBuffer();
		sb.append("insert into ")
				.append(tableName)
				.append("(");
		// 字段声明
		Map<String, Object> propertyMap = ReflectionUtil.getAllPropertiesMap(targetObject);
		for (Entry<String, Object> entry : propertyMap.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();
			log.info("key={}, value={}", key, value);
			String columnName = StringUtils.camelToUnderline((String) key);
			sb.append(columnName)
					.append(",");
		}
		// 操作时间让Mysql自动设置
		sb.append("version, operation_type, operator_id, operator_name)");

		// 属性值占位符声明
		sb.append("values(");
		for (Entry<String, Object> entry : propertyMap.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append(" ? ")
					.append(",");
		}
		sb.append(" ? , ? , ? , ? );");
		String sql = sb.toString();

		// 设置sql占位符对应的参数
		List<Object> params = new ArrayList<>();
		for (Entry<String, Object> entry : propertyMap.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append("?")
					.append(",");
			Object columnValue = ReflectionUtil.getFieldValueByFieldName(targetObject, (String) key);
			params.add(columnValue);
		}
		// User user=getUsers(token);
		// params.add(generateVersionNumber(user));
		// params.add(user.getId());
		// params.add(user.getUserName());

		params.add("1");		// 版本号，这里的版本号应该是递增的
		params.add(operationType);		// 操作类型
		params.add("1");// 操作人id，即是谁改了数据
		params.add("admin");		// 操作人名称
		// 操作时间不用设置

		JDBCUtil jdbcUtil = JDBCUtil.getInstance();
		jdbcUtil.getConnection();
		jdbcUtil.updateByPreparedStatement(sql, params);
		jdbcUtil.releaseConn();
	}

	/**
	 * plugin方法用于某些处理器(Handler)的构建过程
	 */
	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	/**
	 * setProperties方法用于拦截器属性的设置。
	 */
	@Override
	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}

}
