package sample;

import com.firebase.client.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
/*connects the ids from sample layout*/
    @FXML public TextField countdown;
    @FXML public TextArea itemDescription;
    @FXML public TextArea bidHistory;
    @FXML public TextField highestBid;
    @FXML public ImageView itemImage;

/*creates a connection to firebase calle myFirebase*/
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/items");

    /*creates a list which holds the firebaseitems and a hashmap*/
    private List<HashMap<String,Object>> fireBaseItems = new ArrayList<HashMap<String, Object>>();
    private Map<String, Object> item = new HashMap<String, Object>();

    /*reads when the program starts*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");

        /*using the ids to put in text*/
        //at the moment I'm using itemdescription for other stuff, that's why its commented away atm
        //itemDescription.setText("Seller: " + "GetSellerCalle \n" + "Description: \n" + "GetDescription \n" + "Start: " + "GetstartKR \n" + "Item: " + " GetItem");

        bidHistory.setText("GetBid\nArray/list");
        highestBid.setText("GetBidDesc");

        /*good way to generate a new image src and add it to imageview*/
        /*start*/
        File file = new File("src/img.jpg");
        Image newImage = new Image(file.toURI().toString());
        itemImage.setImage(newImage);
        /*end*/

        int min = 3;
        int sec = 14;
        double totalSec = TimeUnit.MINUTES.toSeconds(min) + sec;
        String cunter = Double.toString(totalSec);

        //Timer.countdownTimer();



        countdown.setText(cunter);

        myFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " items in Firebase");
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Item auctionItem = postSnapShot.getValue(Item.class);
                    System.out.println(auctionItem.getPrice());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
