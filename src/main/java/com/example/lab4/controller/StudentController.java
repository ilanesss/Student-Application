package com.example.lab4.controller;

import com.example.lab4.model.Student;
import com.example.lab4.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String homePage() {
        return "index"; // or "index", matching your HTML file name in src/main/resources/templates/
    }

    // 1. List & Filter page
    @GetMapping("/students")
    public String getAllStudents(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "minExam", required = false) String minExam,
            @RequestParam(name = "maxExam", required = false) String maxExam,
            Model model) {

        Integer minScore = parseInteger(minExam);
        Integer maxScore = parseInteger(maxExam);

        List<Student> students = studentService.searchStudents(keyword, minScore, maxScore);

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minExam", minScore);
        model.addAttribute("maxExam", maxScore);

        return "students"; // students.html
    }

    // 2. Add Form Page - Mapped to both /students/add and /students/new
    // MUST be declared BEFORE @GetMapping("/students/{id}")
    @GetMapping({"/students/add", "/students/new"})
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "addstud"; // addstud.html
    }

    // 3. Save Student
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.addStudent(student);
        return "redirect:/students";
    }

    // 4. Delete Student
    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // 5. Details Page - Must come AFTER specific string paths like /students/add or /students/new
    @GetMapping("/students/{id}")
    public String getStudentDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "studinfo"; // studinfo.html
    }

    private Integer parseInteger(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}