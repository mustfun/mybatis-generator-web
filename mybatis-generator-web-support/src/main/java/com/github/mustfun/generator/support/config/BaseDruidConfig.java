package com.github.mustfun.generator.support.config;


/**
 * Created by dengzhiyuan on 2017/4/5.
 */
public abstract class BaseDruidConfig {

    private String name;
    private String connectionProperties;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private String validationQuery;
    private int validationQueryTimeout;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private long maxWait;
    private boolean useUnfairLock;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private long maxEvictableIdleTimeMillis;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private boolean removeAbandoned;
    private long removeAbandonedTimeoutMillis;
    private boolean logAbandoned;
    private int queryTimeout;
    private int transactionQueryTimeout;
    private String filters;

    public BaseDruidConfig() {
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getFilters() {
        return this.filters;
    }

    public String getConnectionProperties() {
        return this.connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public boolean isTestOnReturn() {
        return this.testOnReturn;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public boolean isUseUnfairLock() {
        return this.useUnfairLock;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public int getQueryTimeout() {
        return this.queryTimeout;
    }

    public int getTransactionQueryTimeout() {
        return this.transactionQueryTimeout;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRemoveAbandonedTimeoutMillis() {
        return this.removeAbandonedTimeoutMillis;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public long getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    public void setUseUnfairLock(boolean useUnfairLock) {
        this.useUnfairLock = useUnfairLock;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return this.maxPoolPreparedStatementPerConnectionSize;
    }

    public int getMaxActive() {
        return this.maxActive;
    }

    public boolean isLogAbandoned() {
        return this.logAbandoned;
    }

    public long getMaxWait() {
        return this.maxWait;
    }

    public String getValidationQuery() {
        return this.validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getValidationQueryTimeout() {
        return this.validationQueryTimeout;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return this.testWhileIdle;
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public int getInitialSize() {
        return this.initialSize;
    }

    public long getMaxEvictableIdleTimeMillis() {
        return this.maxEvictableIdleTimeMillis;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public void setRemoveAbandonedTimeoutMillis(long removeAbandonedTimeoutMillis) {
        this.removeAbandonedTimeoutMillis = removeAbandonedTimeoutMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public boolean isPoolPreparedStatements() {
        return this.poolPreparedStatements;
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public boolean isRemoveAbandoned() {
        return this.removeAbandoned;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public void setMaxEvictableIdleTimeMillis(long maxEvictableIdleTimeMillis) {
        this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
    }

    public void setTransactionQueryTimeout(int transactionQueryTimeout) {
        this.transactionQueryTimeout = transactionQueryTimeout;
    }

    public String getUrl() {
        return this.url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
