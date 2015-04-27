package syr.labs.jarvis.app.bean;

/**
 * Created by Bharat on 8/6/2014.
 */
public class WebsiteResultItem {

    public int itemId;
    public String time;
    public String websiteUrl;

    // constructor
    public WebsiteResultItem(int itemId, String websiteUrl) {
        this.itemId = itemId;
        this.websiteUrl = websiteUrl;
    }

    public WebsiteResultItem(String time , String websiteUrl) {
        this.time = time;
        this.websiteUrl = websiteUrl;
    }

}
