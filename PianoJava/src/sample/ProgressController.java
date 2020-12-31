package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {
    @FXML
    private Label songNameLabel,goodNotesLabel,yourNotesLabel,scoreLabel,score;
    @FXML
    private Text goodNotesText,yourNotesText;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView logoView,backgroundView,backView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File backFile = new File("Assets/back.png");
        Image backImage = new Image(backFile.toURI().toString());
        backView.setImage(backImage);

        File logoFile = new File("Assets/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoView.setImage(logoImage);

        File backgroundFile = new File("Assets/340434.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundView.setImage(backgroundImage);
    }

    @FXML
    public void backOnAction(Event event){
        try{
            URL url = new File("src/sample/songs.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene mainMenuScene= new Scene(root, 380,550);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Label getSongNameLabel() {
        return songNameLabel;
    }

    public Label getGoodNotesLabel() {
        return goodNotesLabel;
    }

    public Label getYourNotesLabel() {
        return yourNotesLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getScore() {
        return score;
    }

    public Text getGoodNotesText() {
        return goodNotesText;
    }

    public Text getYourNotesText() {
        return yourNotesText;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
