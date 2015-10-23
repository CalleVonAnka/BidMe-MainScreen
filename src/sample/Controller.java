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
import java.text.Bidi;
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
    //anv�nder mappen items, ska g� att koda som child men fungerade inte - men n�r det skrevs h�r fungerade det
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/items");

    /*creates a list which holds the firebaseitems and a hashmap*/
    private List<HashMap<String,Object>> fireBaseItems = new ArrayList<HashMap<String, Object>>();
    private HashMap<String, BidItem> itemsMap = new HashMap<String, BidItem>();
    private boolean itemsDownloaded = false;

    /*reads when the program starts*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");

        /*using the ids to put in text*/
        itemDescription.setText("Seller: " + "GetSellerCalle \n" + "Description: \n" + "GetDescription \n" + "Start: " + "GetstartKR \n" + "Item: " + " GetItem");

        bidHistory.setText("GetBid\nArray/list");
        highestBid.setText("GetBidDesc");

        /*good way to generate a new image src and add it to imageview*/
        /*start*/
        File file = new File("src/img.jpg");
        Image newImage = new Image(file.toURI().toString());
        itemImage.setImage(newImage);
        /*end*/


        //ett tappert f�rs�k p� en enkel timer funktion som hade varit grymt ifall den fungerade!!
        /*int min = 3;
        int sec = 14;
        double totalSec = TimeUnit.MINUTES.toSeconds(min) + sec;
        String cunter = Double.toString(totalSec);*/

        //en annan timer funktion
       /* long startTime = System.currentTimeMillis();

        //long elapsedTime = System.nanoTime() - startTime;
        long elapsedTime = System.nanoTime() - startTime;
        long timeTillNextDisplayChange = 1000 - (elapsedTime % 1000);
        try {
            Thread.sleep(timeTillNextDisplayChange);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long elapsedSeconds = elapsedTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;

        String min = String.valueOf(elapsedMinutes);
        String sec = String.valueOf(secondsDisplay);
*/


        /*a event that listens for changes in values in the database*/
        myFirebase.addValueEventListener(new ValueEventListener() {
            /*uses on data change function*/
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*prints out current count posts/tables in the database*/
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " bidding posts");
                /*a for loop iterating with how many items in it(db)*/
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    //firebase exempel som klyddar men ska vara korrekt
//                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
//                    System.out.println(bidItem.getTitle() + " - " + bidItem.getDescription());

                    /*selects only a value which will be used*/
                    /*h�mtar title och l�gger i itemsMap*/
                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
                    itemsMap.put(bidItem.getTitle(), bidItem);


                }

                updateDescription("Snabb");//str�ng med namnet p� title i firebase f�r att starta metoden och
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase read failed: "+ firebaseError.getMessage());
            }
        });

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

        countdown.setText("Time left: "  + "seconds" );
        Timer.tjena(10);//kallar p� timer funktionen, men guin �ppnas inte f�rr�n den r�knat klart.. vilket �r konstigt
    }

    public void updateDescription(String item) {
        //H�mtar detta ifr�n givet item och returnerar
        System.out.println(itemsMap.get(item).getIdSeller());
        System.out.println(itemsMap.get(item).getDescription());
        System.out.println(itemsMap.get(item).getStartedPrice());
        System.out.println(itemsMap.get(item).getType());
    }

    //f�rs�ker att st�nga connection (skapas flera mains just nu) och kanske rensa guin f�r n�sta item
    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        //myFirebase.removeEventListener();
//        mModels.clear();
//        mKeys.clear();
    }

    //k�rs i main f�r att terminata connection o eventlistener som just nu inte har n�got namn och kan d�rf�r inte kalla listenern
    public void onStop(){
        //myFirebase.getRoot().removeEventListener();
        cleanup();
    }



}
