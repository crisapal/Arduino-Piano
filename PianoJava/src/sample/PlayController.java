package sample;
import com.fazecast.jSerialComm.SerialPort;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayController implements Initializable {

    @FXML
    private ImageView backView,logoView,backgroundView;

    @FXML
    private Label songNameLabel;

    @FXML
    private Text lyricsText;

    private String notes;
    private String recordedSong;
    SerialPort sp;


    public PlayController(){
        sp = SerialPort.getCommPort("/dev/cu.usbserial-14120");
        // connection setting according .ino project
        sp.setComPortParameters(9600, 8, 1, 0);
        // block until bytes can be written
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }
    }

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
        //closing UART connection each time I switch to a new screen
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }

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



    //todo: sending the notes to the LCD, start recording and open connection
    public void startPlayingOnAction(ActionEvent event){
        sp.writeBytes(new byte[]{1},1);
        byte[] name= songNameLabel.getText().getBytes();
        sp.writeBytes(name,name.length);


    }

    //end connection, work only if something was played
    public void stopPlayingOnAction(ActionEvent event){
        sp.writeBytes(new byte[]{2},1);


    }

    //open new connection and send the recorded notes, work only if something stoped playing
    public void listenOnAction(ActionEvent event){

    }

    //compare through pattern matching, work only if something was once played
    public void viewProgressOnAction(ActionEvent event){
        //closing UART connection each time I switch to a new screen
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }

        try{
            URL url = new File("src/sample/progress.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            ProgressController myController=(ProgressController) loader.getController();
            myController.getSongNameLabel().setText(songNameLabel.getText());
            myController.getGoodNotesText().setText(notes);

            //todo: add score formula, progress level, pattern matching on my notes
            Scene songsScene= new Scene(root, 425,550);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(songsScene);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public Label getSongNameLabel() {
        return songNameLabel;
    }

    public void setSongNameLabel(Label songNameLabel) {
        this.songNameLabel = songNameLabel;
    }

    public Text getLyricsText() {
        return lyricsText;
    }

    public void setLyricsText(Text lyricsText) {
        this.lyricsText = lyricsText;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
