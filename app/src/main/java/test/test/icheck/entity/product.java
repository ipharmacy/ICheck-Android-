package test.test.icheck.entity;

public class product {
    int id,rate,productImage,brandImage;
    String name,brandName,address,available;

    public product(int rate, int productImage, int brandImage, String name, String brandName, String address, String available) {
        this.rate = rate;
        this.productImage = productImage;
        this.brandImage = brandImage;
        this.name = name;
        this.brandName = brandName;
        this.address = address;
        this.available = available;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public int getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(int brandImage) {
        this.brandImage = brandImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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
}
