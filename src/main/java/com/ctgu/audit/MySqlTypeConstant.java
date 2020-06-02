package com.ctgu.audit;

/**
 * 用于配置Mysql数据库中类型，并且该类型需要设置几个长度 <br>
 * 这里配置多少个类型决定了，创建表能使用多少类型 <br>
 * 例如：varchar(1) decimal(5,2) datetime
 */
public class MySqlTypeConstant
{
	public static final String INT = "int(11)";

	public static final String VARCHAR = "varchar(255)";

	public static final String BINARY = "binary";

	public static final String CHAR = "char(255)";

	public static final String BIGINT = "bigint(20)";

	public static final String BIT = "bit";

	public static final String TINYINT = "tinyint(2)";

	public static final String SMALLINT = "smallint(6)";

	public static final String MEDIUMINT = "mediumint(9)";

	public static final String DECIMAL = "decimal(10,2)";

	public static final String DOUBLE = "double";

	public static final String TEXT = "text";

	public static final String MEDIUMTEXT = "mediumtext";

	public static final String LONGTEXT = "longtext";

	public static final String DATETIME = "datetime";

	public static final String TIMESTAMP = "timestamp";

	public static final String DATE = "date";

	public static final String TIME = "time";

	public static final String FLOAT = "float";

	public static final String YEAR = "year";

	public static final String BLOB = "blob";

	public static final String LONGBLOB = "longblob";

	public static final String MEDIUMBLOB = "mediumblob";

	public static final String TINYTEXT = "tinytext";

	public static final String TINYBLOB = "tinyblob";
}
