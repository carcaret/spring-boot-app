package com.carcaret.web;

import com.carcaret.web.filter.CustomFilter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("admin");
    registry.addViewController("/admin/menu").setViewName("admin");
  }

  @Bean
  public FilterRegistrationBean filterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(customFilter());
    registration.addUrlPatterns("/*");
    registration.setName("customFilter");
    registration.setOrder(1);
    return registration;
  }

  @Bean(name = "customFilter")
  public Filter customFilter() {
    return new CustomFilter();
  }

}
