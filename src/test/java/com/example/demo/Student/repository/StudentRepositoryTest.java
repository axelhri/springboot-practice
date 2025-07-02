package com.example.demo.Student.repository;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.mapper.StudentMapper;
import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.student.service.StudentService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRepositoryTest {
    @Autowired
    private StudentRepository testRepo;

    @Autowired
    private StudentService testService;

    @Autowired
    private StudentMapper testMapper;


    @Test
    @Order(1)
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
        assertEquals(email, foundStudent.get().getEmail());
    }

    @Test
    @Order(2)
    void createNewStudentTestSuccessfully() {
        /* Arrange */
        Student newStudent = new Student("Absalom","abs@gmail.com",LocalDate.of(1920,6,4));
        StudentDTO newStudentDTO = testMapper.toDto(newStudent);

        /* Act */
        testService.addNewStudent(newStudentDTO);
        Optional<Student> result = testRepo.findStudentByEmail("abs@gmail.com");

        /* Assert */
        assertNotNull(result);
        assertEquals(newStudent.getName(), result.get().getName());
        assertEquals(newStudent.getEmail(), result.get().getEmail());
        assertEquals(newStudent.getDob(), result.get().getDob());
    }

    @Test
    @Order(3)
    void updateStudentTestSuccessfully() {
        /* Arrange */
        Optional<Student> foundStudent = testRepo.findStudentByEmail("abs@gmail.com");
        String newName = "Michel";
        StudentDTO updatedDTO = new StudentDTO();
        updatedDTO.setName(newName);

        /* Act */
        testService.updateStudent(foundStudent.get().getId(), updatedDTO);

        /* Assert */
        Optional<Student> updatedStudent = testRepo.findById(foundStudent.get().getId());
        assertEquals(newName, updatedStudent.get().getName());
    }

    @Test
    @Order(4)
    void updateStudentTestError() {
        /* Arrange */
        Optional<Student> foundStudent = testRepo.findStudentByEmail("abs@gmail.com");
        String newName = "Michel";
        StudentDTO updatedDTO = new StudentDTO();
        updatedDTO.setName(newName);
        updatedDTO.setName("Gilbert");

        /* Act */
        testService.updateStudent(foundStudent.get().getId(), updatedDTO);

        /* Assert */
        Optional<Student> updatedStudent = testRepo.findById(foundStudent.get().getId());
        assertNotEquals(newName, updatedStudent.get().getName());
    }

    @Test
    @Order(5)
    void deleteStudentTestError() {
        /* Arrange */
        Optional<Student> foundStudent = testRepo.findStudentByEmail("axel@gmail.com");

        /* Act */
        testService.deleteStudent(foundStudent.get().getId());

        /* Assert */
        Optional<Student> deletedStudent = testRepo.findStudentByEmail("abs@gmail.com");
        assertTrue(deletedStudent.isPresent());
    }

    @Test
    @Order(6)
    void deleteStudentTestSuccessfully() {
        /* Arrange */
        Optional<Student> foundStudent = testRepo.findStudentByEmail("abs@gmail.com");

        /* Act */
        testService.deleteStudent(foundStudent.get().getId());

        /* Assert */
        Optional<Student> deletedStudent = testRepo.findStudentByEmail("abs@gmail.com");
        assertFalse(deletedStudent.isPresent());
    }
}