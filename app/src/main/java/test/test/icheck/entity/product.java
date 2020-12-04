package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

import test.test.icheck.entity.photoProduct;
import test.test.icheck.entity.reviews;

public class product {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private ArrayList<String> images;
    @SerializedName("brand")
    private String brand;
    @SerializedName("category")
    private String category;
    @SerializedName("address")
    private String address;
    @SerializedName("available")
    private String available;
    @SerializedName("rate")
    private Double rate;
    @SerializedName("__v")
    private int __v;
    @SerializedName("reviews")
    private ArrayList<reviews> reviews;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;

    public product(String id, String name, String description, ArrayList<String> images, String brand, String category, String address, String available, Double rate, String __v, ArrayList<reviews> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
        this.brand = brand;
        this.category = category;
        this.address = address;
        this.available = available;
        this.rate = rate;
        this.reviews = reviews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public ArrayList<test.test.icheck.entity.reviews> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<test.test.icheck.entity.reviews> reviews) {
        this.reviews = reviews;
    }

    public product() {
    }

    @Override
    public String toString() {
        return "product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", available='" + available + '\'' +
                ", rate='" + rate + '\'' +
                ", __v=" + __v +
                ", reviews=" + reviews +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
