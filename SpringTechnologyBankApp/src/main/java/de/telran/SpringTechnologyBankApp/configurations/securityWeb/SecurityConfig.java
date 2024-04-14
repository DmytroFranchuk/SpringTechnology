package de.telran.SpringTechnologyBankApp.configurations.securityWeb;

import de.telran.SpringTechnologyBankApp.services.usersapp.impl.UserDetailServiceImpl;
import de.telran.SpringTechnologyBankApp.services.usersapp.interf.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
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
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                        "/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html/**",
                        "/webjars/**").permitAll()
                        .requestMatchers("/api/v1/registration/**").hasRole("REGISTRAR")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/registration/users/all").hasRole("ANONYMOUS")
                        .requestMatchers("/login*", "/logout*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/echo").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/test").hasRole("CLIENT")
                        .anyRequest().authenticated())
                        .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}

//    @Bean
//    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//        // Пользователи в памяти
//        auth.userDetailsService(inMemoryUserDetailsManager);
//
//        // Пользователи из базы данных
//        DaoAuthenticationProvider userDetailAuthProvider = new DaoAuthenticationProvider();
//        userDetailAuthProvider.setUserDetailsService(userDetailService);
//        userDetailAuthProvider.setPasswordEncoder(passwordEncoder());
//        auth.authenticationProvider(userDetailAuthProvider);
//
//        return auth.build();
//        auth.userDetailsService(inMemoryUserDetailsManager);
//
//        // Пользователи из базы данных
//        auth.userDetailsService(userDetailService)
//                .passwordEncoder(passwordEncoder());
//    }


//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

//    @Bean
//    public AuthenticationManager authenticationManager(@NotNull HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .build();
//    }
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler2() {
//        return new CustomAuthenticationSuccessHandler();
//    }

//                .headers(headers -> headers.cacheControl(Customizer.withDefaults()).disable())
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)

//                .formLogin(Customizer.withDefaults())
//                .formLogin(form -> form.defaultSuccessUrl("/hello"))


//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl(getSuccessUrl())
//                        .permitAll()
//                        .failureUrl("/login")
//
////                        .successHandler(authenticationSuccessHandler())
//                )

//                .logout(logoutPage -> logoutPage
////                        .logoutUrl("/logout"));
//                        .logoutSuccessUrl("/login")
//                );


//    private String getSuccessUrl() {
//        SavedRequest savedRequest = null;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            if (attributes != null) {
//                savedRequest = new HttpSessionRequestCache().getRequest(attributes.getRequest(), attributes.getResponse());
//            }
//        }
//        String redirectUrl = "/hello";
//        String savedRedirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : null;
//        if (savedRedirectUrl != null && !savedRedirectUrl.equals("/login")) {
//            redirectUrl = savedRedirectUrl;
//        }
//        System.out.println("==================>> " + redirectUrl + " : " + savedRedirectUrl);
//        return redirectUrl;
//    }

//    @Bean
//    public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
//        SavedRequestAwareAuthenticationSuccessHandler successHandler =
//                new SavedRequestAwareAuthenticationSuccessHandler();
//        successHandler.setUseReferer(true);
//        return successHandler;
//    }


//    @Bean
//    public AuthenticationManager authenticationManager(@NotNull HttpSecurity http) throws Exception {
////        return http.getSharedObject(AuthenticationManagerBuilder.class)
////                .build();
//        return new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                String username = authentication.getName();
//                String password = authentication.getCredentials().toString();
//                UserDetails userDetails = usersConfig.defaultUsers().loadUserByUsername(username);
//                if (userDetails != null && passwordEncoder().matches(password, userDetails.getPassword())) {
//                    return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//                }
//                userDetails = userApplicationService.loadUserByUsername(username);
//                if (userDetails != null && passwordEncoder().matches(password, userDetails.getPassword())) {
//                    return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//                } else {
//                    throw new AuthenticationException("Неверный логин или пароль") {
//                    };
//                }
//            }
//        };
//    }