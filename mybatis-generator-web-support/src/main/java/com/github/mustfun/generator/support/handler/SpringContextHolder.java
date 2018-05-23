package com.github.mustfun.generator.support.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/5/22
 * @since 1.0
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 获取Bean
     *
     * @param beanType bean类型
     * @return
     */
    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    /**
     * 获取Bean
     *
     * @param beanName bean名称
     * @param beanType bean类型
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> beanType) {
        return applicationContext.getBean(beanName, beanType);
    }


    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }
}
