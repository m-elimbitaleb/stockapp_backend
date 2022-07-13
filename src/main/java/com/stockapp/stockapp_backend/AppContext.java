package com.stockapp.stockapp_backend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class AppContext implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        AppContext.context = context;
    }

    public static <T> T getBean(Class<T> bean) {
        return context.getBean(bean);
    }
}
