package com.firstGroup.restaurant.config;

import com.firstGroup.restaurant.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenFilter tokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/token").anonymous()
                .antMatchers("/admin/login").anonymous()
                .antMatchers("/auth/checkToken").permitAll()
                .antMatchers("/restaurant/findAll").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/common/test/getToken").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/doc.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors();
    }
}
