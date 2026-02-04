package dio.spring_security.controller;

import dio.spring_security.dto.LoginDTO;
import dio.spring_security.dto.SessaoDTO;
import dio.spring_security.model.User;
import dio.spring_security.repository.UserRepository;
import dio.spring_security.security.JWTCreator;
import dio.spring_security.security.JWTObject;
import dio.spring_security.security.SecurityConfig;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class LoginController {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final SecurityConfig securityConfig;

    public LoginController(
            PasswordEncoder encoder,
            UserRepository repository,
            SecurityConfig securityConfig) {

        this.encoder = encoder;
        this.repository = repository;
        this.securityConfig = securityConfig;
    }

    @PostMapping("/login")
    public SessaoDTO login(@RequestBody LoginDTO login) {

        User user = repository.findByUsername(login.getUsername());

        if (user == null || !encoder.matches(login.getPassword(), user.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        JWTObject jwtObject = new JWTObject();
        jwtObject.setSubject(user.getUsername());
        jwtObject.setIssuedAt(new Date());
        jwtObject.setExpiration(
                new Date(System.currentTimeMillis() + securityConfig.getExpiration())
        );
        jwtObject.setRoles(user.getRoles());

        String token = JWTCreator.create(
                securityConfig.getPrefix(),
                securityConfig.getKey(),
                jwtObject
        );

        SessaoDTO sessao = new SessaoDTO();
        sessao.setLogin(user.getUsername());
        sessao.setToken(token);

        return sessao;
    }
}
