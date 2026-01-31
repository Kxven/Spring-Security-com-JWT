package dio.spring_security;

import dio.spring_security.config.SecurityDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private SecurityDataBaseService securityService;
    @Autowired
    public void globalUserDetails( AuthenticationManagerBuilder auth ) throws Exception{
        auth.userDetailsService(securityService).passwordEncoder( NoOpPasswordEncoder.getInstance());
    }


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/managers").hasRole("MANAGERS")
                        .requestMatchers("/users").hasAnyRole("USERS", "MANAGERS")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails manager = User.withUsername("manager123")
                .password("{noop}123")
                .roles("MANAGERS")
                .build();

        UserDetails user = User.withUsername("user123")
                .password("{noop}123")
                .roles("USERS")
                .build();

        return new InMemoryUserDetailsManager(manager, user);
    }*/
}
