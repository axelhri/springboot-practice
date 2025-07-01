package com.example.demo.Student.service;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.mapper.StudentMapper;
import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.student.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    StudentService studentService;

    private StudentDTO studentDTO;
    private Student studentEntity;

    @BeforeEach
    void setup() {
        studentDTO = new StudentDTO();
        studentDTO.setName("Axel");
        studentDTO.setEmail("axel@gmail.com");
        studentDTO.setDob(LocalDate.of(2000, 4, 3));

        studentEntity = new Student();
        studentEntity.setName("Axel");
        studentEntity.setEmail("axel@gmail.com");
        studentEntity.setDob(LocalDate.of(2000, 4, 3));
    }

    @Test
    void addNewStudentSuccessfullyTest() {

        /* Arrange */
        Mockito.when(studentRepository.findStudentByEmail("axel@gmail.com"))
                .thenReturn(Optional.empty());

        Mockito.when(studentMapper.toEntity(studentDTO))
                .thenReturn(studentEntity);

        /* Act */
        studentService.addNewStudent(studentDTO);

        /* Assert */
        Mockito.verify(studentRepository, Mockito.times(1)).save(studentEntity);
    }

    @Test
    void getStudent() {
        /* Arrange */
        Mockito.when(studentRepository.findById(studentEntity.getId())).thenReturn(Optional.of(studentEntity));

        /* Act */
        String result = studentService.getStudent(studentEntity.getId());

        /* Assert */
        Assertions.assertEquals("Axel", result);
    }
}