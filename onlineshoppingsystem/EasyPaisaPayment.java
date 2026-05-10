package onlineshoppingsystem;

public class EasyPaisaPayment extends Payment {
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

