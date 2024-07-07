package social.media.socialMedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //FIXME: use the following:
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/api/users/seed").hasRole("ADMIN")
//                        .requestMatchers("/api/users/deleteByUsername").hasRole("ADMIN")
//                        .requestMatchers("/api/users").hasRole("ADMIN")
//                        .requestMatchers("/api/users/**").authenticated()  // other routes under /api/users
//                        .anyRequest().permitAll() // public access to all other routes
//                )
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2Client(Customizer.withDefaults());
//        return http.build();
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/api/users/seed", "/api/users", "/api/users/{username}", "/api/users/deleteByUsername")
//                        .hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2Client(Customizer.withDefaults());
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
////                .oauth2ResourceServer((oauth2) -> oauth2
////                        .jwt(Customizer.withDefaults())
////                )
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2Client(Customizer.withDefaults());
//        return http.build();
//    }


}