package com.example.chaima.Controllers;

import com.example.chaima.Entities.CategoryFeedback;
import com.example.chaima.Repositories.EmployeeRepository;
import com.example.chaima.Services.CategoryFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category-feedbacks")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryFeedbackController {

    @Autowired
    private CategoryFeedbackService service;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<CategoryFeedback> getAll() {
        return service.getAll();
    }

    @GetMapping("/user")
    public List<CategoryFeedback> getByUser(@RequestParam Integer userId) {
        return service.getByEmployeeId(userId);
    }

    @PostMapping
    public CategoryFeedback create(@RequestBody CategoryFeedback feedback) {
        feedback.setEmployee(employeeRepository.findById(feedback.getEmployee().getId()).get());
        System.out.println("Received: " + feedback);
        return service.save(feedback);
    }

    @PutMapping
    public CategoryFeedback update(@RequestBody CategoryFeedback feedback) {
        return service.save(feedback);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/averages")
    public Map<String, Double> getCategoryAverages() {
        return service.getAverageRatingsPerCategory();
    }

}
