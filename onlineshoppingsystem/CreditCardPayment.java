
package onlineshoppingsystem;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(int paymentId, double amount, String cardNumber, String cvv) {
        super(paymentId, amount);

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty.");
        }
        if (cardNumber.length() != 16) {
            throw new IllegalArgumentException("Card number must be exactly 16 digits.");
        }
        if (cvv == null || cvv.trim().isEmpty()) {
            throw new IllegalArgumentException("CVV cannot be null or empty.");
        }
        if (cvv.length() != 3) {
            throw new IllegalArgumentException("CVV must be exactly 3 digits.");
        }

        this.cardNumber = cardNumber.trim();
        this.cvv = cvv.trim();
    }

    // ----------- Validation -----------

    public boolean validateCard() {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalStateException("Card number is not set or empty.");
        }
        if (cvv == null || cvv.trim().isEmpty()) {
            throw new IllegalStateException("CVV is not set or empty.");
        }
        if (cardNumber.length() != 16) {
            throw new IllegalStateException("Card number must be exactly 16 digits.");
        }
        if (cvv.length() != 3) {
            throw new IllegalStateException("CVV must be exactly 3 digits.");
        }
        return true;
    }

    // ----------- Process Payment -----------

    
    public void processPayment() {
        try {
            validatePaymentState();

            if (validateCard()) {
                System.out.println("Processing credit card payment...");
                System.out.println("Payment ID  : " + getPaymentId());
                System.out.println("Amount      : $" + getAmount());
                System.out.println("Card Number : **** **** **** " + cardNumber.substring(12));
                System.out.println("Payment processed successfully.");
            }

        } catch (Exception e) {
            System.out.println("Payment processing failed: " + e.getMessage());
        }
    }

    // ----------- Setters with Validation -----------

    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty.");
        }
        if (cardNumber.length() != 16) {
            throw new IllegalArgumentException("Card number must be exactly 16 digits.");
        }
        this.cardNumber = cardNumber.trim();
    }

    public void setCvv(String cvv) {
        if (cvv == null || cvv.trim().isEmpty()) {
            throw new IllegalArgumentException("CVV cannot be null or empty.");
        }
        if (cvv.length() != 3) {
            throw new IllegalArgumentException("CVV must be exactly 3 digits.");
        }
        this.cvv = cvv.trim();
    }

    // ----------- Getters -----------

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }
}

