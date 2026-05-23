
package onlineshoppingsystem;

public class CashOnDelivery extends Payment {
    private boolean confirmDelivery;

    public CashOnDelivery(int paymentId, double amount, boolean confirmDelivery) {
        super(paymentId, amount);

        this.confirmDelivery = confirmDelivery;
    }

    // ----------- Process Payment -----------

    @Override
    public void processPayment() {
        try {
            validatePaymentState();

            if (confirmDelivery) {
                System.out.println("Processing Cash on Delivery payment...");
                System.out.println("Payment ID : " + getPaymentId());
                System.out.println("Amount     : $" + getAmount());
                System.out.println("Payment processed successfully.");
            } else {
                throw new IllegalStateException("Delivery not confirmed yet. Please confirm delivery first.");
            }

        } catch (Exception e) {
            System.out.println("Payment processing failed: " + e.getMessage());
        }
    }

    // ----------- Confirm Receipt -----------

    public void confirmReceipt() {
        try {
            if (!confirmDelivery) {
                throw new IllegalStateException("Cannot confirm receipt. Delivery has not been confirmed yet.");
            }
            System.out.println("Delivery confirmed. Cash payment will be processed.");

        } catch (Exception e) {
            System.out.println("Receipt confirmation failed: " + e.getMessage());
        }
    }

    // ----------- Setter -----------

    public void setConfirmDelivery(boolean confirmDelivery) {
        this.confirmDelivery = confirmDelivery;
    }

    // ----------- Getter -----------

    public boolean isConfirmDelivery() {
        return confirmDelivery;
    }
}