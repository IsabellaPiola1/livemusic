package br.com.fiap.livemusic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import br.com.fiap.livemusic.user.UserRepository;

@Configuration
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests( auth -> auth.anyRequest().authenticated())
                .oauth2Login( form -> form.loginPage("/login").defaultSuccessUrl("/task").permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login") )
                .addFilterBefore(new LoginFilter(userRepository), OAuth2LoginAuthenticationFilter.class)
                .build();
    }
    
}