// package com.springdale.erp;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;

// @SpringBootApplication
// @EnableConfigurationProperties
// public class ErpApplication {

//     public static void main(String[] args) {
//         SpringApplication.run(ErpApplication.class, args);
//     }
// }
package com.springdale.erp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springdale.erp.users.repo.UserRepository;
import com.springdale.erp.users.entity.User;
import com.springdale.erp.users.enums.Role;

@SpringBootApplication
public class ErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmailIgnoreCase("admin@school.local").isEmpty()) {
                User user = new User();
                user.setFullName("Admin User");
                user.setEmail("admin@school.local");
                user.setPasswordHash(encoder.encode("Admin@123")); 
                user.setRole(Role.ADMIN); 
                user.setActive(true);

                repo.save(user);

                System.out.println("Admin user created");
            }
        };
    }
}
//mvn clean spring-boot:run -Dspring-boot.run.profiles=dev

// to start type above line