package Model;

import java.time.LocalDateTime;

public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String city;
    private int active;
    private LocalDateTime createDate;
    private String createdBy;
    private String lastUpdate;
    private String cityName;
    private String lastUpdateBy;
    private String phone;
    private int division;


    public Customer(int customerId, String customerName,
                    String address,
                     String postalCode, String phone,
                    int division, String createdBy,
                    String lastUpdate, String lastUpdateBy,
                    String cityName)
         {
         this.customerId=customerId;
        this.customerName = customerName;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;

        this.address=address;

        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.lastUpdate= lastUpdate;

             this.cityName = cityName;
         }

    //getters

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getDivision() {
        return division;
    }

    public String getPhone() {
        return phone;
    }

    public int getActive() {
        return active;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    @Override
    public String toString(){
        return (customerName);
    }

    // Setters

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBy(String updatedBy) {
        this.lastUpdateBy = updatedBy;
    }



}
