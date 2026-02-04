package dio.spring_security.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final SecurityConfig securityConfig;

    public JWTFilter(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Libera H2 e login sem JWT
        if (path.startsWith("/h2-console") || path.equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

        try {
            if (token != null && !token.isBlank()) {

                JWTObject jwtObject = JWTCreator.create(
                        token,
                        securityConfig.getPrefix(),
                        securityConfig.getKey()
                );

                var authorities = jwtObject.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                jwtObject.getSubject(),
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}
