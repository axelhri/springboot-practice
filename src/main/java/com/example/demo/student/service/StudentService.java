package com.example.demo.student.service;

import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.mapper.StudentMapper;
import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.student.service.impl.StudentServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentServiceImpl {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentDTO> getStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Student getStudent(UUID studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
    }

    @Override
    public void addNewStudent(StudentDTO studentDTO) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(studentDTO.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(studentMapper.toEntity(studentDTO));
    }

    @Override
    public void deleteStudent(UUID studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    @Transactional
    public void updateStudent(UUID studentId, StudentDTO dto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist"));

        if (dto.getName() != null && !dto.getName().isBlank() &&
                !Objects.equals(student.getName(), dto.getName())) {
            student.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank() &&
                !Objects.equals(student.getEmail(), dto.getEmail())) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(dto.getEmail());
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            student.setEmail(dto.getEmail());
        }
    }
}
