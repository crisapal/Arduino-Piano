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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
    private ImageView backView, logoView, backgroundView;

    @FXML
    private Label songNameLabel;

    @FXML
    private Text lyricsText;

    private String notes;
    private String recordedSong;
    SerialPort sp;
    int comanda = 0;


    public PlayController() {
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
    public void backOnAction(Event event) {
        //closing UART connection each time I switch to a new screen
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }

        try {
            URL url = new File("src/sample/sample.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene mainMenuScene = new Scene(root, 380, 550);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(mainMenuScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //play- max  150 notes keys
    public void startPlayingOnAction(ActionEvent event) {
        if (comanda == 0) { //if nothing was played yet
            byte[] play = {'P', 'L', 'A', 'Y'};
            byte[] name = (songNameLabel.getText() + "          ").getBytes();  //making sure we have 16 bytes to write

            byte[] notesBytes = new byte[150];
            for (int i = 0; i < 150; i++) {
                if (i < this.notes.length())
                    notesBytes[i] = this.notes.getBytes()[i];
                else
                    notesBytes[i] = ' ';
            }
            notesBytes[149] = '.';
            sp.writeBytes(play, 4);
            sp.writeBytes(name, 16);
            sp.writeBytes(notesBytes, 150);
            comanda = 1;
            recordedSong = "";
            System.out.println("play started");
        } else {
            getWarning("  Your song was already played! Refresh Arduino!");
        }


    }

    //end connection, work only if something was played
    public void stopPlayingOnAction(ActionEvent event) {
        if (comanda == 0) {
            getWarning(" No song was played to be stopped");
        } else {
            //song is played
            comanda = 0;
            byte[] stop = {'S', 'T', 'O', 'P'};
            sp.writeBytes(stop,4);
            System.out.println("Stopped");


        }

    }

    //open new connection and send the recorded notes, work only if something stoped playing
    public void listenOnAction(ActionEvent event) {
        if (comanda == 0) { //something was played or not at all


            byte[] crcrd = {'R', 'C', 'R', 'D'};
            sp.writeBytes(crcrd, crcrd.length);


            System.out.println("Recording played");
        } else {
            getWarning("you should stop playing first");
        }

    }

    //compare through pattern matching, work only if something was once played
    public void viewProgressOnAction(ActionEvent event) {

        //getting progress from pipe by reading recording
        byte[] recorded = new byte[150];
        System.out.println("Received:"+ sp.readBytes(recorded, 150));

        String rcrd=new String();
        for (byte bla : recorded)
            rcrd+=(char)bla;

        System.out.println("Sent recording"+rcrd);
        recordedSong=rcrd;

        //closing UART connection each time I switch to a new screen
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }

        try {
            URL url = new File("src/sample/progress.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            ProgressController myController = (ProgressController) loader.getController();
            myController.getSongNameLabel().setText(songNameLabel.getText());
            myController.getGoodNotesText().setText(notes);
            myController.getYourNotesText().setText(recordedSong);

            myController.setProgress();

            Scene songsScene = new Scene(root, 425, 550);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(songsScene);
        } catch (Exception e) {
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

    private void getWarning(String s) {


        File logoFile = new File("Assets/lock.png");
        Image image = new Image(logoFile.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Info- piano playing");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(s);
        dialog.setGraphic(imageView);

        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
}
