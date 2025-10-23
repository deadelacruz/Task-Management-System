package com.example.demo.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web Configuration for serving React SPA and static resources
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the project root directory
        String projectRoot = System.getProperty("user.dir");
        String frontendBuildPath = projectRoot + File.separator + "frontend" + File.separator + "build";
        
        // Serve React build files from frontend/build directory
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + frontendBuildPath + File.separator + "static" + File.separator)
                .setCachePeriod(31556926); // 1 year cache for static assets

        // Serve React index.html and other files from frontend/build
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + frontendBuildPath + File.separator)
                .setCachePeriod(0); // No cache for HTML files to ensure updates
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Handle React Router routes - redirect to index.html for SPA
        registry.addViewController("/dashboard")
                .setViewName("forward:/index.html");
        registry.addViewController("/tasks")
                .setViewName("forward:/index.html");
        registry.addViewController("/reports")
                .setViewName("forward:/index.html");
        registry.addViewController("/login")
                .setViewName("forward:/index.html");
        registry.addViewController("/register")
                .setViewName("forward:/index.html");
    }
}
