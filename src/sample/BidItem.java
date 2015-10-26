package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Calle Von Anka on 2015-10-21.
 */
public class BidItem {

    //Retrieves items from database, for easier use in controller

//    private Map<String, Object> bids;
    private int currentPrice;
    private String description;
    private String id;
    private String idBuyer;
    private String idSeller;
    private String image;
    private boolean sold;
    private int startedPrice;
    private int timer;
    private String title;
    private String type;

    public BidItem() {
    }

    /*public Map<String, Object> getBids() {
        return bids;
    }*/

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

    public int getTimer() {
        return timer;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

}