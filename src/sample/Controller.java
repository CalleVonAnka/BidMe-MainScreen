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

    @FXML public TextField countdown;
    @FXML public TextArea itemDescription;
    @FXML public TextArea bidHistory;
    @FXML public TextField highestBid;
    @FXML public ImageView itemImage;


    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/");
    private List<HashMap<String,Object>> fireBaseItems = new ArrayList<HashMap<String, Object>>();
    private Map<String, Object> item = new HashMap<String, Object>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");
        //itemDescription.setText("Seller: " + "GetSellerCalle \n" + "Description: \n" + "GetDescription \n" + "Start: " + "GetstartKR \n" + "Item: " + " GetItem");
        bidHistory.setText("GetBid\nArray/list");
        highestBid.setText("GetBidDesc");
        File file = new File("src/img.jpg");
        Image newImage = new Image(file.toURI().toString());
        itemImage.setImage(newImage);
        countdown.setText("Time function goes here");

        myFirebase.child("Items");

        myFirebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " bidding posts");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    BidItem bidItem = postSnapshot.getValue(BidItem.class);
                    System.out.println(bidItem.getTitle() + " - " + bidItem.getDescription());
//bortkommenterat men ska användas, har problem med databasen att hämta bids/bidders eftersom BidItem klassen måste veta exakt vad värdena i firebase heter
//                    item = postSnapshot.getValue(HashMap.class);
//                    fireBaseItems.add((HashMap<String,Object>) item);
//
//                    System.out.println(fireBaseItems.get(0).get("Title").toString());
//                    itemDescription.setText(fireBaseItems.get(0).get("Title").toString());

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Firebase read failed: "+ firebaseError.getMessage());
            }
        });

        myFirebase.addChildEventListener(new ChildEventListener() {
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
                System.out.println("Firebase read failed: "+ firebaseError.getMessage());
            }
        });

    }





}
