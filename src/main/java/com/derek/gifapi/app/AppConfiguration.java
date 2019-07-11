package com.derek.gifapi.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

/**
 * This class configures the application when it is started up.
 */
@Configuration
public class AppConfiguration {
    @Value("${db.salt}")
    private String salt;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(16, new SecureRandom(salt.getBytes()));
    }
}
