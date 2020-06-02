package com.ctgu.audit;

import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.ctgu.audit.annotation.Audited;

import lombok.extern.slf4j.Slf4j;

/**
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
		Object target = invocation.getTarget();
		Method method = invocation.getMethod();
		Object[] args = invocation.getArgs();

		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object targetObject = args[1];

		// 没有审计注解的类，不拦截
		if (targetObject.getClass()
				.getAnnotation(Audited.class) == null)
		{
			return invocation.proceed();
		}

		Object result = null;
		SqlCommandType type = mappedStatement.getSqlCommandType();
		switch (type)
		{
			case INSERT:
				doAudit(targetObject, OperationType.getName(0));
				result = invocation.proceed();
			case UPDATE:
				result = invocation.proceed();
				doAudit(targetObject, OperationType.getName(1));
				break;
			case DELETE:
				doAudit(targetObject, OperationType.getName(2));
				result = invocation.proceed();
				break;
			default:
				// 执行真正的操作
				result = invocation.proceed();
				break;
		}
		return result;
	}

	/**
	 * 执行审计过程
	 */
	private void doAudit(Object targetObject, String operationType) throws Exception
	{
		String sql = ObjectToTableUtil.checkAndCreateTable(targetObject);
		if (sql != null)
		{
			log.info("{}对应的审计表不存在，自动建表过程已被执行", targetObject.getClass()
					.getSimpleName());
		}

		doDB(targetObject, operationType);
	}

	/**
	 * 审计数据保存到数据库
	 */
	private void doDB(Object targetObject, String operationType) throws Exception
	{
		String tableName = ObjectToTableUtil.getTableName(targetObject);

		String tableSql = "insert into %s ";
		String fieldSql = "(xx  ,version, operation_type, operator_id, operator_name, operation_time) ";
		String valueSql = "values(xx,  1, operationType, 0, null , new Date())";

	}

	// plugin方法用于某些处理器(Handler)的构建过程
	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	// setProperties方法用于拦截器属性的设置。
	@Override
	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}

}
