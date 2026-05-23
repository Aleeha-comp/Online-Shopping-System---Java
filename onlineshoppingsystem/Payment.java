
package onlineshoppingsystem;

public abstract class Payment {
    private int paymentId;
    private double amount;

    public Payment(int paymentId, double amount) {
        if (paymentId <= 0) {
            throw new IllegalArgumentException("Payment ID must be a positive integer. Provided: " + paymentId);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero. Provided: " + amount);
        }

        this.paymentId = paymentId;
        this.amount = amount;
    }

    // ----------- Getters -----------

    public int getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    // ----------- Setters with Validation -----------

    public void setPaymentId(int paymentId) {
        if (paymentId <= 0) {
            throw new IllegalArgumentException("Payment ID must be a positive integer. Provided: " + paymentId);
        }
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero. Provided: " + amount);
        }
        this.amount = amount;
    }

    // ----------- Helper Method for Subclasses -----------

    protected void validatePaymentState() {
        if (paymentId <= 0) {
            throw new IllegalStateException("Cannot process payment: Invalid Payment ID → " + paymentId);
        }
        if (amount <= 0) {
            throw new IllegalStateException("Cannot process payment: Invalid amount → " + amount);
        }
    }

    // ----------- Abstract Method -----------

    public abstract void processPayment();
}
