package com.example.restservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan
@Import({
    SecurityAutoConfiguration.class,
    ServerPropertiesAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class,
    JacksonAutoConfiguration.class,
    WebMvcAutoConfiguration.class,
    ErrorMvcAutoConfiguration.class,
    DispatcherServletAutoConfiguration.class,
    AopAutoConfiguration.class,
    PropertyPlaceholderAutoConfiguration.class
})
@ImportResource("classpath:config-rest-properties.xml")
public class Application {

    private static final String SECURITY_ROLE_API_CLIENT = "API_CLIENT";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    protected static class AuthenticationSecurity extends
            GlobalAuthenticationConfigurerAdapter {

        @Value("${username1}")
        private String username1;
        @Value("${password1}")
        private String password1;
        @Value("${username2}")
        private String username2;
        @Value("${password2}")
        private String password2;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(username1).password(password1).roles(SECURITY_ROLE_API_CLIENT)
                    .and()
                    .withUser(username2).password(password2).roles(SECURITY_ROLE_API_CLIENT);
        }

        @EnableWebSecurity
        @Configuration
        protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/api/**").hasRole(SECURITY_ROLE_API_CLIENT)
                        .and()
                        .httpBasic();
            }
        }
    }
}
