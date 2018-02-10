package com.github.mustfun.generator.support.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dengzhiyuan on 2017/4/10.
 */
@Configuration
public class DruidOtherConfig {

    private transient org.slf4j.Logger resultsetLogger = LoggerFactory.getLogger("com.alibaba.druid.resultset");
    private transient org.slf4j.Logger statementLogger = LoggerFactory.getLogger("com.alibaba.druid.statement");
    private transient org.slf4j.Logger datasourceLogger = LoggerFactory.getLogger("com.alibaba.druid.datasource");
    private transient org.slf4j.Logger connectionLogger = LoggerFactory.getLogger("com.alibaba.druid.connection");



    /**
     * 本次日志用slf4jFilter，因为用的logback
     * 重新定义一下logger的名称，这样就可以在logback重新定制新的logger名称
     * @return
     */
    @Bean
    public Slf4jLogFilter slf4jLogFilter(){
        Slf4jLogFilter slf4jLogFilter=new Slf4jLogFilter();
        slf4jLogFilter.setResultSetLogEnabled(true);
        slf4jLogFilter.setStatementLogEnabled(true);
        slf4jLogFilter.setConnectionLogEnabled(true);
        slf4jLogFilter.setDataSourceLogEnabled(true);
        slf4jLogFilter.setResultSetLogger(resultsetLogger);
        slf4jLogFilter.setDataSourceLogger(datasourceLogger);
        slf4jLogFilter.setStatementLogger(statementLogger);
        slf4jLogFilter.setConnectionLogger(connectionLogger);
        return slf4jLogFilter;
    }

    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){

        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","admin");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }
}
