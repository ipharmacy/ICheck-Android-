package test.test.icheck.entity;

import com.google.gson.annotations.SerializedName;

public class ProductDetails {
    @SerializedName("product")
    private Product product;
    @SerializedName("isLiked")
    private String isLiked;

    public ProductDetails() {
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "product=" + product +
                ", isLiked='" + isLiked + '\'' +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }
}
