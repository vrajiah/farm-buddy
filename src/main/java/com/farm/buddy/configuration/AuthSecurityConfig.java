package com.farm.buddy.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class AuthSecurityConfig {
    @EnableWebSecurity
    public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.anonymous().disable()
                    .requestMatcher(request -> {
                        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
                        return (auth != null && auth.startsWith("Basic"));
                    })
                    .authorizeRequests()
                    /* replace below line to antMatchers to enable Basic Authentication for /predict */
                    //.antMatchers("/api**", "/predict").authenticated()
                    .antMatchers("/api**").authenticated()
                    .and().logout().logoutSuccessUrl("/").permitAll()
                    .and().csrf().disable()
                    .httpBasic()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }
}
