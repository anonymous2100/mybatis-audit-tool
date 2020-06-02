//package com.ctgu.config;
//
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//
///**
// * Druid监控web配置 http://localhost:8823/druid/index.html 用户名admin 密码admin
// * 
// * @author lh2
// */
//@Configuration
//public class DruidConfig
//{
//	private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);
//
//	private static final String DB_PREFIX = "spring.datasource";
//
//	@Bean
//	public ServletRegistrationBean druidServlet()
//	{
//		ServletRegistrationBean reg = new ServletRegistrationBean();
//		reg.setServlet(new StatViewServlet());
//		// 登录URL http://localhost:8080/d/login.html
//		reg.addUrlMappings("/druid/*");
//		// 设置白名单
//		reg.addInitParameter("allow", "*");
//		// 设置黑名单
//		reg.addInitParameter("deny", "");
//		// 设置登录查看信息的账号密码.
//		reg.addInitParameter("loginUsername", "admin");
//		reg.addInitParameter("loginPassword", "admin");
//		// 是否能够重置数据 禁用HTML页面上的“Reset All”功能
//		reg.addInitParameter("resetEnable", "false");
//
//		return reg;
//	}
//
//	@Bean
//	public FilterRegistrationBean filterRegistrationBean()
//	{
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//		filterRegistrationBean.addUrlPatterns("/*");
//		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//		return filterRegistrationBean;
//	}
//
//	@Bean(destroyMethod = "close", initMethod = "init")
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource druidDataSource()
//	{
//		return new DruidDataSource();
//	}
//
//	// 解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
//	@Component
//	@ConfigurationProperties(prefix = DB_PREFIX)
//	class IDataSourceProperties
//	{
//		private String url;
//		private String username;
//		private String password;
//		private String driverClassName;
//		private int initialSize;
//		private int minIdle;
//		private int maxActive;
//		private int maxWait;
//		private int timeBetweenEvictionRunsMillis;
//		private int minEvictableIdleTimeMillis;
//		private String validationQuery;
//		private boolean testWhileIdle;
//		private boolean testOnBorrow;
//		private boolean testOnReturn;
//		private String filters;
//
//		private boolean poolPreparedStatements;
//		private int maxPoolPreparedStatementPerConnectionSize;
//		private String connectionProperties;
//
//		@Bean // 声明其为Bean实例
//		@Primary // 在同样的DataSource中，首先使用被标注的DataSource
//		public DataSource dataSource()
//		{
//			DruidDataSource datasource = new DruidDataSource();
//			datasource.setUrl(url);
//			datasource.setUsername(username);
//			datasource.setPassword(password);
//			datasource.setDriverClassName(driverClassName);
//
//			// configuration
//			datasource.setInitialSize(initialSize);
//			datasource.setMinIdle(minIdle);
//			datasource.setMaxActive(maxActive);
//			datasource.setMaxWait(maxWait);
//			datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//			datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//			datasource.setValidationQuery(validationQuery);
//			datasource.setTestWhileIdle(testWhileIdle);
//			datasource.setTestOnBorrow(testOnBorrow);
//			datasource.setTestOnReturn(testOnReturn);
//			datasource.setPoolPreparedStatements(poolPreparedStatements);
//			datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//			try
//			{
//				datasource.setFilters(filters);
//			}
//			catch (SQLException e)
//			{
//				System.err.println("druid configuration initialization filter: " + e);
//			}
//			datasource.setConnectionProperties(connectionProperties);
//			return datasource;
//		}
//
//		public String getUrl()
//		{
//			return url;
//		}
//
//		public void setUrl(String url)
//		{
//			this.url = url;
//		}
//
//		public String getUsername()
//		{
//			return username;
//		}
//
//		public void setUsername(String username)
//		{
//			this.username = username;
//		}
//
//		public String getPassword()
//		{
//			return password;
//		}
//
//		public void setPassword(String password)
//		{
//			this.password = password;
//		}
//
//		public String getDriverClassName()
//		{
//			return driverClassName;
//		}
//
//		public void setDriverClassName(String driverClassName)
//		{
//			this.driverClassName = driverClassName;
//		}
//
//		public int getInitialSize()
//		{
//			return initialSize;
//		}
//
//		public void setInitialSize(int initialSize)
//		{
//			this.initialSize = initialSize;
//		}
//
//		public int getMinIdle()
//		{
//			return minIdle;
//		}
//
//		public void setMinIdle(int minIdle)
//		{
//			this.minIdle = minIdle;
//		}
//
//		public int getMaxActive()
//		{
//			return maxActive;
//		}
//
//		public void setMaxActive(int maxActive)
//		{
//			this.maxActive = maxActive;
//		}
//
//		public int getMaxWait()
//		{
//			return maxWait;
//		}
//
//		public void setMaxWait(int maxWait)
//		{
//			this.maxWait = maxWait;
//		}
//
//		public int getTimeBetweenEvictionRunsMillis()
//		{
//			return timeBetweenEvictionRunsMillis;
//		}
//
//		public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis)
//		{
//			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
//		}
//
//		public int getMinEvictableIdleTimeMillis()
//		{
//			return minEvictableIdleTimeMillis;
//		}
//
//		public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis)
//		{
//			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
//		}
//
//		public String getValidationQuery()
//		{
//			return validationQuery;
//		}
//
//		public void setValidationQuery(String validationQuery)
//		{
//			this.validationQuery = validationQuery;
//		}
//
//		public boolean isTestWhileIdle()
//		{
//			return testWhileIdle;
//		}
//
//		public void setTestWhileIdle(boolean testWhileIdle)
//		{
//			this.testWhileIdle = testWhileIdle;
//		}
//
//		public boolean isTestOnBorrow()
//		{
//			return testOnBorrow;
//		}
//
//		public void setTestOnBorrow(boolean testOnBorrow)
//		{
//			this.testOnBorrow = testOnBorrow;
//		}
//
//		public boolean isTestOnReturn()
//		{
//			return testOnReturn;
//		}
//
//		public void setTestOnReturn(boolean testOnReturn)
//		{
//			this.testOnReturn = testOnReturn;
//		}
//
//		public boolean isPoolPreparedStatements()
//		{
//			return poolPreparedStatements;
//		}
//
//		public void setPoolPreparedStatements(boolean poolPreparedStatements)
//		{
//			this.poolPreparedStatements = poolPreparedStatements;
//		}
//
//		public int getMaxPoolPreparedStatementPerConnectionSize()
//		{
//			return maxPoolPreparedStatementPerConnectionSize;
//		}
//
//		public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize)
//		{
//			this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
//		}
//
//		public String getFilters()
//		{
//			return filters;
//		}
//
//		public void setFilters(String filters)
//		{
//			this.filters = filters;
//		}
//
//		public String getConnectionProperties()
//		{
//			return connectionProperties;
//		}
//
//		public void setConnectionProperties(String connectionProperties)
//		{
//			this.connectionProperties = connectionProperties;
//		}
//	}
//}
