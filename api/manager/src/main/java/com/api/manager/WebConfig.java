package com.api.manager;

import com.api.manager.auth.jwt.JwtAuthenticationEntryPoint;
import com.api.manager.auth.jwt.JwtReqFilter;
import com.api.manager.auth.service.JwtUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfig {


    private final JwtUserDetailService jwtUserDetailsService;

    private final JwtReqFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint entryPoint;

    //Auth
    WebConfig(JwtUserDetailService jwtUserDetailService, JwtReqFilter jwtRequestFilter, JwtAuthenticationEntryPoint entryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.entryPoint = entryPoint;
        this.jwtUserDetailsService = jwtUserDetailService;
    }

    //Auth
    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers("/auth/*")
                .permitAll().requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().requestMatchers(
                        "/v3/api-docs/**"
                ).permitAll().requestMatchers("/swagger-ui/**").permitAll().anyRequest().authenticated()
        ).exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint)).sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(
                jwtRequestFilter, UsernamePasswordAuthenticationFilter.class
        );

        return httpSecurity.build();
    }


}


