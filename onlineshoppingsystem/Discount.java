package onlineshoppingsystem;
import java.time.LocalDate;
public class Discount {
    private double percentage;
    private String code;
    private LocalDate expiryDate;

    public Discount(double percentage, String code, LocalDate expiryDate) {
        this.percentage = percentage;
        this.code = code;
        this.expiryDate = expiryDate;
    }

    public boolean isValid() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isBefore(expiryDate) || currentDate.isEqual(expiryDate);
    }

    public double getDiscountedPrice(double originalPrice) {
        if (isValid()) {
            return originalPrice - (originalPrice * percentage / 100);
        }
        return originalPrice;
    }

    

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
