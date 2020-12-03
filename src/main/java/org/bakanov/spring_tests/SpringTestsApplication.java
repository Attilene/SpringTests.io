package org.bakanov.spring_tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringTestsApplication {
    public static void main(String[] args) { SpringApplication.run(SpringTestsApplication.class, args); }
}
