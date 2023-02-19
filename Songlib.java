package song;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Songlib extends Application {
    
    @Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("gui.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		ObservableList<Song> obsList = FXCollections.observableArrayList();
		readFile(obsList);
 
		Controller1 listController = loader.getController();
		listController.start(obsList, 0);


		Scene scene = new Scene(root, 600, 420);
		primaryStage.setTitle("Song Library App");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show(); 

	}

	public void readFile(ObservableList<Song> obsList){
		Scanner f = null;
		try {
			Scanner scan = new Scanner(new File("src/song/SaveFile.txt"));
			f = scan;
		} catch (FileNotFoundException e) {
			File file = new File("src/song/SaveFile.txt");
			try{
				file.createNewFile();
				return;
			} catch (Exception a) {
				System.out.println("Error occurred while creating new file");
				System.exit(0);
			}
		}

		while(f.hasNextLine()){
			String line = f.nextLine();
			Song s = new Song();
			int slash = line.indexOf('^');
			int slashPlace = slash;
			int end = line.lastIndexOf('^');
			while(slash!=end){
				slash = line.indexOf('^', slashPlace+1);
				if(line.charAt(slashPlace-1) == 's'){
					s.setSongName(line.substring(slashPlace+1, slash));
				}
				if(line.charAt(slashPlace-1) == 'a'){
					s.setArtist(line.substring(slashPlace+1, slash));
				}
				if(line.charAt(slashPlace-1) == 'l'){
					s.setAlbum(line.substring(slashPlace+1, slash));
				}
				if(line.charAt(slashPlace-1) == 'y'){
					s.setYear(line.substring(slashPlace+1, slash));
				}
				slashPlace = line.indexOf('^', slash+1);
			}
			obsList.add(s);
		}
	}

    public static void main(String[] args) {
		launch(args);
	}
}
