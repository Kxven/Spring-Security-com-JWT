package dio.spring_security.pastasParaexclui.init;

import dio.spring_security.model.User;
import dio.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StartApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        if (repository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setName("ADMIN");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("master123"));
            admin.getRoles().add("MANAGERS");
            repository.save(admin);
        }

        if (repository.findByUsername("user") == null) {
            User user = new User();
            user.setName("USER");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.getRoles().add("USERS");
            repository.save(user);
        }
    }
}
