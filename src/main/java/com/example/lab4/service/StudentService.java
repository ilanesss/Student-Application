package com.example.lab4.service;

import com.example.lab4.model.Student;
import com.example.lab4.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Seed database with initial data if table is empty
    @PostConstruct
    public void initData() {
        if (studentRepository.count() == 0) {
            addStudent(new Student(null, "Anel", "Bexeitova", "anel@iitu.edu.kz", 20, 100, null));
            addStudent(new Student(null, "Dariga", "Eralyyeva", "dariga@iitu.edu.kz", 20, 86, null));
            addStudent(new Student(null, "Amina", "Moldahanova", "amina@iitu.edu.kz", 21, 73, null));
            addStudent(new Student(null, "Noob", "Nooob", "noob@iitu.edu.kz", 21, 54, null));
            addStudent(new Student(null, "Loser", "Loooser", "loser@iitu.edu.kz", 24, 17, null));
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void addStudent(Student student) {
        if (student.getExam() < 0 || student.getExam() > 100) {
            throw new IllegalArgumentException("Exam result must be between 0 and 100.");
        }

        // Automatic Grade Calculation
        student.setMark(calculateMark(student.getExam()));

        // Save to PostgreSQL
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public String calculateMark(int examScore) {
        if (examScore >= 90) return "A";
        if (examScore >= 75) return "B";
        if (examScore >= 60) return "C";
        if (examScore >= 50) return "D";
        return "F";
    }
}