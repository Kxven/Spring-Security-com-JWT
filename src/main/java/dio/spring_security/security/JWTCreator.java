package dio.spring_security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class JWTCreator {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    private static SecretKey getSigningKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public static String create(String prefix, String key, JWTObject jwtObject) {

        String token = Jwts.builder()
                .setSubject(jwtObject.getSubject())
                .setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();

        return prefix + " " + token;
    }

    public static JWTObject create(String token, String prefix, String key) {

        token = token.replace(prefix, "").trim();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();

        JWTObject object = new JWTObject();
        object.setSubject(claims.getSubject());
        object.setIssuedAt(claims.getIssuedAt());
        object.setExpiration(claims.getExpiration());
        object.setRoles((List<String>) claims.get(ROLES_AUTHORITIES));

        return object;
    }

    private static List<String> checkRoles(List<String> roles) {
        return roles.stream()
                .map(r -> "ROLE_".concat(r.replace("ROLE_", "")))
                .collect(Collectors.toList());
    }
}
