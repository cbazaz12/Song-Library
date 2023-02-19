//Christian Bazaz & Taegon Park
package song;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class Controller1 {

    @FXML
    ListView<Song> listView;

    ObservableList<Song> obsList; 
    Comparator<Song> songComparator;


    @FXML Button add;
    @FXML Button delete;
    @FXML Button edit;
    @FXML TextArea info;
    @FXML TextField album;
    @FXML TextField artist;
    @FXML TextField songName;
    @FXML TextField year;
    @FXML private Button addButton;   
    @FXML private Button deleteButton;

    public void start(ObservableList<Song> obs, int songIndex){
        obsList = obs;
        listView.setItems(obs);
        songComparator = Comparator.comparing(Song::getLongLowerCase);
        if(!obsList.isEmpty()){
            listView.getSelectionModel().select(songIndex);
            String content = "Song Name: " + listView.getSelectionModel().getSelectedItem().getSongName()
                           + "\nArtist: " + listView.getSelectionModel().getSelectedItem().getArtist()
                           + "\nAlbum: " + listView.getSelectionModel().getSelectedItem().getAlbum()
                           + "\nYear: " + listView.getSelectionModel().getSelectedItem().getYear();
            info.setText(content);

            FXCollections.sort(obsList, songComparator);
        }
    }

    @FXML
    public void infoView(ActionEvent event) throws Exception{
        if(!obsList.isEmpty()){
            int songIndex = listView.getSelectionModel().getSelectedIndex();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
            Parent root = loader.load();
            Controller2 controller2 = loader.getController();
            controller2.display(listView.getSelectionModel().getSelectedItem(), obsList, songIndex);
    
            Stage stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void updateInfo(){
        if (!obsList.isEmpty() && listView.getSelectionModel().getSelectedItem() != null){
            String content = "Song Name: " + listView.getSelectionModel().getSelectedItem().getSongName()
                    + "\nArtist: " + listView.getSelectionModel().getSelectedItem().getArtist()
                    + "\nAlbum: " + listView.getSelectionModel().getSelectedItem().getAlbum()
                    + "\nYear: " + listView.getSelectionModel().getSelectedItem().getYear();
            info.setText(content);
        }
    }

    public void passObs(ObservableList<Song> obs){
        obsList = obs;
    }  

    @FXML
    void deleteInfo(MouseEvent event) {
        if(!obsList.isEmpty()){
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            obsList.remove(selectedIndex);
            listView.getSelectionModel().select(selectedIndex);
            if(selectedIndex == 0 && obsList.size() ==0){
                 info.setText("No info");    
            }
            else{
               String content = "Song Name: " + listView.getSelectionModel().getSelectedItem().getSongName()
                           + "\nArtist: " + listView.getSelectionModel().getSelectedItem().getArtist()
                           + "\nAlbum: " + listView.getSelectionModel().getSelectedItem().getAlbum()
                           + "\nYear: " + listView.getSelectionModel().getSelectedItem().getYear();
                info.setText(content);
            }
            fileExport();
        }
    }

    @FXML
    void addInfo(MouseEvent event) {
        Song temp = new Song(songName.getText(), artist.getText(), album.getText(), year.getText());   
        //checks if both song and artist are filled
        String empty = "";
        if(songName.getText().equals(empty) || artist.getText().equals(empty)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Both song and artist are required.");
            alert.setHeaderText("Information alert");
            alert.showAndWait();
            return;            
        }
        //checks if song already exists
        Boolean exists = false;
        for (Song element : obsList) {
            if (element.getSongName().equals(songName.getText()) && element.getArtist().equals(artist.getText())){
                exists = true;
            }
        }
        if(exists == false){             
            obsList.add(temp);
            FXCollections.sort(obsList, songComparator);    
            int position = obsList.indexOf(temp);
            listView.getSelectionModel().select(position);
            updateInfo();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Song already exists in library.");
            alert.setHeaderText("Information alert");
            alert.showAndWait();
        }
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
