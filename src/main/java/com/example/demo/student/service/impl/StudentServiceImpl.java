package com.example.demo.student.service.impl;

import com.example.demo.student.dto.StudentDTO;
import java.util.List;
import java.util.UUID;

public interface StudentServiceImpl {
    List<StudentDTO> getStudents();
    String getStudent(UUID studentId);
    void addNewStudent(StudentDTO studentDTO);
    void deleteStudent(UUID studentId);
    void updateStudent(UUID studentId, StudentDTO studentDTO);
}
