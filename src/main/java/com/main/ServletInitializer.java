package com.main;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    public ServletInitializer() {
        System.out.println("初始化ServletInitializer");
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(KiptvcmsApp.class);//MyApplication是启动类名
    }
}