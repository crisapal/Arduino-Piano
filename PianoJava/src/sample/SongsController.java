package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SongsController implements Initializable {

    @FXML
    private ImageView backgroundView,logoView,backView;

    @FXML
    private VBox mySongsStack;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
       File backgroundFile = new File("Assets/340434.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundView.setImage(backgroundImage);

        File logoFile = new File("Assets/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoView.setImage(logoImage);

        File backFile = new File("Assets/back.png");
        Image backImage = new Image(backFile.toURI().toString());
        backView.setImage(backImage);




        //initializing the very first songs
        for(Song song:SongsList.songList){
            Button songButton= new Button(song.getName());
            songButton.setPrefSize(386,73);
            songButton.setFont(Font.font("Avenir Book",17));

            songButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try{

                        URL url = new File("src/sample/play.fxml").toURI().toURL();
                        FXMLLoader loader = new FXMLLoader(url);
                        Parent root = loader.load();

                        PlayController myController=(PlayController) loader.getController();
                        myController.getSongNameLabel().setText(song.getName());
                        myController.getLyricsText().setText(song.getLyrics());
                        myController.setNotes(song.getNotes());

                        Scene songsScene= new Scene(root, 425,550);
                        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
                        primaryStage.setScene(songsScene);

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });

            mySongsStack.getChildren().add(0,(Node) songButton);
        }

    }

    @FXML
    public void addSongOnAction(ActionEvent event){
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("Add song to the list");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText("This feature will be available later!");

        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.showAndWait();

    }

    @FXML
    public void backOnAction(Event event){
        try{
            URL url = new File("src/sample/sample.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene mainMenuScene= new Scene(root, 380,550);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
