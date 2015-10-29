package sample;

import com.firebase.client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sun.misc.BASE64Decoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class Controller implements Initializable {

    //connects the ids from GUI
    @FXML public TextField countdown;
    @FXML public TextArea itemDescription;
    @FXML public TextArea bidHistory;
    @FXML public TextField highestBid;
    @FXML public ImageView itemImage;
    @FXML public Button button;
    BidItem bidItem;
    Image newImage;
//    @FXML public Button button1;


    //Creates a connection to Firebase Items
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/items");

    private Firebase myFirebaseUsers = new Firebase("https://biddme.firebaseio.com/users");

    //Creates a two lists which holds the FirebaseItems and FirebaseUsers
    private List<BidItem> fireBaseItems = new ArrayList<BidItem>();
    private List<BidUsers> fireBaseUsers = new ArrayList<BidUsers>();


    private int highestBidder = 0;
    private int latestBid = 0;
    private int winnerBid=0;


    private HashMap<String, BidItem> itemsMap = new HashMap<String, BidItem>();

    //Array to store all bids
    private ArrayList<String> allBids = new ArrayList();

    private static int seconds;
    private int TIME = 30;

    private String titleName;
    private String buyerId;
    private String itemId;
    private Boolean isSold;
    private String imageString;
    private String description;
    private String type;
    private String formattedBidArray;
    private Integer currPrice;
    private Integer startPrice;

    private HashMap<String, Object> hashMapItem;

    //Initialize the program
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goOnline();
        System.out.println("Initialize data...");
        hashMapItem = new HashMap<>();
        seconds = TIME;

        myFirebaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " users");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    BidUsers bidUsers = postSnapshot.getValue(BidUsers.class);
                    fireBaseUsers.add(bidUsers);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        myFirebase.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                /*Selects only a value which will be used
                    H�mtar title och l�gger i itemsMap*/
                bidItem = snapshot.getValue(BidItem.class);
                fireBaseItems.add(bidItem);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final BidItem bidItem = dataSnapshot.getValue(BidItem.class);
                System.out.println("Child updated");
                updateDescription(bidItem);
                if (bidItem.getUpForSale() && !bidItem.isSold() && seconds == TIME) {

                    System.out.println("Update Timer");
                    final Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            countdown.setText("Time left: " + --seconds);
                            if (seconds == 0) {
                                System.out.println("Entered seconds ==0 if sats");
                                myFirebase.child(bidItem.getId()).child("sold").setValue(true);
                                myFirebase.child(bidItem.getId()).child("upForSale").setValue(false);
                                fireBaseItems.remove(0);

                                timer.cancel();

                                seconds = TIME;
                                clearGUI();
                                myFirebase.child(fireBaseItems.get(0).getId()).child("sold").setValue(false);
                                myFirebase.child(fireBaseItems.get(0).getId()).child("upForSale").setValue(true);


                            }
                        }
                    }, 1000, 1000);
                }
                try {
                    for (int i = 0; i < fireBaseUsers.size(); i++) {

                        if (bidItem.getBids().get(fireBaseUsers.get(i).getId()) != null) {
                            latestBid = bidItem.getBids().get(fireBaseUsers.get(i).getId());
                            bidHistory.setText("bidds " + fireBaseUsers.get(i).getUsername() + " with amount of " +latestBid);


                            if (latestBid > highestBidder) {
                                highestBidder = latestBid;
                                highestBid.setText(fireBaseUsers.get(i).getUsername() + " with amount of " + highestBidder);
                                String newBid = String.valueOf(highestBidder);
                                allBids.add(""+ newBid + "\n");

                            }
                            System.out.println("bidds" + fireBaseUsers.get(i).getUsername() + " with amount of " + latestBid);
                            bidHistory.setText("bidds " + fireBaseUsers.get(i).getUsername() + " with amount of " +latestBid);
                            Collections.reverse(allBids);
                            formattedBidArray = allBids.toString()
                                    .replace(",", "")  //remove the commas
                                    .replace("[", "")  //remove the left bracket
                                    .replace("]", "")  //remove the right bracket
                                    .trim();           //remove trailing spaces from partially initialized arrays
                            bidHistory.setText(" " + formattedBidArray + "\n");
                            bidHistory.autosize();
                            bidHistory.setWrapText(true);
                        }

                    }
                } catch (NullPointerException e) {
                    System.out.println("Nullpointer");
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

    private void clearGUI() {
        System.out.println("GUI cleared!");
        allBids.clear();
        highestBid.clear();
        bidHistory.clear();
        //countdown.clear();
        itemDescription.clear();
        //itemImage.setImage(null);
    }

    public void updateDescription(BidItem item) {
        System.out.println("Updated Description!");
        button.setDisable(true);
        /*Decodes the image string and sets it as new variable for imageview*/
        imageString = item.getImage();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            ByteArrayInputStream decodedImage = new ByteArrayInputStream(base64Decoder.decodeBuffer(imageString));
            newImage = new Image(decodedImage);
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

        //t�mmer bidhistory array
       // allBids.clear();
//        allBids.removeAll(allBids);
//        Arrays.fill(allBids, null);
//        allBids.removeAll(Arrays.asList("", null));

    }

    public void goOnline() {
        myFirebase.goOnline();
    }

    public void onStop(){
        myFirebase.goOffline();
    }

    public void buttonClicked(ActionEvent event) {
        System.out.println("Button was clicked, auction started!");
        BidItem bidItem = fireBaseItems.get(0);
        myFirebase.child(bidItem.getId()).child("upForSale").setValue(true);

    }
}