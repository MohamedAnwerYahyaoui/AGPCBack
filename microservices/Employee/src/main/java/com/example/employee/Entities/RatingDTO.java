package com.example.employee.Entities;

public class RatingDTO {
    private long rating;

    public RatingDTO() {}

    public RatingDTO(long rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(long rating) {
        this.rating = rating;
    }
}
