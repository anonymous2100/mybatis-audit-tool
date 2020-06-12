package com.ctgu.audit;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import com.ctgu.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ObjectToTableUtil
 * @Description: 根据java对象名自动创建审计表的工具类
 * @author lh2
 * @date 2020年6月12日 下午5:45:50
 */
@Slf4j
public class ObjectToTableUtil
{

	/**
	 * 判断表结构是否存在，若不存在则自动创建
	 * 
	 * @param source
	 */
	public static String checkAndCreateTable(Object sourceObject)
	{
		String tableName = getTableName(sourceObject);

		boolean flag = checkTableExist(tableName);
		if (flag)
		{
			log.info("{}对应的审计表已经存在！", sourceObject.getClass()
					.getSimpleName());
			return null;
		}

		log.info("{}对应的审计表不存在，即将自动建表......", sourceObject.getClass()
				.getSimpleName());
		Map<String, Object> propertyMap;
		try
		{
			// 获得对象属性的键值对
			propertyMap = ReflectionUtil.getAllPropertiesMap(sourceObject);
			propertyMap.remove("serialVersionUID");
			log.info("属性的键值对为：{}", propertyMap.toString());

			// 拼接并执行建表sql语句1
			StringBuffer dropSB = new StringBuffer();
			String deleteTableSql = dropSB.append(" drop table if	exists ")
					.append(tableName)
					.append(";\n")
					.toString();
			JDBCUtil jdbcUtil = JDBCUtil.getInstance();
			jdbcUtil.getConnection();
			jdbcUtil.updateByPreparedStatement(deleteTableSql, null);

			// 拼接并执行建表sql语句2
			StringBuffer createSB = new StringBuffer();
			createSB.append("create table ")
					.append(tableName)
					.append("(\n");
			for (Entry<String, Object> entry : propertyMap.entrySet())
			{
				Object key = entry.getKey();
				Object value = entry.getValue();
				log.info("key={}, value={}", key, value);

				createSB.append(StringUtils.camelToUnderline((String) key))
						.append("  ");
				if (javaToJdbcType(sourceObject, key) == null)
				{
					createSB.append("varchar(255) ");
				}
				else
				{
					createSB.append(javaToJdbcType(sourceObject, key));
				}
				createSB.append(",\n");
			}
			createSB.append("version int(11) not null default 1 ")
					.append(",\n");
			createSB.append("operation_type varchar(64) not null default 'ADD' ")
					.append(",\n");
			createSB.append("operator_id int(11) ")
					.append(",\n");
			createSB.append("operator_name varchar(255) null default null ")
					.append(",\n");
			createSB.append("operation_time datetime not null default now() ");
			createSB.append("\n)")
					.append(";\n");
			String createSql = createSB.toString();

			jdbcUtil.updateByPreparedStatement(createSql, null);
			jdbcUtil.releaseConn();

			return deleteTableSql + createSql;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("建表过程出错：{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 得到自动创建的表名
	 * 
	 * @param source
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getTableName(Object source)
	{
		Class clazz = source.getClass();
		String classSimpleName = clazz.getSimpleName();
		String tableName = "t_" + classSimpleName.toLowerCase() + "_audit";

		return tableName;
	}

	/**
	 * mysql根据名称判断某张表是否存在，即是否已被创建
	 */
	private static boolean checkTableExist(String tableName)
	{
		JDBCUtil jdbcUtil = JDBCUtil.getInstance();
		Connection con = jdbcUtil.getConnection();
		ResultSet rs;
		try
		{
			rs = con.getMetaData()
					.getTables(null, null, tableName, null);
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * java类型转换为Mysql数据类型
	 * 
	 * @param sourceObject
	 *            java类
	 * @param sourceProperty
	 *            java类中的属性
	 */
	private static String javaToJdbcType(Object sourceObject, Object sourceProperty)
	{
		String typeDesc = getType(sourceObject, sourceProperty);
		if (typeDesc == null)
		{
			return null;
		}
		String jdbcType = JavaToMysqlType.typeMap.get(typeDesc);

		return jdbcType;
	}

	/**
	 * 根据java对象obj和对象obj的属性名称xx，得到属性xx的java类型yy
	 * 
	 * @param result
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static String getType(Object result, Object key)
	{
		Class clazz = result.getClass();

		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			Field field = fields[i];
			field.setAccessible(true);

			String fieldName = field.getName();
			String fieldType = field.getGenericType()
					.toString();
			if (key.equals(fieldName))
			{
				return fieldType;
			}
		}
		return null;
	}

}
