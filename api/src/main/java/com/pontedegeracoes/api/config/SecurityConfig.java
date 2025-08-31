package com.pontedegeracoes.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pontedegeracoes.api.security.CustomUserDetailsService;
import com.pontedegeracoes.api.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  private final CustomUserDetailsService customUserDetailsService;

  public SecurityConfig(JwtAuthFilter jwtAuthFilter,
      CustomUserDetailsService customUserDetailsService) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configure(http))
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/auth/**",
                "/docs/**",
                "/ws/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/users",
                "/users/email/**",
                "/necessities")

            .permitAll()
            .anyRequest().authenticated())
        .userDetailsService(customUserDetailsService)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}