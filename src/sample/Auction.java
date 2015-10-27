package sample;

import com.firebase.client.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sun.misc.BASE64Decoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.Bidi;
import java.util.*;
import java.util.List;
/**
 * Created by marioabouraad on 2015-10-27.
 */
public class Auction implements Initializable {

    //connects the ids from GUI
    @FXML public TextField countdown;
    @FXML public TextArea itemDescription;
    @FXML public TextArea bidHistory;
    @FXML public TextField highestBid;
    @FXML public ImageView itemImage;
//    @FXML public Button button1;


    //Creates a connection to firebase
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/items");

    //Creates a list which holds the firebaseitems and a hashmap
    private List<BidItem> fireBaseItems = new ArrayList<BidItem>();
    private HashMap<String, BidItem> itemsMap = new HashMap<String, BidItem>();

    //Array to store all bids
    private ArrayList<String> allBids = new ArrayList();

    private static int seconds;

    private String titleName;
    private String buyerId;
    private String itemId;
    private Boolean isSold;
    private String imageString;
    private String description;
    private String type;
    private String formattedBidArray;
    private Integer currPrice;
    private Integer highestPrice;
    private Integer startPrice;

    private HashMap<String, Object> hashMapItem;

    private Firebase auctionId = new Firebase("https://biddme.firebaseio.com/auction");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");
        hashMapItem = new HashMap<>();
        goOnline();


        //Event that listens for changes in the database
        myFirebase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println("There are " + dataSnapshot.getChildrenCount() + " bidding posts");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //kanske l�gga in bidhistory som egen funtion h�r
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase read failed: " + firebaseError.getMessage());
            }
        });

        myFirebase.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                /*Selects only a value which will be used
                    H�mtar title och l�gger i itemsMap*/
                BidItem bidItem = snapshot.getValue(BidItem.class);
                itemsMap.put(bidItem.getTitle(), bidItem);
                fireBaseItems.add(bidItem);





                /*titleName = bidItem.getTitle().toString();
                System.out.println(titleName);
                isSold = itemsMap.get(titleName).isSold();
                if (!isSold) {
                    System.out.println("Item is not sold");
                    updateDescription(titleName);//str�ng med namnet p� title i firebase f�r att starta metoden och
                    doInBackground(titleName);//det som ska k�ras vid sidan om
                }else{
                    ShowResults();
                }

                clearGUI();*/

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final BidItem bidItem = dataSnapshot.getValue(BidItem.class);

                if (bidItem.getUpForSale() == true && bidItem.isSold() == false) {
                    seconds = 20;
                    updateDescription(bidItem);
                    final Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            countdown.setText("Time left: " + --seconds);
                            Firebase auctionId = new Firebase("https://biddme.firebaseio.com/auction");
                            auctionId.child("id").setValue(itemId);

                            /**
                             * Algorithm for myself for tomorrow.
                             *
                             * 1. In the auction-child, save the current id of the item displayed for sale now
                             * 2. Create a child in that id called bids.
                             * 3. Make firebase store all the bids (and what user-id that bid came from)
                             * 4. Sort the list of bids with highestvalue first
                             * 5. Everytime a higherbid is made, add 5 more seconds
                             * 6. When time is up, the user with highest bid wins.
                             *
                             * -----"Maybes"------
                             *
                             * - 30-60 second pause before next item shows up
                             * - The auction child should empty its values after the auction ends?
                             * - Make a firebase-child with "solditems"
                             * - Move the soldItem from items to soldItems
                             *
                             * //Mario
                             *
                             *
                             */


                            currPrice = startPrice;

                            if(highestPrice>currPrice) {
                                currPrice = highestPrice;
                                seconds+=5;
                            }

                            if (seconds == 0) {
                                myFirebase.child(bidItem.getId()).child("sold").setValue(true);
                                myFirebase.child(bidItem.getId()).child("upForSale").setValue(false);
                                fireBaseItems.remove(0);
                                myFirebase.child(fireBaseItems.get(0).getId()).child("upForSale").setValue(true);
                                timer.cancel();

                            }
                        }
                    }, 1000, 1000);
                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void updateDescription(BidItem item) {
        /*Decodes the image string and sets it as new variable for imageview*/
        imageString = item.getImage();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            ByteArrayInputStream decodedImage = new ByteArrayInputStream(base64Decoder.decodeBuffer(imageString));
            Image newImage = new Image(decodedImage);
            itemImage.setImage(newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set information to variable
        description = item.getDescription();
        type = item.getType();

        itemId = item.getId();

        buyerId = item.getIdBuyer();

        startPrice = item.getStartedPrice();


        /*Information to TextBox*/
        itemDescription.setText(
                "Type: " + type + "\n" +
                        "Title: " + titleName + "\n" +
                        "Description: \n" + description + "\n" +
                        "Start price: " + startPrice + "\n" +
                        "Item: " + type + "\n"
        );


    }


    public void goOnline() {
        myFirebase.goOnline();
    }

    public void onStop(){
        myFirebase.goOffline();
    }
}
