package sample;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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
                for (DataSnapshot postSnapchot : dataSnapshot.getChildren()){
                    item = postSnapchot.getValue(HashMap.class);
                    fireBaseItems.add((HashMap<String,Object>) item);

                    System.out.println(dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }





}
