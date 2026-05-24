package onlineshoppingsystem;

import java.io.*;
import java.util.*;

public class Review implements Serializable{
    private int reviewId;
    private int rating;
    private String comment;
    private String reviewerName;

    public Review() {
        this.reviewId = 0;
        this.rating = 1;
        this.comment = "";
        this.reviewerName = "Anonymous";
    }

    public Review(int reviewId, int rating,
                  String comment, String reviewerName) {

        setReviewId(reviewId);
        setRating(rating);
        setComment(comment);
        setReviewerName(reviewerName);
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        if (reviewId <= 0) {
            throw new IllegalArgumentException(
                "Review ID must be greater than 0."
            );
        }
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(
                "Rating must be between 1 and 5."
            );
        }
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Comment cannot be empty."
            );
        }
        this.comment = comment;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        if (reviewerName == null || reviewerName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Reviewer name cannot be empty."
            );
        }
        this.reviewerName = reviewerName;
    }

    @Override
    public String toString() {
        return "Review by " + reviewerName +
               " | Rating: " + rating + "/5 | " +
               comment;
    }
}