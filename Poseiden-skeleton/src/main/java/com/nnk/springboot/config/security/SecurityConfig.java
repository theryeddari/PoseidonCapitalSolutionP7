package com.nnk.springboot.config.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Optional;

/**
 * Security configuration class for setting up Spring Security.
 * <p>
 * This configuration class is responsible for:
 * <ul>
 *     <li>Providing a BCrypt password encoder bean.</li>
 *     <li>Defining a UserDetailsService bean to load user details by username.</li>
 *     <li>Configuring HTTP security to handle authentication and authorization.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@Profile("default")
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Provides a BCrypt password encoder bean.
     * <p>
     * This bean is used to encode and validate passwords securely.
     * </p>
     *
     * @return BCryptPasswordEncoder configured password encoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.debug("Creating BCrypt password encoder bean");
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a UserDetailsService bean for loading user details by username.
     * <p>
     * This implementation uses a lambda function to load user details from the UserRepository.
     * If the user is not found, a UsernameNotFoundException is thrown.
     * </p>
     *
     * @param userRepository the repository used to fetch user details.
     * @return UserDetailsService implementation.
     */
    @Bean
    public UserDetailsService clientUserDetailsService(UserRepository userRepository) {
        logger.debug("Creating UserDetailsService bean with UserRepository");

        // Implement loadUserByUsername (interface function) by lambda
        UserDetailsService userDetailsService = username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                logger.error("User not found with username: {}", username);
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            logger.info("User found: {}", username);

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getUsername())
                    .password(user.get().getPassword()) // Assuming user.getPassword() returns hashed password
                    .roles(user.get().getRole())
                    .build();
        };
        return userDetailsService;
    }

    /**
     * Configures HTTP security for the application.
     * <p>
     * This method sets up authorization rules, login form configurations, exception handling,
     * and logout functionality.
     * </p>
     *
     * @param http the HttpSecurity object used for configuration.
     * @return SecurityFilterChain with the configured security settings.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Configuring HTTP security");

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login").permitAll() // Allow access to the login page
                                .requestMatchers("/home/admin/**").hasRole("ADMIN") // Restrict access to admin routes
                                .requestMatchers("/home/**").authenticated() // Restrict access to authenticated users
                                .requestMatchers("/**").permitAll() // Allow access to other routes
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Specify the login page URL
                                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/home")) // Redirect on successful login
                                .failureHandler((request, response, exception) -> {
                                    logger.error("Login failed: {}", exception.getMessage());
                                    response.sendRedirect("/login?error=true");
                                }) // Redirect on login failure
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .accessDeniedPage("/403") // Redirect to error page on access denial
                )
                .logout(formLogout ->
                        formLogout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/app-logout", "POST")) // Specify logout URL and method
                                .logoutSuccessUrl("/home") // Redirect on successful logout
                );

        return http.build();
    }
}
