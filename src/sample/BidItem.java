package sample;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Calle Von Anka on 2015-10-21.
 */
public class BidItem {

    /*retrieves the items in database, for easier use in controller*/

    //den här klassen måste överenstämma med vad som finns som childs i items för att koden skall fungera korrekt

//    private List<HashMap<String,Object>> listBids;
    private int currentPrice;
    private String description;
    private String idBuyer;
    private String idSeller;
    private String image;
    //private long price;
    //private String seller;
    private boolean sold;
    private int startedPrice;
    private String title;
    private String type;
//    private int pincode;

    public BidItem() {
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public String getDescription() {
        return description;
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



/*ERROR MED UPPDATERING Push failed
Failed with error: fatal: unable to access 'https://github.com/CalleVonAnka/BidMe-MainScreen.git/': Failed connect to github.com:443; No error
Går inte att commita om, så skapar ändring i filen via denna kommentar, ta bort denna kommentar sen!
*/