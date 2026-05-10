package onlineshoppingsystem;

    public class CreditCardPayment extends Payment {
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

