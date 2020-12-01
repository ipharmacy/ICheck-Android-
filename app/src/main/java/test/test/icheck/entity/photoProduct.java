package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

public class photoProduct {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public photoProduct(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public photoProduct(String name) {
        this.name = name;
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
}
