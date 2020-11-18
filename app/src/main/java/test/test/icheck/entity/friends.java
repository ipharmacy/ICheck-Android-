package test.test.icheck.entity;

public class friends {
    int id,friendImage;
    String friendName;

    public friends(int friendImage, String friendName) {
        this.friendImage = friendImage;
        this.friendName = friendName;
    }

    public int getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(int friendImage) {
        this.friendImage = friendImage;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
