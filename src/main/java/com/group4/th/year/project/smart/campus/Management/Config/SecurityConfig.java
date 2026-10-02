package com.group4.th.year.project.smart.campus.Management.Config;

import com.group4.th.year.project.smart.campus.Management.ROLE;
import com.group4.th.year.project.smart.campus.Management.Repo.LibrarianRepo;
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
                        .requestMatchers("/", "/index.html", "/admin.html", "/student.html", "/librarian.html", "/placement.html", "/styles.css", "/app.js", "/favicon.ico").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/dashboard").hasRole("ADMIN")
                        .requestMatchers("/student-panel/access").hasRole("STUDENT")
                        .requestMatchers("/librarian-panel/access").hasRole("LIBRARIAN")
                        .requestMatchers("/placement-panel/access").hasRole("PLACEMENT")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/courses/**", "/notices/**", "/resources/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/courses/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.PUT, "/courses/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/notices/**").hasAnyRole("ADMIN", "FACULTY", "PLACEMENT")
                        .requestMatchers(HttpMethod.PUT, "/notices/**").hasAnyRole("ADMIN", "FACULTY", "PLACEMENT")
                        .requestMatchers(HttpMethod.DELETE, "/notices/**").hasAnyRole("ADMIN", "PLACEMENT")
                        .requestMatchers(HttpMethod.POST, "/resources/**").hasAnyRole("ADMIN", "SECURITY", "LIBRARIAN")
                        .requestMatchers(HttpMethod.PUT, "/resources/**").hasAnyRole("ADMIN", "SECURITY", "LIBRARIAN")
                        .requestMatchers(HttpMethod.DELETE, "/resources/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/students/**", "/faculties/**", "/events/**", "/bookings/**", "/books/**", "/issues/**", "/logs/**").authenticated()
                        .requestMatchers("/students/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers("/faculties/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/events/**").hasAnyRole("ADMIN", "FACULTY", "PLACEMENT")
                        .requestMatchers(HttpMethod.PUT, "/events/**").hasAnyRole("ADMIN", "FACULTY", "PLACEMENT")
                        .requestMatchers(HttpMethod.DELETE, "/events/**").hasAnyRole("ADMIN", "PLACEMENT")
                        .requestMatchers(HttpMethod.POST, "/bookings/**").hasAnyRole("ADMIN", "STUDENT", "FACULTY")
                        .requestMatchers(HttpMethod.PUT, "/bookings/**").hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.DELETE, "/bookings/**").hasRole("ADMIN")
                        .requestMatchers("/books/**", "/issues/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/librarians/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .requestMatchers("/librarians/**").hasRole("ADMIN")
                        .requestMatchers("/logs/**").hasAnyRole("ADMIN", "SECURITY")
                        .requestMatchers("/allotments/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo, LibrarianRepo librarianRepo, PasswordEncoder passwordEncoder) {
        return username -> {
            UserDetails defaultUser = getDefaultUser(username, passwordEncoder);
            if (defaultUser != null) {
                return defaultUser;
            }

            return userRepo.findByEmail(username)
                    .filter(user -> user.getRole() == ROLE.ADMIN)
                    .filter(user -> user.getPassword() != null && !user.getPassword().isBlank())
                    .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                            .password(user.getPassword())
                            .roles(user.getRole().name())
                            .build())
                    .or(() -> librarianRepo.findByEmail(username)
                            .map(librarian -> org.springframework.security.core.userdetails.User.withUsername(librarian.getEmail())
                                    .password(librarian.getPassword())
                                    .roles("LIBRARIAN")
                                    .build()))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        };
    }

    private UserDetails getDefaultUser(String username, PasswordEncoder passwordEncoder) {
        return switch (username) {
            case "admin" -> org.springframework.security.core.userdetails.User.withUsername("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles("ADMIN")
                    .build();
            case "faculty" -> org.springframework.security.core.userdetails.User.withUsername("faculty")
                    .password(passwordEncoder.encode("faculty123"))
                    .roles("FACULTY")
                    .build();
            case "student" -> org.springframework.security.core.userdetails.User.withUsername("student")
                    .password(passwordEncoder.encode("student123"))
                    .roles("STUDENT")
                    .build();
            case "security" -> org.springframework.security.core.userdetails.User.withUsername("security")
                    .password(passwordEncoder.encode("security123"))
                    .roles("SECURITY")
                    .build();
            case "librarian" -> org.springframework.security.core.userdetails.User.withUsername("librarian")
                    .password(passwordEncoder.encode("librarian123"))
                    .roles("LIBRARIAN")
                    .build();
            case "placement" -> org.springframework.security.core.userdetails.User.withUsername("placement")
                    .password(passwordEncoder.encode("placement123"))
                    .roles("PLACEMENT")
                    .build();
            default -> null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
