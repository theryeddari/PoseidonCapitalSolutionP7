package com.nnk.springboot.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
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

        return new InMemoryUserDetailsManager(user);
    }
}
