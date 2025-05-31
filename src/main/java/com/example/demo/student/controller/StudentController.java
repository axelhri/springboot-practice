package com.example.demo.student.controller;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public ResponseEntity<String> registerNewStudent(@RequestBody StudentDTO studentDTO) {
        studentService.addNewStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") UUID studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student deleted successfully");
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<String> updateStudent(
            @PathVariable UUID studentId,
            @RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(studentId, studentDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully");
    }
}
