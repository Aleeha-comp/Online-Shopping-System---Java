package onlineshoppingsystem;

public class EasyPaisaPayment extends Payment {
    private String phoneNumber;
    private static final double Transaction_Limit = 50000.0;
    

    public EasyPaisaPayment(int paymentId, double amount, String phoneNumber) {
        super(paymentId, amount);
        this.phoneNumber = phoneNumber;
    }

    public boolean isWithinTransactionLimit() {
        return getAmount() <= Transaction_Limit;
    }

    public void sendOTP() {
        if (!isWithinTransactionLimit()) {
            System.out.println("Cannot send OTP: Transaction exceeds limit of Rs. " + Transaction_Limit);
            return;
        }
        else{
            System.out.println("OTP sent to phone number: " + phoneNumber);
        }
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
        if (!validatePhoneNumber()) {
            System.out.println("Invalid phone number. Payment cannot be processed.");
            return;
        }
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

