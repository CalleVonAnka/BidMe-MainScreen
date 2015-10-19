package sample;

import com.firebase.client.Firebase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public TextField countdown;
    @FXML public TextArea itemDescription;
    @FXML public TextArea bidHistory;
    @FXML public TextField highestBid;

    private Firebase myFirebase = new Firebase("https://biddme.firebaseio.com/");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize data...");
        countdown.setText("Time function goes here");
    }
}
