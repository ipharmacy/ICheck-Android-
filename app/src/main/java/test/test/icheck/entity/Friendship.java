package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

public class Friendship {
    @SerializedName("_id")
    private String id;
    @SerializedName("Accepted")
    private String email;

    @SerializedName("user")
    private Customer customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Friendship(String id, String email, Customer customer) {
        this.id = id;
        this.email = email;
        this.customer = customer;
    }

    public Friendship() {
    }
}
