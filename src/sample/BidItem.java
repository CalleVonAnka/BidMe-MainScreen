package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Calle Von Anka on 2015-10-21.
 */
public class BidItem {

    //Retrieves items from database, for easier use in controller

    private int currentPrice;
    private String description;
    private String id;
    private String idBuyer;
    private String idSeller;
    private String image;
    private boolean sold;
    private int startedPrice;
    private String title;
    private String type;

    public BidItem() {
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public String getImage() {
        return image;
    }

    public boolean isSold() {
        return sold;
    }

    public int getStartedPrice() {
        return startedPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

}