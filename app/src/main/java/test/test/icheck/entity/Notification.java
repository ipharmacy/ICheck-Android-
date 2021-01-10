package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("_id")
    private String id;
    @SerializedName("receiver")
    private Customer receiver;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
    @SerializedName("type")
    private String type;
    @SerializedName("link")
    private String link;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public Notification(Customer receiver, String title, String description, String image, String type, String link, String createdAt, String updatedAt) {
        this.receiver = receiver;
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.link = link;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Notification(String id, Customer receiver, String title, String description, String image, String type, String link, String createdAt, String updatedAt) {
        this.id = id;
        this.receiver = receiver;
        this.title = title;
        this.description = description;
        this.image = image;
        this.type = type;
        this.link = link;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Notification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
