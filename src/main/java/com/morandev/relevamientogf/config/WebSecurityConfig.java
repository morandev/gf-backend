package com.morandev.relevamientogf.config;

import com.morandev.relevamientogf.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(
            UserDetailsService userDetailsService,
            JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Registra un encriptador/desencriptador para las passwords
     *
     * @return, Desencriptador de tipo BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
            si la api es autenticada con tokens como jwt
            no se necesita la proteccion CSRF
         */
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/roles/**").authenticated()
                .antMatchers("/api/integrantes/**").authenticated()
                .antMatchers("/api/grupos-familiares/**").authenticated()
                .antMatchers("/api/authenticate/**").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and()
                /*
                    desabilitamos los sessions ids
                */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //agregamos un filtro antes del filtro de autenticacion de usario y password
        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}

