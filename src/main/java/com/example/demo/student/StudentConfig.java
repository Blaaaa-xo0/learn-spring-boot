package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Calendar.AUGUST;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "mariam.jamal@gmail.com",
                    LocalDate.of(2002, AUGUST, 28)
            );

            Student john = new Student(
                    "John doe",
                    "johndoe@gmail.com",
                    LocalDate.of(1990, AUGUST, 28)
            );

            repository.saveAll(
                    List.of(mariam, john)
            );
        };
    }
}
