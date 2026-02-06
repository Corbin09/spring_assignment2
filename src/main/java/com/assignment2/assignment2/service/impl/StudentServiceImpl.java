package com.assignment2.assignment2.service.impl;

import com.assignment2.assignment2.dto.StudentRequest;
import com.assignment2.assignment2.entity.Student;
import com.assignment2.assignment2.exception.StudentNotFoundException;
import com.assignment2.assignment2.repository.StudentRepository;
import com.assignment2.assignment2.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public List<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Student getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student create(StudentRequest request) {
        validateAge(request.getDob());

        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Student student = new Student(
                null,
                request.getName(),
                request.getEmail(),
                request.getDob()
        );

        return repository.save(student);
    }

    @Override
    public Student update(Long id, StudentRequest request) {
        Student student = getById(id);
        validateAge(request.getDob());

        if (!student.getEmail().equals(request.getEmail())
                && repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setDob(request.getDob());

        return repository.save(student);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getById(id));
    }

    private void validateAge(LocalDate dob) {
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("Student must be at least 18 years old");
        }
    }
}