package com.example.lab4.repository;

import com.example.lab4.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 1. Basic CRUD operations are ALREADY provided out-of-the-box by JpaRepository:
    // - save(Student student)           -> Adds a new student or updates an existing one
    // - findAll()                      -> Returns List<Student> of all records
    // - findById(Long id)              -> Returns Optional<Student> by ID
    // - deleteById(Long id)            -> Deletes a record by ID
    // - count()                        -> Returns total record count


    // 2. Custom Spring Data JPA Query Methods (Generated automatically by method naming convention):

    // Find a student by their unique email address
    Optional<Student> findByEmail(String email);

    // Find all students who achieved a specific mark/grade (e.g., "A", "B")
    List<Student> findByMark(String mark);

    // Find students whose last name contains a search keyword (case-insensitive)
    List<Student> findBySurnameContainingIgnoreCase(String surname);

    // Find all students who scored greater than or equal to a minimum exam score
    List<Student> findByExamGreaterThanEqual(int examScore);
}