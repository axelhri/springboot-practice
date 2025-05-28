package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
          Student mariam = new Student(
                    "Mariam",
                    "mariam@gmail.com",
                    LocalDate.of(1975, Month.JANUARY, 5)
            );

            Student axel = new Student(
                    "Axel",
                    "axel@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 3)
            );

            repository.saveAll(
                    List.of(mariam,axel)
            );
        };
    }
}
