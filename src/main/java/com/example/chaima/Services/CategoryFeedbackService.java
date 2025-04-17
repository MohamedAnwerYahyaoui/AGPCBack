package com.example.chaima.Services;

import com.example.chaima.Entities.CategoryFeedback;
import com.example.chaima.Repositories.CategoryFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryFeedbackService {

    @Autowired
    private CategoryFeedbackRepository repository;

    public List<CategoryFeedback> getAll() {
        return repository.findAll();
    }

    public List<CategoryFeedback> getByEmployeeId(Integer employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public CategoryFeedback save(CategoryFeedback feedback) {
        return repository.save(feedback);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Map<String, Double> getAverageRatingsPerCategory() {
        List<CategoryFeedback> all = repository.findAll();
        Map<String, List<Integer>> grouped = new HashMap<>();

        for (CategoryFeedback feedback : all) {
            grouped
                    .computeIfAbsent(feedback.getCategory(), k -> new ArrayList<>())
                    .add(feedback.getNote());
        }

        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : grouped.entrySet()) {
            List<Integer> notes = entry.getValue();
            double avg = notes.stream().mapToInt(i -> i).average().orElse(0.0);
            averages.put(entry.getKey(), avg);
        }

        return averages;
    }

}
