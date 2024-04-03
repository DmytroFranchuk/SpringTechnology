package de.telran.SpringTechnologyBankApp.configurations.securityWeb;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class PathExcludeSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(@NotNull HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public SecurityFilterChain configureExcludePath(@NotNull HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(httpBasic -> {})
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login*", "/logout*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/hello").permitAll()
                        .requestMatchers(HttpMethod.GET, "/echo").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/test").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/registration/*").hasRole("REGISTRAR")
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }



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
}



















