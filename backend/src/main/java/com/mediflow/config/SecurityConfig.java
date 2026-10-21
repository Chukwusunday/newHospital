package com.mediflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${DAN_ADMIN_PASSWORD:ChangeMe123!}") private String adminPassword;
    @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder=passwordEncoder();
        auth.inMemoryAuthentication()
            .withUser("admin").password(encoder.encode(adminPassword)).roles("ADMIN").and()
            .withUser("reception").password(encoder.encode("Reception123!")).roles("RECEPTION").and()
            .withUser("nurse").password(encoder.encode("Nurse123!")).roles("NURSE").and()
            .withUser("doctor").password(encoder.encode("Doctor123!")).roles("DOCTOR").and()
            .withUser("pharmacy").password(encoder.encode("Pharmacy123!")).roles("PHARMACY");
    }
    @Override protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/", "/index.html", "/assets/**", "/favicon.ico", "/error").permitAll()
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/h2-console/**").hasRole("ADMIN")
            .antMatchers("/api/auth/**").authenticated()
            .antMatchers(HttpMethod.POST,"/api/patients/**").hasAnyRole("RECEPTION","ADMIN")
            .antMatchers(HttpMethod.PUT,"/api/patients/**").hasAnyRole("RECEPTION","ADMIN")
            .antMatchers(HttpMethod.POST,"/api/visits/**").hasAnyRole("RECEPTION","ADMIN")
            .antMatchers(HttpMethod.PUT,"/api/visits/*/vitals").hasAnyRole("NURSE","ADMIN")
            .antMatchers(HttpMethod.PUT,"/api/visits/*/consultation").hasAnyRole("DOCTOR","ADMIN")
            .antMatchers(HttpMethod.POST,"/api/prescriptions/**").hasAnyRole("DOCTOR","ADMIN")
            .antMatchers(HttpMethod.PUT,"/api/prescriptions/*/dispense").hasAnyRole("PHARMACY","ADMIN")
            .antMatchers("/api/**").authenticated().anyRequest().denyAll().and().httpBasic();
        http.headers().frameOptions().sameOrigin();
    }
}
