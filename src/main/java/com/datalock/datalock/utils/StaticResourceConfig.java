package com.datalock.datalock.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/imagenes/categorias/**")
                .addResourceLocations("file:/opt/imagenes/categorias/");


        registry.addResourceHandler("/imagenes/productos/**")
                .addResourceLocations("file:/opt/imagenes/productos/");
    }
}