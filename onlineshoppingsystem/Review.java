
public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private String reviewerName;

    public Review() {
        this.reviewId = 0;
        this.rating = 0;
        this.comment = "";
        this.reviewerName = "Anonymous";
    }
    
    public Review(int reviewId, int rating, String comment, String reviewerName) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.reviewerName = reviewerName;
    }

    public int getRating() { return rating; }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review by " + reviewerName + " | Rating: " + rating + "/5 | " + comment;
    }
}