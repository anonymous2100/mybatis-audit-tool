package com.ctgu.audit;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: JDBCUtil
 * @Description:JDBC工具类
 * @author lh2
 * @date 2020年6月12日 下午5:44:54
 */
@Slf4j
public class JDBCUtil
{
	// 表示定义数据库的用户名
	private static String USERNAME;
	// 定义数据库的密码
	private static String PASSWORD;
	// 定义数据库的驱动信息
	private static String DRIVER;
	// 定义访问数据库的地址
	private static String URL;
	// 定义数据库的链接
	private Connection connection;
	// 定义sql语句的执行对象
	private PreparedStatement pstmt;
	// 定义查询返回的结果集合
	private ResultSet resultSet;
	// 配置文件读取类
	private static Properties PROPS = new Properties();

	private static final JDBCUtil instance = new JDBCUtil();

	static
	{
		// 加载数据库配置信息，并给相关的属性赋值
		try
		{
			InputStream inputStream = JDBCUtil.class.getClassLoader()
					.getResourceAsStream("application-dev.properties");
			PROPS.load(inputStream);

			USERNAME = PROPS.getProperty("spring.datasource.username");
			PASSWORD = PROPS.getProperty("spring.datasource.password");
			DRIVER = PROPS.getProperty("spring.datasource.driver-class-name");
			URL = PROPS.getProperty("spring.datasource.url");

			log.info(USERNAME + "\n" + PASSWORD + "\n" + DRIVER + "\n" + URL);
		}
		catch (Exception e)
		{
			throw new RuntimeException("读取数据库配置文件异常！", e);
		}
	}

	private JDBCUtil()
	{
	}

	public static JDBCUtil getInstance()
	{
		return instance;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接
	 */
	public Connection getConnection()
	{
		try
		{
			Class.forName(DRIVER); // 注册驱动
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接
		}
		catch (Exception e)
		{
			throw new RuntimeException("get connection error!", e);
		}
		return connection;
	}

	/**
	 * 执行更新操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            执行参数
	 * @return 执行结果
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException
	{
		printRealSql(sql, params); // 打印真实 SQL 的函数

		boolean flag = false;
		int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		// 填充sql语句中的占位符
		if (params != null && !params.isEmpty())
		{
			for (int i = 0; i < params.size(); i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}

		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            执行参数
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findResult(String sql, List<?> params) throws SQLException
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty())
		{
			for (int i = 0; i < params.size(); i++)
			{
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next())
		{
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++)
			{
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null)
				{
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 在开发过程，SQL语句有可能写错，如果能把运行时出错的 SQL 语句直接打印出来，那对排错非常方便， 因为其可以直接拷贝到数据库客户端进行调试。
	 * 
	 * @param sql
	 *            SQL 语句，可以带有 ? 的占位符
	 * @param params
	 *            插入到 SQL 中的参数，可单个可多个可不填
	 * @return 实际 sql 语句
	 */
	public String printRealSql(String sql, List<Object> list)
	{
		Object[] params = list.toArray();

		if (params == null || params.length == 0)
		{
			log.info("PreparedStatement最终执行的SQL 语句为------------>\n {} \n", sql);

			return sql;
		}

		if (!match(sql, params))
		{
			log.info("SQL 语句中的占位符与参数个数不匹配。SQL：{}", sql);
			return null;
		}

		int cols = params.length;
		Object[] values = new Object[cols];
		System.arraycopy(params, 0, values, 0, cols);

		for (int i = 0; i < cols; i++)
		{
			Object value = values[i];
			if (value instanceof Date)
			{
				values[i] = "'" + value + "'";
			}
			else if (value instanceof String)
			{
				values[i] = "'" + value + "'";
			}
			else if (value instanceof Boolean)
			{
				values[i] = (Boolean) value ? 1 : 0;
			}
		}
		String statementSql = String.format(sql.replaceAll("\\?", "%s"), values);

		log.info("PreparedStatement最终执行的SQL 语句为------------>\n\n {} \n", statementSql);

		return statementSql;
	}

	/**
	 * ? 和参数的实际个数是否匹配
	 * 
	 * @param sql
	 *            SQL 语句，可以带有 ? 的占位符
	 * @param params
	 *            插入到 SQL 中的参数，可单个可多个可不填
	 * @return true 表示为 ? 和参数的实际个数匹配
	 */
	private static boolean match(String sql, Object[] params)
	{
		if (params == null || params.length == 0)
			return true; // 没有参数，完整输出

		Matcher m = Pattern.compile("(\\?)")
				.matcher(sql);
		int count = 0;
		while (m.find())
		{
			count++;
		}

		return count == params.length;
	}

	/**
	 * 释放资源
	 */
	public void releaseConn()
	{
		if (resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (pstmt != null)
		{
			try
			{
				pstmt.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (connection != null)
		{
			try
			{
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	//
	// public static void main(String[] args)
	// {
	// JDBCUtil jdbcUtil = new JDBCUtil();
	// jdbcUtil.getConnection();
	// try
	// {
	// List<Map<String, Object>> result = jdbcUtil.findResult("select * from t_user", null);
	// for (Map<String, Object> m : result)
	// {
	// System.out.println(m);
	// }
	// }
	// catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	// finally
	// {
	// jdbcUtil.releaseConn();
	// }
	// }
}
