package com.example.demo.Student.repository;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.mapper.StudentMapper;
import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.student.service.StudentService;
import org.junit.jupiter.api.AfterEach;
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

    @Autowired
    private StudentService testService;

    @Autowired
    private StudentMapper testMapper;

    @AfterEach
    void cleanTests() {
        testRepo.deleteAll();
    }

    @Test
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
    void updateStudentTestSuccessfully() {

        /* Arrange */
        Student studentToUpdate = new Student("Jean-Pierre","jp@gmail.com",LocalDate.of(1970,12,21));
        testRepo.save(studentToUpdate);
        String newName = "Michel";
        StudentDTO updatedDTO = new StudentDTO();
        updatedDTO.setName(newName);

        /* Act */
        testService.updateStudent(studentToUpdate.getId(), updatedDTO);

        /* Assert */
        Optional<Student> updatedStudent = testRepo.findById(studentToUpdate.getId());
        assertEquals(newName, updatedStudent.get().getName());
    }

    @Test
    void updateStudentTestError() {
        /* Arrange */
        Student studentToUpdate = new Student("Jean-Pierre","jp@gmail.com",LocalDate.of(1970,12,21));
        testRepo.save(studentToUpdate);
        String newName = "Michel";
        StudentDTO updatedDTO = new StudentDTO();
        updatedDTO.setName("Gilbert");

        /* Act */
        testService.updateStudent(studentToUpdate.getId(), updatedDTO);

        /* Assert */
        Optional<Student> updatedStudent = testRepo.findById(studentToUpdate.getId());
        assertNotEquals(newName, updatedStudent.get().getName());
    }

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
        assertEquals(email, foundStudent.get().getEmail());
    }
}