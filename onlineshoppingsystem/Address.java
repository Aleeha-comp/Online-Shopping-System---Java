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

    public Address(String street, String city,
                   String province, String country,
                   String zipCode, String label) {

        this.addressId = idCounter++;

        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zipCode = zipCode;
        this.label = label;
    }

    // Validate address
    public boolean validate() {

        return street != null && !street.isEmpty()
            && city != null && !city.isEmpty()
            && zipCode != null && !zipCode.isEmpty();
    }

    // Full address
    public String getFullAddress() {

        return label + ": "
            + street + ", "
            + city + ", "
            + province + ", "
            + country + " - "
            + zipCode;
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
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // toString
    @Override
    public String toString() {

        return "[" + label + "] " + getFullAddress();
    }
}
