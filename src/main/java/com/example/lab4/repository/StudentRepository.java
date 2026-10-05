package com.example.lab4.repository;

import com.example.lab4.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE " +
            "(:keyword IS NULL OR LOWER(s.name) LIKE LOWER(CAST(:keyword AS String)) " +
            " OR LOWER(s.surname) LIKE LOWER(CAST(:keyword AS String))) AND " +
            "(:minExam IS NULL OR s.exam >= :minExam) AND " +
            "(:maxExam IS NULL OR s.exam <= :maxExam)")
    List<Student> filterStudents(
            @Param("keyword") String keyword,
            @Param("minExam") Integer minExam,
            @Param("maxExam") Integer maxExam
    );
}