package com.nnk.springboot.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Provides a BCrypt password encoder bean.
     *
     * @return BCryptPasswordEncoder configured password encoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.debug("Creating BCrypt password encoder bean");
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a User in memory to login bean.
     *
     * @return InMemoryUserDetailsManager configured to have a user memory .
     */
    //we need a user in memory because we configured a Bean in configuration class so springSecurity unable facilities dev user Login.
    // And we haven't config UserDetail to connect at data source so User In memory.
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/home/admin/**").hasRole("ADMIN")
                                .requestMatchers("/home/**").authenticated()
                                .requestMatchers("/**").permitAll()

                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/home"))  // Redirect on successful login
                                .failureHandler((request, response, exception) -> {
                                    response.sendRedirect("/login?error=true");
                                })
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .accessDeniedPage("/403")  // Redirect to error page on access denial
                );

        return http.build();
    }
}
