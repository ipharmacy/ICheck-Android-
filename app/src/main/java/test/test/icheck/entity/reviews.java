package test.test.icheck.entity;

public class reviews {
    int id,image,rate;
    String name,review;

    public reviews(int image, int rate, String name, String review) {
        this.image = image;
        this.rate = rate;
        this.name = name;
        this.review = review;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
