package com.ctgu.audit;

import java.util.HashMap;
import java.util.Map;

/**
 * @version:1.0
 * @Description: mysql数据类型与java类型转换
 */
public class JavaToMysqlType
{
	// javaType --> mysqlType
	public static Map<String, String> typeMap = new HashMap<>();

	static
	{
		typeMap.put("class java.lang.String", MySqlTypeConstant.VARCHAR);
		typeMap.put("class java.math.BigInteger", MySqlTypeConstant.BIGINT);
		typeMap.put("class java.math.BigDecimal", MySqlTypeConstant.DECIMAL);
		typeMap.put("class java.sql.Date", MySqlTypeConstant.DATE);
		typeMap.put("class java.util.Date", MySqlTypeConstant.DATE);
		typeMap.put("class java.sql.Timestamp", MySqlTypeConstant.DATETIME);
		typeMap.put("class java.sql.Time", MySqlTypeConstant.TIME);

		typeMap.put("class java.lang.Byte", MySqlTypeConstant.TINYINT);
		typeMap.put("class java.lang.Short", MySqlTypeConstant.SMALLINT);
		typeMap.put("class java.lang.Integer", MySqlTypeConstant.INT);
		typeMap.put("class java.lang.Long", MySqlTypeConstant.BIGINT);
		typeMap.put("class java.lang.Float", MySqlTypeConstant.FLOAT);
		typeMap.put("class java.lang.Double", MySqlTypeConstant.DOUBLE);
		typeMap.put("class java.lang.Character", MySqlTypeConstant.CHAR);
		typeMap.put("class java.lang.Boolean", MySqlTypeConstant.TINYINT);

		typeMap.put("byte", MySqlTypeConstant.TINYINT);
		typeMap.put("short", MySqlTypeConstant.SMALLINT);
		typeMap.put("int", MySqlTypeConstant.INT);
		typeMap.put("long", MySqlTypeConstant.BIGINT);
		typeMap.put("float", MySqlTypeConstant.FLOAT);
		typeMap.put("double", MySqlTypeConstant.DOUBLE);
		typeMap.put("char", MySqlTypeConstant.VARCHAR);
		typeMap.put("boolean", MySqlTypeConstant.BIT);
	}

}
