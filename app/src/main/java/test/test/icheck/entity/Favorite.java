package test.test.icheck.entity;
import com.google.gson.annotations.SerializedName;
public class Favorite  {
    @SerializedName("_id")
    private String id;
    @SerializedName("product")
    private Product product;

    public Favorite(String id, Product product) {
        this.id = id;
        this.product = product;
    }

    public Favorite() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
