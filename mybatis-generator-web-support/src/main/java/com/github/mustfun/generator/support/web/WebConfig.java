package com.github.mustfun.generator.support.web;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/4/26
 * @since 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {//继承了这个类有什么用？

    @Bean
    public HttpMessageConverter httpMessageConverter(){
        FastJsonConfig fastJsonConfig =new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 =  new FastJsonHttpMessageConverter4();
        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);
        fastJsonHttpMessageConverter4.setDefaultCharset(Charset.forName("UTF-8"));
        //fastJsonHttpMessageConverter4.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8,MediaType.APPLICATION_JSON));
        return fastJsonHttpMessageConverter4;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(httpMessageConverter());
        super.configureMessageConverters(converters);
    }


}
