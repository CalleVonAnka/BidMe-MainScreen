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
import java.io.File;
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

    //Creates a connection to firebase
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/items");

    //Creates a list which holds the firebaseitems and a hashmap
    private List<HashMap<String,Object>> fireBaseItems = new ArrayList<HashMap<String, Object>>();
    private HashMap<String, BidItem> itemsMap = new HashMap<String, BidItem>();
    private boolean itemsDownloaded = false;

    //Array to store all bids
    private ArrayList<String> allBids = new ArrayList();

    String buyerId;
    String itemId;

    //Initialize the program
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");
        goOnline();


        //Event that listens for changes in the database
        myFirebase.addValueEventListener(new ValueEventListener() {
            /*uses on data change function*/
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*prints out current count posts/tables in the database*/
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " bidding posts");
                /*a for loop iterating with how many items in it(db)*/
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    /*Selects only a value which will be used
                    H�mtar title och l�gger i itemsMap*/
                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
                    itemsMap.put(bidItem.getTitle(), bidItem);


                    String titleName = bidItem.getTitle().toString();
                    System.out.println(titleName);
                    updateDescription(titleName);//str�ng med namnet p� title i firebase f�r att starta metoden och
                }
                //timerSeconds();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void updateDescription(String item) {
        /*Decodes the image string and sets it as new variable for imageview*/
        String imageString = itemsMap.get(item).getImage();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            ByteArrayInputStream decodedImage = new ByteArrayInputStream(base64Decoder.decodeBuffer(imageString));
            Image newImage = new Image(decodedImage);
            itemImage.setImage(newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Set information to variable
        String description = itemsMap.get(item).getDescription();
        String type = itemsMap.get(item).getType();
        String title = itemsMap.get(item).getTitle();
        itemId = itemsMap.get(item).getId();
        buyerId = itemsMap.get(item).getIdBuyer();
        //If item is sold
        String sold;
        Boolean isSold = itemsMap.get(item).isSold();
        if(isSold){
            sold = "Yes";
        }else{
            sold = "No";
        }

        Integer startPrice = itemsMap.get(item).getStartedPrice();
        Integer currPrice = itemsMap.get(item).getCurrentPrice();


        /*Information to TextBox*/
        itemDescription.setText(
                "Type: " + type + "\n" +
                "Title: " + title + "\n" +
                "Description: \n" + description + "\n" +
                "Start price: " + startPrice + "\n" +
                "Item: " + type + "\n" +
                "Item sold: " + sold);

        //Add all bids to array
        allBids.add(""+currPrice+"\n");
        //Reverse order of bids
        Collections.reverse(allBids);

        //Information to TextBox / TextView
        //doesn't update with db at the moment, fix
        bidHistory.setText("" + allBids + "\n");
        highestBid.setText(""+currPrice+"");

        int seconds = 60;

        for (int i = seconds; i>=0; i--) {
            try {
                sleep(1000);
                countdown.setText("Time left: " + i + " seconds");
                myFirebase.child(itemId).child("timer").setValue(i);

                if(i == 0){
                    myFirebase.child(itemId).child("sold").setValue(true);
                    myFirebase.child(itemId).child("idBuyer").setValue("Petter");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void goOnline() {
        myFirebase.goOnline();
    }

    public void onStop(){
        myFirebase.goOffline();
    }
}


//bortkommenterat men kommer att anv�ndas ifall bidds ligger i items, just nu �r inte strukturen korrekt i firebase
        /*myFirebase.child("Items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BidItem newBid = dataSnapshot.getValue(BidItem.class);
                System.out.println("New bid: " + newBid.getBids());
                bidHistory.setText(newBid.getBids().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //System.out.println("Firebase read failed: "+ firebaseError.getMessage());
            }
        });*/


//firebase exempel som klyddar men ska vara korrekt
//                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
//                    System.out.println(bidItem.getTitle() + " - " + bidItem.getDescription());