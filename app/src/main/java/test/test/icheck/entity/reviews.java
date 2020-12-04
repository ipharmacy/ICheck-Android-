package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class reviews {
    @SerializedName("_id")
    private String id;
    @SerializedName("review")
    private String review;
    @SerializedName("user")
    private Customer user;
    @SerializedName("rate")
    private Double rate;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;

    public reviews() {
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }



    public reviews(String id, String review, String user, Double rate) {
        this.id = id;
        this.review = review;
        this.rate = rate;
    }

    public reviews(String review, String user, Double rate) {
        this.review = review;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "reviews{" +
                "id='" + id + '\'' +
                ", review='" + review + '\'' +
                ", user=" + user +
                ", rate='" + rate + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
