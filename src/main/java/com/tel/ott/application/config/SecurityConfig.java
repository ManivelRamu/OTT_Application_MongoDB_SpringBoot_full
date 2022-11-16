package com.tel.ott.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.inMemoryAuthentication().withUser("user").password("user").roles("USER")
              .and().withUser("admin").password("admin").roles("ADMIN");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().authorizeRequests()
              .antMatchers("/api/user/**").permitAll()
              .antMatchers("/admin/findAll").hasRole("ADMIN")
              .antMatchers("/movie/get/**").permitAll()
              .antMatchers("*/modify/**").hasRole("ADMIN")
              //.antMatchers("/show/get/**").hasRole("user")
              //.antMatchers("/season/get/**").hasRole("user")
              //.antMatchers("/episode/get/**").hasRole("user")
              //.antMatchers("*/create/**").hasRole("ADMIN")
              .and().httpBasic();
  }

  @SuppressWarnings("deprecation")
  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
      return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }
}