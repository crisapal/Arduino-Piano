package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ImageView backgroundView,pianoView,logoView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File backgroundFile = new File("Assets/340434.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundView.setImage(backgroundImage);

        File pianoFile = new File("Assets/piano.png");
        Image pianoImage = new Image(pianoFile.toURI().toString());
        pianoView.setImage(pianoImage);

        File logoFile = new File("Assets/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoView.setImage(logoImage);

    }

    public void songsListOnAction(ActionEvent event){
        try{
            URL url = new File("src/sample/songs.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene songsScene= new Scene(root, 380,550);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(songsScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void shuffleOnAction(ActionEvent event){
        //get random song to play
        Song myShuffledSong = SongsList.randomSong();

        try{

            URL url = new File("src/sample/play.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            PlayController myController=(PlayController) loader.getController();
            myController.getSongNameLabel().setText(myShuffledSong.getName());
            myController.getLyricsText().setText(myShuffledSong.getLyrics());
            myController.setNotes(myShuffledSong.getNotes());


            Scene songsScene= new Scene(root, 425,550);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(songsScene);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
