package com.example.chaima.Repositories;

import com.example.chaima.Entities.CategoryFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryFeedbackRepository extends JpaRepository<CategoryFeedback, Integer> {
    List<CategoryFeedback> findByEmployeeId(Integer employeeId);
    List<CategoryFeedback> findByCategory(String category);
}
