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

public class Controller implements Initializable {
/*connects the ids from sample layout*/
    @FXML public TextField countdown;
    @FXML public TextArea itemDescription;
    @FXML public TextArea bidHistory;
    @FXML public TextField highestBid;
    @FXML public ImageView itemImage;

/*creates a connection to firebase calle myFirebase*/
    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com");

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
        countdown.setText("Time function goes here");

        //myFirebase.child("items");

        /*a event that listens for changes in values in the database*/
        myFirebase.addValueEventListener(new ValueEventListener() {
/*uses on data change function*/
            public void onDataChange(DataSnapshot dataSnapshot) {
/*prints out current count posts/tables in the database*/
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " bidding posts");
/*a for loop iterating with how many items in it(db)*/
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    //BidItem bidItem = postSnapshot.getValue(BidItem.class);
                    //System.out.println(bidItem.getTitle() + " - " + bidItem.getDescription());

/*selects only a value which will be used*/
                    System.out.println(postSnapshot.child("items/price").getValue());


                    //works and will be used later on but working on isolating data and retrieving it right at the moment
//                    item = postSnapshot.getValue(HashMap.class);
//                    fireBaseItems.add((HashMap<String,Object>) item);
//
//                    System.out.println(fireBaseItems.get(0).get("Title").toString());
//                    itemDescription.setText(fireBaseItems.get(0).get("Title").toString());

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //System.out.println("Firebase read failed: "+ firebaseError.getMessage());
            }
        });

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

    }

    //körs i main för att rensa data
    /*public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mKeys.clear();
    }

    public void onStop(){
        myFirebase.getRoot().removeEventListener();
        cleanup();
    }*/

}
