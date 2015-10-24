package sample;

import com.firebase.client.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    //Initialize the program
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");

        //Event that listens for changes in the database
        myFirebase.addValueEventListener(new ValueEventListener() {
            /*uses on data change function*/
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*prints out current count posts/tables in the database*/
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " bidding posts");
                /*a for loop iterating with how many items in it(db)*/
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    /*Selects only a value which will be used
                    Hämtar title och lägger i itemsMap*/
                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
                    itemsMap.put(bidItem.getTitle(), bidItem);
                }
                updateDescription("Snabb");//sträng med namnet på title i firebase för att starta metoden och
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void updateDescription(String item) {

        //Set information to variable
        String description = itemsMap.get(item).getDescription();
        String type = itemsMap.get(item).getType();
        String title = itemsMap.get(item).getTitle();

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
        String itemN = itemsMap.get(item).getType();

        /*Information to TextBox*/
        itemDescription.setText(
                "Type: " + type + "\n" +
                "Title: " + title + "\n" +
                "Description: \n" + description + "\n" +
                "Start price: " + startPrice + "\n" +
                "Item: " + itemN + "\n" +
                "Item sold: " + sold);

        //Add all bids to array
        allBids.add(""+currPrice+"\n");
        //Reverse order of bids
        Collections.reverse(allBids);

        //Information to TextBox / TextView
        bidHistory.setText("" + allBids + "\n");
        highestBid.setText(""+currPrice+"");

        /*Timer som räknar ner i sekunder*/
        int seconds = 60;

        for (int i = seconds; i>=0; i--) {
            try {
                sleep(1000);
                countdown.setText("Time left: " + i + " seconds");
                //myFirebase.child(sellerID).child("timer").setValue(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //försöker att stänga connection (skapas flera mains just nu) och kanske rensa guin för nästa item
    public void cleanup() {
        /*myFirebase.removeEventListener();
        mModels.clear();
        mKeys.clear();*/
    }

    //körs i main för att terminata connection o eventlistener som just nu inte har något namn och kan därför inte kalla listenern
    public void onStop(){
        /*myFirebase.getRoot().removeEventListener();
        Firebase.goOffline();
        cleanup();*/
    }
}


//bortkommenterat men kommer att användas ifall bidds ligger i items, just nu är inte strukturen korrekt i firebase
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

/*good way to generate a new image src and add it to imageview*/
        /*start
File file = new File("src/img.jpg");
Image newImage = new Image(file.toURI().toString());
itemImage.setImage(newImage);
        end*/

//firebase exempel som klyddar men ska vara korrekt
//                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
//                    System.out.println(bidItem.getTitle() + " - " + bidItem.getDescription());