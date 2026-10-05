package com.example.lab4.controller;

import com.example.lab4.model.Student;
import com.example.lab4.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    // Dependency injection of Service layer
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Home page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 2. Display all students
    @GetMapping("/students")
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    // 3. Open Add Student page
    @GetMapping("/students/add")
    public String addStudentPage(Model model) {
        model.addAttribute("student", new Student());
        return "addstud";
    }

    // 4. Handle form submission
    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student, Model model) {
        try {
            studentService.addStudent(student);
            return "redirect:/students";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("student", student);
            return "addstud";
        }
    }

    // 5. Display single student details
    @GetMapping("/students/{id}")
    public String getStudentDetails(@PathVariable("id") Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "studinfo";
    }

    // 6. Delete student
    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}