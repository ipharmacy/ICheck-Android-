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
    private String rate;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;

    public reviews(String id, String review, String user, String rate) {
        this.id = id;
        this.review = review;
        this.rate = rate;
    }

    public reviews(String review, String user, String rate) {
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


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
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
