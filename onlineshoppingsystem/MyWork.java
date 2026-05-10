package onlineshoppingsystem;
import java.time.LocalDate;


abstract class Payment {
    private int paymentId;
    private double amount;

    public Payment(int paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract void processPayment();
}


class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(int paymentId, double amount, String cardNumber, String cvv) {
        super(paymentId, amount);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public boolean validateCard() {
        if (cardNumber == null) {
            System.out.println("Card number is null!");
            return false;
        }
        if (cvv == null) {
            System.out.println("CVV is null!");
            return false;
        }
        if (cardNumber.length() < 16) {
            System.out.println("Invalid card number length!");
            return false;
        }
        if (cvv.length() != 3) {
            System.out.println("Invalid CVV length!");
            return false;
        }
        return true;
    }


    public void processPayment() {
        if (validateCard()) {
            System.out.println("Processing credit card payment of $" + getAmount());
            // Either just print here 
        } else {
            System.out.println(validateCard());
        }
    }

    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}



class CashOnDelivery extends Payment {
    private boolean confirmDelivery;

    public CashOnDelivery(int paymentId, double amount, boolean confirmDelivery) {
        super(paymentId, amount);
        this.confirmDelivery = confirmDelivery;
    }


    public void processPayment() {
        if (confirmDelivery) {
            System.out.println("Processing Cash on Delivery payment of $" + getAmount());
            
        } else {
            System.out.println("Delivery not confirmed yet. Please confirm delivery first.");
        }
    }

    public void confirmReceipt() {
        if(!isConfirmDelivery()){
            System.out.println("Delivery not confirmed yet.");
            return;
        }
        else{
            System.out.println("Delivery confirmed. Cash payment will be processed.");
        }
    }

    public boolean isConfirmDelivery() {
        return confirmDelivery;
    }

    public void setConfirmDelivery(boolean confirmDelivery) {
        this.confirmDelivery = confirmDelivery;
    }
}


class EasyPaisaPayment extends Payment {
    private String phoneNumber;
    

    public EasyPaisaPayment(int paymentId, double amount, String phoneNumber) {
        super(paymentId, amount);
        this.phoneNumber = phoneNumber;
    }

    public void sendOTP() {
        System.out.println("OTP sent to phone number: " + phoneNumber);
    
    }


    public boolean validatePhoneNumber() {
        if(phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() < 11) {
            System.out.println("Invalid phone number.");
            return false;
        }
        else {
            return true;
        }
    }

    public void processPayment() {
        sendOTP();
        System.out.println("Processing EasyPaisa payment of $" + getAmount());
    
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}



class Discount {
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



public class Mywork {
    
}
