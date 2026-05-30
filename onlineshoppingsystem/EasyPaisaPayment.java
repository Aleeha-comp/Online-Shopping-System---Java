
package onlineshoppingsystem;

public class EasyPaisaPayment extends Payment {
    private String phoneNumber;
    private static final double TRANSACTION_LIMIT = 50000.0;

    public EasyPaisaPayment(int paymentId, double amount, String phoneNumber) {
        super(paymentId, amount);

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (phoneNumber.length() != 11) {
            throw new IllegalArgumentException("Phone number must be exactly 11 digits.");
        }

        this.phoneNumber = phoneNumber.trim();
    }

    // ----------- Transaction Limit Check -----------

    public boolean isWithinTransactionLimit() {
        if (getAmount() > TRANSACTION_LIMIT) {
            throw new IllegalStateException("Transaction amount Rs." + getAmount() + " exceeds the limit of Rs." + TRANSACTION_LIMIT);
        }
        return true;
    }
    

    // ----------- Validate Phone Number -----------

    public boolean validatePhoneNumber() {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalStateException("Phone number is not set or empty.");
        }
        if (phoneNumber.length() != 11) {
            throw new IllegalStateException("Phone number must be exactly 11 digits.");
        }
        return true;
    }

    // ----------- Process Payment -----------

    
    public void processPayment() {
    try {
        validatePaymentState();

        if (validatePhoneNumber() && isWithinTransactionLimit()) {

            System.out.println("Processing EasyPaisa payment...");
            System.out.println("Payment ID   : " + getPaymentId());
            System.out.println("Amount       : Rs." + getAmount());
            System.out.println("Phone Number : " + phoneNumber);
            System.out.println("Payment processed successfully.");
        }

    } catch (Exception e) {
        System.out.println("Payment processing failed: " + e.getMessage());
    }
}

    // ----------- Setter with Validation -----------

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (phoneNumber.length() != 11) {
            throw new IllegalArgumentException("Phone number must be exactly 11 digits.");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    // ----------- Getter -----------

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

