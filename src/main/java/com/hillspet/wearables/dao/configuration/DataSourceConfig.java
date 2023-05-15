package com.hillspet.wearables.dao.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * It maps the database configuration from yaml file
 * 
 * @author vvodyaram
 *
 */
@Component
@ConfigurationProperties("datasource")
public class DataSourceConfig {
	private int acquireIncrement;
	private boolean testConnectionOnCheckin;
	private int minPoolSize;
	private int maxPoolSize;
	private int maxStatements;
	private int maxIdleTime;
	private boolean testConnectionOnCheckout;
	private int checkoutTimeout;
	private int idleConnectionTestPeriod;
	private int maxIdleTimeExcessConnections;
	private boolean forceSynchronousCheckins;
	private int numHelperThreads;
	private String driverClass;
	private String user;
	private String password;
	private boolean debugUnreturnedConnectionStackTraces;
	private String jdbcUrl;
	private int initialPoolSize;
	/**
	 * packagesToScan will hold list of packages which needs to be scanned for
	 */
	private List<String> packagesToScan;

	private String transactionManager;

	private String dataSourceName;

	private String sharedCacheMode;

	private String hibernateProperties;

	private String encodingFormat;

	private String schema;

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public int getNumHelperThreads() {
		return numHelperThreads;
	}

	public void setNumHelperThreads(int numHelperThreads) {
		this.numHelperThreads = numHelperThreads;
	}

	public boolean isForceSynchronousCheckins() {
		return forceSynchronousCheckins;
	}

	public void setForceSynchronousCheckins(boolean forceSynchronousCheckins) {
		this.forceSynchronousCheckins = forceSynchronousCheckins;
	}

	public boolean isTestConnectionOnCheckin() {
		return testConnectionOnCheckin;
	}

	public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
		this.testConnectionOnCheckin = testConnectionOnCheckin;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(int maxStatements) {
		this.maxStatements = maxStatements;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public boolean isTestConnectionOnCheckout() {
		return testConnectionOnCheckout;
	}

	public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
		this.testConnectionOnCheckout = testConnectionOnCheckout;
	}

	public int getCheckoutTimeout() {
		return checkoutTimeout;
	}

	public void setCheckoutTimeout(int checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}

	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public int getMaxIdleTimeExcessConnections() {
		return maxIdleTimeExcessConnections;
	}

	public void setMaxIdleTimeExcessConnections(int maxIdleTimeExcessConnections) {
		this.maxIdleTimeExcessConnections = maxIdleTimeExcessConnections;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean isDebugUnreturnedConnectionStackTraces() {
		return debugUnreturnedConnectionStackTraces;
	}

	public void setDebugUnreturnedConnectionStackTraces(boolean debugUnreturnedConnectionStackTraces) {
		this.debugUnreturnedConnectionStackTraces = debugUnreturnedConnectionStackTraces;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the jdbcUrl
	 */
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * @param jdbcUrl the jdbcUrl to set
	 */
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public List<String> getPackagesToScan() {
		return packagesToScan;
	}

	public void setPackagesToScan(List<String> packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

	public String getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(String transactionManager) {
		this.transactionManager = transactionManager;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getSharedCacheMode() {
		return sharedCacheMode;
	}

	public void setSharedCacheMode(String sharedCacheMode) {
		this.sharedCacheMode = sharedCacheMode;
	}

	public String getHibernateProperties() {
		return hibernateProperties;
	}

	public void setHibernateProperties(String hibernateProperties) {
		this.hibernateProperties = hibernateProperties;
	}

	public String getEncodingFormat() {
		return encodingFormat;
	}

	public void setEncodingFormat(String encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}
