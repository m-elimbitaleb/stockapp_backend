/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.config;

import net.bull.javamelody.*;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class JavaMelodyConfiguration implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(new SessionListener());
    }

    /**
     * For get FilterRegistrationBean
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<MonitoringFilter> javaMelody() {
        final FilterRegistrationBean<MonitoringFilter> javaMelody = new FilterRegistrationBean();
        javaMelody.setFilter(new MonitoringFilter());
        javaMelody.setAsyncSupported(true);
        javaMelody.setName("javamelody");
        javaMelody.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        javaMelody.addInitParameter(Parameter.LOG.getCode(), Boolean.toString(true));
        javaMelody.addUrlPatterns("/*");
        return javaMelody;
    }

    /**
     * For monitoring of jdbc datasources:
     *
     * @return SpringDataSourceBeanPostProcessor
     */
    @Bean
    public SpringDataSourceBeanPostProcessor monitoringDataSourceBeanPostProcessor() {
        SpringDataSourceBeanPostProcessor processor = new SpringDataSourceBeanPostProcessor();
        processor.setExcludedDatasources(null);
        return processor;
    }

    /**
     * For monitoring of beans or methods having @MonitoredWithSpring:
     *
     * @return MonitoringSpringAdvisor
     */
    @Bean
    public MonitoringSpringAdvisor monitoringAdvisor() {
        final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
        interceptor.setPointcut(new MonitoredWithAnnotationPointcut());
        return interceptor;
    }

    /**
     * For monitoring of all services and controllers
     *
     * @return MonitoringSpringAdvisor
     */
    @Bean
    public MonitoringSpringAdvisor springRestControllerMonitoringAdvisor() {
        final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
        interceptor.setPointcut(new AnnotationMatchingPointcut(RestController.class));
        return interceptor;
    }
}
