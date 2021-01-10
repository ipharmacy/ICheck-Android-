package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

public class MessageToDecode {
    @SerializedName("type")
    String type;
    @SerializedName("senderId")
    String senderId;
    @SerializedName("message")
    String message;
    @SerializedName("receiverId")
    String receiverId;

    public MessageToDecode(String type, String senderId, String message, String receiverId) {
        this.type = type;
        this.senderId = senderId;
        this.message = message;
        this.receiverId = receiverId;
    }

    public MessageToDecode() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
