package song;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller2 {

    @FXML TextField album;
    @FXML TextField artist;
    @FXML Button back;
    @FXML TextField songName;
    @FXML TextArea text;
    @FXML Button update;
    @FXML TextField year;
    ObservableList<Song> obsList; 
    Song currentSong;
    int songIndex;

    public void display(Song song, ObservableList<Song> obs, int index){
        songIndex = index;
        String content = "Song Name: " + song.getSongName()
                           + "\nArtist: " + song.getArtist()
                           + "\nAlbum: " + song.getAlbum()
                           + "\nYear: " + song.getYear();
        text.setText(content);
        obsList = obs;
        currentSong = song;
        songName.setText(song.getSongName());
        artist.setText(song.getArtist());
        album.setText(song.getAlbum());
        year.setText(song.getYear());
    }

    @FXML
    public void mainView(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = loader.load();
        Controller1 controller = loader.getController();
        controller.start(obsList, songIndex);
        controller.passObs(obsList);

        Stage stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 420);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateInfo(ActionEvent event){
            String empty = "";
            if(songName.getText().equals(empty) || artist.getText().equals(empty)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setContentText("Both song and artist are required.");
                alert.setHeaderText("Information alert");
                alert.showAndWait();
                return;            
            }
            Boolean exists = false;
            for (Song element : obsList) {
                if (element.getSongName().equals(songName.getText()) && element.getArtist().equals(artist.getText())){
                    exists = true;
                }
            }
            if(exists == false){             
                currentSong.setSongName(songName.getText());
                currentSong.setArtist(artist.getText());
                currentSong.setAlbum(album.getText());
                currentSong.setYear(year.getText());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setContentText("Song already exists in library.");
                alert.setHeaderText("Information alert");
                alert.showAndWait();
            }
            display(currentSong, obsList, songIndex);
            fileExport();
    }

    public void fileExport(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/song/SaveFile.txt"));
            for (Song song : obsList) {
                if(song.getSongName()!=""){
                    writer.write("s^"+song.getSongName()+"^ ");
                }
                if(song.getArtist()!=""){
                    writer.write("a^"+song.getArtist()+"^ ");
                }
                if(song.getAlbum()!=""){
                    writer.write("l^"+song.getAlbum()+"^ ");
                }
                if(song.getYear()!=""){
                    writer.write("y^"+song.getYear()+"^ ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
