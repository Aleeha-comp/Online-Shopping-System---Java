package onlineshoppingsystem;

    public class CashOnDelivery extends Payment {
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

