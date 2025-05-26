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
                  1L,
                    "Mariam",
                    "ma@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5),
                    21
            );

            Student axel = new Student(
                    1L,
                    "Axel",
                    "axel@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 3),
                    25
            );

            repository.saveAll(
                    List.of(mariam,axel)
            );
        };
    }
}
