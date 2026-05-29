package onlineshoppingsystem;

import java.io.*;
import java.util.*;

public class Review implements Serializable{
    private int reviewId;
    private int rating;
    private String reviewerName;

    //constructors
    public Review() {
        this.reviewId = 0;
        this.rating = 1;
        this.reviewerName = "Anonymous";
    }


    public Review(int reviewId, int rating, String reviewerName) {
        setReviewId(reviewId);
        setRating(rating);
        setReviewerName(reviewerName);
    }

    
    //getters
    public int getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }
    
    public String getReviewerName() {
        return reviewerName;
    }

    //setters
    public void setReviewId(int reviewId) {
        if (reviewId <= 0) {
            throw new IllegalArgumentException("Review ID must be greater than 0.");
        }
        this.reviewId = reviewId;
    }


    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }


    public void setReviewerName(String reviewerName) {
        if (reviewerName == null || reviewerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Reviewer name cannot be empty.");
        }
        this.reviewerName = reviewerName;
    }


    //display
    @Override
    public String toString() {
        return "Review by " + reviewerName + " | Rating: " + rating + "/5  ";
    }
}