package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MessageChat implements Comparable<MessageChat> {
    @SerializedName("_id")
    private String id;
    @SerializedName("sender")
    private String sender;
    @SerializedName("receiver")
    private String receiver;
    @SerializedName("type")
    private String type;
    @SerializedName("message")
    private String message;
    @SerializedName("createdAt")
    private Date createdAt;
    @SerializedName("updatedAt")
    private Date updatedAt;

    public MessageChat() {
    }

    public MessageChat(String sender, String type, String message, Date createdAt) {
        this.sender = sender;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
    }

    public MessageChat(String id, String sender, String receiver, String type, String message, Date createdAt, Date updatedAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public int compareTo(MessageChat o) {
        return this.createdAt.compareTo(o.getCreatedAt());
    }
}
