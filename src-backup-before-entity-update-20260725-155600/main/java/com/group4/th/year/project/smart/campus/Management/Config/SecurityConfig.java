package com.group4.th.year.project.smart.campus.Management.Config;

import com.group4.th.year.project.smart.campus.Management.Repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/dashboard").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/courses/**", "/notices/**", "/resources/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/courses/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.PUT, "/courses/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/notices/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.PUT, "/notices/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.DELETE, "/notices/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/resources/**").hasAnyRole("ADMIN", "SECURITY", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/resources/**").hasAnyRole("ADMIN", "SECURITY", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/resources/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/students/**", "/faculties/**", "/events/**", "/bookings/**", "/books/**", "/issues/**", "/logs/**").authenticated()
                        .requestMatchers("/students/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers("/faculties/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/events/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.PUT, "/events/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.DELETE, "/events/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/bookings/**").hasAnyRole("ADMIN", "STUDENT", "FACULTY")
                        .requestMatchers(HttpMethod.PUT, "/bookings/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.DELETE, "/bookings/**").hasRole("ADMIN")
                        .requestMatchers("/books/**", "/issues/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/logs/**").hasAnyRole("ADMIN", "SECURITY")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        return username -> {
            UserDetails defaultUser = getDefaultUser(username, passwordEncoder);
            if (defaultUser != null) {
                return defaultUser;
            }

            return userRepo.findByEmail(username)
                    .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                            .password(user.getPassword())
                            .roles(user.getRole().name())
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        };
    }

    private UserDetails getDefaultUser(String username, PasswordEncoder passwordEncoder) {
        return switch (username) {
            case "admin" -> org.springframework.security.core.userdetails.User.withUsername("admin")
                    .password(passwordEncoder.encode("[REMOVED]"))
                    .roles("ADMIN")
                    .build();
            case "faculty" -> org.springframework.security.core.userdetails.User.withUsername("faculty")
                    .password(passwordEncoder.encode("[REMOVED]"))
                    .roles("FACULTY")
                    .build();
            case "student" -> org.springframework.security.core.userdetails.User.withUsername("student")
                    .password(passwordEncoder.encode("[REMOVED]"))
                    .roles("STUDENT")
                    .build();
            case "security" -> org.springframework.security.core.userdetails.User.withUsername("security")
                    .password(passwordEncoder.encode("[REMOVED]"))
                    .roles("SECURITY")
                    .build();
            case "librarian" -> org.springframework.security.core.userdetails.User.withUsername("librarian")
                    .password(passwordEncoder.encode("[REMOVED]"))
                    .roles("LIBRARIAN")
                    .build();
            default -> null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
