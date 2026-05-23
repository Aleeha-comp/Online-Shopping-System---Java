
package onlineshoppingsystem;

import java.time.LocalDate;

public class Discount {
    private double percentage;
    private String code;
    private LocalDate expiryDate;

    public Discount(double percentage, String code, LocalDate expiryDate) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100. Provided: " + percentage);
        }
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Discount code cannot be null or empty.");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null.");
        }

        this.percentage = percentage;
        this.code = code.trim();
        this.expiryDate = expiryDate;
    }

    public boolean isValid() {
        if (expiryDate == null) {
            throw new IllegalStateException("Expiry date is not set. Cannot validate discount.");
        }
        LocalDate currentDate = LocalDate.now();
        return currentDate.isBefore(expiryDate) || currentDate.isEqual(expiryDate);
    }

    public double getDiscountedPrice(double originalPrice) {
        if (originalPrice < 0) {
            throw new IllegalArgumentException("Original price cannot be negative. Provided: " + originalPrice);
        }
        if (isValid()) {
            double discountedPrice = originalPrice - (originalPrice * percentage / 100);
            if (discountedPrice < 0) {
                throw new ArithmeticException("Discounted price resulted in a negative value. Check discount percentage.");
            }
            return discountedPrice;
        }
        return originalPrice;
    }

    // ----------- Setters with Validation -----------

    public void setPercentage(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100. Provided: " + percentage);
        }
        this.percentage = percentage;
    }

    public void setCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Discount code cannot be null or empty.");
        }
        this.code = code.trim();
    }

    public void setExpiryDate(LocalDate expiryDate) {
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null.");
        }
        this.expiryDate = expiryDate;
    }

    // ----------- Getters -----------

    public double getPercentage() {
        return percentage;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
