package com.example.chaima.Controllers;

import com.example.chaima.Entities.Feedback;
import com.example.chaima.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin(origins = "http://localhost:4200")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/user")
    public List<Feedback> getUserFeedbacks(@RequestParam Integer userId) {
        return feedbackService.getUserFeedbacks(userId);
    }

    @GetMapping("/{id}")
    public Optional<Feedback> getFeedbackById(@PathVariable("id") Integer id) {
        return feedbackService.getFeedbackById(id);
    }

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        System.out.println("Received feedback: " + feedback);
        return feedbackService.saveFeedback(feedback);
    }

    @PutMapping
    public Feedback updateFeedback(@RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable("id") Integer id) {
        feedbackService.deleteFeedback(id);
    }
}
