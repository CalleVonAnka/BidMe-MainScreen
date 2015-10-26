package sample;

import com.firebase.client.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sun.misc.BASE64Decoder;

import javax.xml.crypto.Data;
import java.awt.*;
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
//    @FXML public Button button1;


    //Creates a connection to firebase
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/items");

    //Creates a list which holds the firebaseitems and a hashmap
    private List<HashMap<String,Object>> fireBaseItems = new ArrayList<HashMap<String, Object>>();
    private HashMap<String, BidItem> itemsMap = new HashMap<String, BidItem>();

    //Array to store all bids
    private ArrayList<String> allBids = new ArrayList();

    private Timer timer;
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
    private Integer startPrice;

    //Initialize the program
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");
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


                titleName = bidItem.getTitle().toString();
                System.out.println(titleName);
                isSold = itemsMap.get(titleName).isSold();
                if (!isSold) {
                    updateDescription(titleName);//str�ng med namnet p� title i firebase f�r att starta metoden och
                    doInBackground(titleName);//det som ska k�ras vid sidan om
                }else{
                    ShowResults();
                }

                clearGUI();

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
                System.out.println("Firebase read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void ShowResults() {


    }

    private void clearGUI() {
        bidHistory.clear();
    }


    public void updateDescription(String item) {

        /*Decodes the image string and sets it as new variable for imageview*/
        imageString = itemsMap.get(item).getImage();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            ByteArrayInputStream decodedImage = new ByteArrayInputStream(base64Decoder.decodeBuffer(imageString));
            Image newImage = new Image(decodedImage);
            itemImage.setImage(newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set information to variable
        description = itemsMap.get(item).getDescription();
        type = itemsMap.get(item).getType();

        itemId = itemsMap.get(item).getId();
        buyerId = itemsMap.get(item).getIdBuyer();

        startPrice = itemsMap.get(item).getStartedPrice();


        /*Information to TextBox*/
        itemDescription.setText(
                "Type: " + type + "\n" +
                        "Title: " + titleName + "\n" +
                        "Description: \n" + description + "\n" +
                        "Start price: " + startPrice + "\n" +
                        "Item: " + type + "\n"
        );

    }

    public void doInBackground(String item){

        currPrice = itemsMap.get(item).getCurrentPrice();

        //Add all bids to array
        allBids.add(""+currPrice+"\n");
        //Reverse order of bids
        Collections.reverse(allBids);



        formattedBidArray = allBids.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the left bracket
                .replace("]", "")  //remove the right bracket
                .trim();           //remove trailing spaces from partially initialized arrays

        //Information to TextBox / TextView
        //doesn't update with db at the moment, fix
        bidHistory.setText("" + formattedBidArray + "\n");
        highestBid.setText(""+currPrice+"");

        seconds = 20;

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countdown.setText("" + setInterval());
                myFirebase.child(itemId).child("timer").setValue(seconds);
                if (seconds == 0) {
                    myFirebase.child(itemId).child("sold").setValue(true);
                }
            }
        }, 1000, 1000);

        /*for (int i = seconds; i>=0; i--) {
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
        }*/
    }

    public static final int setInterval() {
        if (seconds == 0) {
            return seconds;
        }
        return --seconds;
    }


    public void goOnline() {
        myFirebase.goOnline();
    }

    public void onStop(){
        myFirebase.goOffline();
    }
}