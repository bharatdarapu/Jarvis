package syr.labs.jarvis.app.bean;

/**
 * Created by Bharat on 8/2/2014.
 */
public class RowItem {
    private int imageId;
    private String title;

    public RowItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
