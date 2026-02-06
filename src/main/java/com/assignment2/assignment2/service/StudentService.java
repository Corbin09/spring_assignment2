package com.assignment2.assignment2.service;

import com.assignment2.assignment2.dto.StudentRequest;
import com.assignment2.assignment2.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAll();

    Student getById(Long id);

    Student create(StudentRequest request);

    Student update(Long id, StudentRequest request);

    void delete(Long id);
}