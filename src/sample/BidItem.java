package sample;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Calle Von Anka on 2015-10-21.
 */
public class BidItem {

    private List<HashMap<String,Object>> listBids;
    private int currentprice;
    private String description;
    private int price;
    private String seller;
    private boolean sold;
    private String title;
    private String type;

    public BidItem() {
    }

    public List getBids() {
        return listBids;
    }

    public int getCurrentprice() {
        return currentprice;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
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
