package de.telran.SpringTechnologyBankApp.configurations.securityWeb;

import de.telran.SpringTechnologyBankApp.services.usersapp.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final UserDetailServiceImpl userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public DaoAuthenticationProvider inMemoryAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(inMemoryUserDetailsManager);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain configureExcludePath(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
//                .addFilter()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**",  "/configuration/ui/**", "/swagger-resources/**",
                                "/configuration/security", "/swagger-ui.html/**", "/webjars/**").permitAll()
                        .requestMatchers("/login*", "/logout*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/test").hasRole("CLIENT")
                        .requestMatchers("/api/v1/registration/**").hasRole("REGISTRAR")
                        .requestMatchers(HttpMethod.GET, "/echo").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/api/v1/managers/**").hasAnyRole("MANAGER", "ADMIN", "REGISTRAR")
                        .requestMatchers("/api/v1/products/**").hasAnyRole("MANAGER", "ADMIN", "REGISTRAR")
                        .requestMatchers("/api/v1/accounts/**").hasAnyRole("MANAGER", "ADMIN", "REGISTRAR")
                        .requestMatchers("/api/v1/agreements/**").hasAnyRole("MANAGER", "ADMIN", "REGISTRAR")
                        .requestMatchers("/api/v1/clients/**").hasAnyRole("CLIENT", "MANAGER", "ADMIN", "REGISTRAR")
                        .requestMatchers("/api/v1/transactions/**").hasAnyRole("CLIENT", "MANAGER", "ADMIN", "REGISTRAR")
                        .anyRequest().authenticated())
                        .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}