package com.example.demo.Student.repository;

import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository testRepo;

    @Test
    void testFindStudentByEmail() {
        /* Arrange */
        String email = "axel@gmail.com";
        Student student = new Student(
                "Axel",
                "axel@gmail.com",
                LocalDate.of(2000,04,03)
        );
        testRepo.save(student);

        /* Act */
        Optional<Student> foundStudent = testRepo.findStudentByEmail(email);

        /* Assert */
        assertTrue(foundStudent.isPresent());
        assertEquals(email, foundStudent.get().getEmail());
    }
}