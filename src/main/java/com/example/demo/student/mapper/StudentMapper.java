package com.example.demo.student.mapper;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDTO toDto(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setDob(student.getDob());
        dto.setAge(student.getAge());
        return dto;
    }

    public Student toEntity(StudentDTO dto) {
        return new Student(dto.getName(), dto.getEmail(), dto.getDob());
    }
}
