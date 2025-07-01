package com.example.demo.student.service.impl;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentServiceImpl {
    List<StudentDTO> getStudents();
    Student getStudent(UUID studentId);
    void addNewStudent(StudentDTO studentDTO);
    void deleteStudent(UUID studentId);
    void updateStudent(UUID studentId, StudentDTO studentDTO);
}
