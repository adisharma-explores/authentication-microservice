//package com.ecommerce.authentication_service.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final JwtAthFilter jwtAthFilter;
//    private final UserDetailsService userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**/auth/**").permitAll() // Public endpoints
//                        .requestMatchers("/swagger-ui.html").permitAll() // Allow Swagger access
//                        .requestMatchers("/apis/v2/**").hasAnyAuthority("ROLE_USER") // Restricted to ROLE_USER
//                        .anyRequest().authenticated() // All other requests must be authenticated
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
//                )
//                .authenticationProvider(authenticationProvider()) // Use custom authentication provider
//                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
////    @Bean
////    public UserDetailsService userDetailsService(){
////        return new UserDetailsService() {
////            @Override
////            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////                return userDAO.findUserByEmail(email);     }
////        };
////    }
//}
