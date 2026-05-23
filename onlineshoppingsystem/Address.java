package onlineshoppingsystem;

public class Address {

    private int addressId;

    private String street;
    private String city;
    private String province;
    private String country;
    private String zipCode;
    private String label;

    // Auto ID generator
    private static int idCounter = 1;

    public Address(String street, String city, String province, String country, String zipCode, String label) {

        try {
            // Validation checks
            if (street == null || street.trim().isEmpty()) {
                throw new IllegalArgumentException("Street cannot be empty!");
            }

            if (city == null || city.trim().isEmpty()) {
                throw new IllegalArgumentException( "City cannot be empty!");
            }

            if (province == null || province.trim().isEmpty()) {
                throw new IllegalArgumentException("Province cannot be empty!");
            }

            if (country == null || country.trim().isEmpty()) {
                throw new IllegalArgumentException("Country cannot be empty!");
            }

            if (zipCode == null || zipCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Zip code cannot be empty!");
            }

            if (label == null || label.trim().isEmpty()) {
                throw new IllegalArgumentException( "Label cannot be empty!");
            }

        this.addressId = idCounter++;

        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zipCode = zipCode;
        this.label = label;

    } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }


    // Full address
    public String getFullAddress() {

        try {
            return label + ": "
                + street + ", "
                + city + ", "
                + province + ", "
                + country + " - "
                + zipCode;

    } catch (Exception e) {

            return "Error generating full address.";
        }
    }

    // Getters
    public int getAddressId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLabel() {
        return label;
    }


    // Setters
    public void setStreet(String street) {
            try {
                if (street == null || street.trim().isEmpty()) {
                    throw new IllegalArgumentException("Street cannot be empty!");
                }

        this.street = street;

        }catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    
    public void setCity(String city) {
        try {
            if (city == null || city.trim().isEmpty()) {
                throw new IllegalArgumentException("City cannot be empty!");
            }

            this.city = city;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setLabel(String label) {
        try {
            if (label == null || label.trim().isEmpty()) {
                throw new IllegalArgumentException("Label cannot be empty!");
            }

        this.label = label;

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // toString
    @Override
    public String toString() {

        return "[" + label + "] " + getFullAddress();
    }
}
