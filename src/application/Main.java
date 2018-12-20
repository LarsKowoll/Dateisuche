package application;

import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * GUI fuer die Suche nach Dateien in einem Verzeichnis inklusive aller Unterverzeichnisse
 * @author Lars, Philip
 *
 */
public class Main extends Application {
	
	String _dateipfad;
	int _anzahlDateien;
	
	/**
	 * Erstellt GUI
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Dateirecherche");
		
		Group wurzel = new Group();
		Scene szene = new Scene(wurzel, Color.WHITE);
		
		// Use a grid pane for organization
	    GridPane gridpane = new GridPane();
	    gridpane.setPadding(new Insets(15,15,15,15));
	    
	    Label labelTitel = new Label("Gefundene Dateien");
	    gridpane.add(labelTitel, 2, 0);
	    GridPane.setHalignment(labelTitel, HPos.CENTER);
	    wurzel.getChildren().add(gridpane);
	    
		TableView<Datei> table = new TableView<Datei>();
		table.setPrefWidth(350);
		table.setPrefHeight(300);
		
		ObservableList<Datei> datenSatz = FXCollections.<Datei> observableArrayList();
		table.setItems(datenSatz);
		gridpane.add(table, 2, 1);
		
		// Setup the first column: name
		TableColumn<Datei, String> nameCol = new TableColumn<Datei, String>("Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<Datei, String>("name"));
		nameCol.setPrefWidth(table.getPrefWidth() / 3);
		
		// Setup the second column: path
		TableColumn<Datei, String> pathCol = new TableColumn<Datei, String>("Pfad");
		pathCol.setMinWidth(100);
		pathCol.setCellValueFactory(new PropertyValueFactory<Datei, String>("path"));
		pathCol.setPrefWidth(table.getPrefWidth() / 3);
		
		// Setup the third colum: groesse
		TableColumn<Datei, Long> dataSizeCol = new TableColumn<Datei, Long>("Groesse (Byte)");
		dataSizeCol.setMinWidth(100);
		dataSizeCol.setCellValueFactory(new PropertyValueFactory<Datei, Long>("dataSize"));
		dataSizeCol.setPrefWidth(table.getPrefWidth() / 3);

		
		
		table.getColumns().add(nameCol);
		table.getColumns().add(pathCol);
		table.getColumns().add(dataSizeCol);
		
		
		
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// TODO
			}
		};
		
		// TextField startordner
		TextField startordner = new TextField("Bitte den Startordner eingeben");
		startordner.setPrefWidth(300);
		gridpane.add(new Label("Startordner: "), 2, 2);
		gridpane.add(startordner, 2, 3);
		startordner.setOnAction(event);
		
		// TextField anzDateien
		TextField anzDateien = new TextField("Bitte die Anzahl der Dateien eingeben");
		anzDateien.setPrefWidth(300);
		gridpane.add(new Label("Anzahl Dateien: "), 2, 5);
		gridpane.add(anzDateien, 2, 6);
		anzDateien.setOnAction(event);
		
		// Progressbar
		ProgressBar fortschrittsanzeige = new ProgressBar();
		GridPane.setHalignment(fortschrittsanzeige, HPos.CENTER);
		gridpane.add(fortschrittsanzeige, 2, 7);
		

		Label label = new Label("Working");
		gridpane.add(label, 2, 7);
		GridPane.setHalignment(label, HPos.CENTER);
		
		
		// Button directoryChooser
		Button directoryChooserButton = new Button("Ordner auswählen");
		gridpane.add(directoryChooserButton, 2, 4);
				
		directoryChooserButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	startordner.setText(getDirectory(primaryStage).getPath());
		    }
		});
		
		// Button dateisucheButton
		Button dateisucheButton = new Button("Dateien suchen");
		gridpane.add(dateisucheButton, 2, 7);
		
		dateisucheButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	datenSatz.clear();
		    	
		    	File ordner = new File(startordner.getText());
		    	if (!ordner.isDirectory()) {
		    		Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Fehlermeldung");
		            alert.setHeaderText(null);
		            alert.setContentText("Ungültige Eingabe für den Startordner");
		            alert.showAndWait();
		    	}
		    	
		    	int anzahlDateien = 0;
		    	try {
		    		anzahlDateien = Integer.parseInt(anzDateien.getText());
		    	} catch (NumberFormatException e) {
		    		Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Fehlermeldung");
		            alert.setHeaderText(null);
		            alert.setContentText("Ungültige Eingabe für Anzahl der Dateien");
		            alert.showAndWait();
		    	}
		    	Dateisuche dateisuche = new Dateisuche(ordner, anzahlDateien, datenSatz);
		    	try {
					dateisuche.call();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	
		    	
		    	FortschrittsanzeigeTask task = new FortschrittsanzeigeTask(label);
				
				fortschrittsanzeige.progressProperty().unbind();
				fortschrittsanzeige.progressProperty().bind(task.progressProperty());
				
				Thread worker = new Thread(task);
				worker.start();
				
				dataSizeCol.setSortType(TableColumn.SortType.DESCENDING);
				table.getSortOrder().add(dataSizeCol);
		    }
		});
		
		// Finalize stage
	    primaryStage.setScene(szene);
	    primaryStage.show();
	}
	
	/**
	 * Oeffnet einen DirectoryChooser und gibt den ausgewaehlten Ordner zurueck
	 * @param primaryStage
	 * @return ausgewaehlten Ordner
	 */
	private File getDirectory(Stage primaryStage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
	   	directoryChooser.setTitle("Open Resource File");
	   	 
	   	File selectedDirectory = directoryChooser.showDialog(primaryStage);
	   	if (selectedDirectory != null) {
	   		return selectedDirectory;
	   	}
	   	return null;
	}
	
	/**
	 * Alt
	 * @return
	 */
	private ObservableList<Datei> getDateienDatensatz() {
		Datei datei1 = new Datei("1", "Dieter", 4);
		Datei datei2 = new Datei("Hans", "Dieter", 2);
		Datei datei3 = new Datei("Anna", "Dieter", 3);

		ObservableList<Datei> dateien = FXCollections.<Datei> observableArrayList(datei1, datei2, datei3);
		return dateien;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
