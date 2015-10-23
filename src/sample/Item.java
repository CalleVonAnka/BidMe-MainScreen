package sample;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Calle Von Anka on 2015-10-21.
 */
public class Item {
    private int currentprice;
    private String description;
    private String image;
    private long price;
    private String seller;
    private boolean sold;
    private String title;
    private String type;

    public Item() {

    }

    public int getCurrentprice() {
        return currentprice;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() { return image; }

    public long getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public boolean isSold() {
        return sold;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
