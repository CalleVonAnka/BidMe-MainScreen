package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*creates the scene*/
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    /*should stop all activity here, including connection*/
    @Override
    public void stop() throws Exception {
        super.stop();
        //g�r ett nytt objekt av onstop f�r att kunna st�nga "main"-connection
        new Controller().onStop();
    }

}


/*ERROR MED UPPDATERING Push failed
Failed with error: fatal: unable to access 'https://github.com/CalleVonAnka/BidMe-MainScreen.git/': Failed connect to github.com:443; No error
G�r inte att commita om, s� skapar �ndring i filen via denna kommentar, ta bort denna kommentar sen!
*/