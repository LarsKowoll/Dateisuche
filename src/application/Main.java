package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	
	String _dateipfad;
	int _anzahlDateien;
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Dateirecherche");
		
		Group wurzel = new Group();
		Scene szene = new Scene(wurzel, Color.WHITE);
		
		// Use a grid pane for organization
	    GridPane gridpane = new GridPane();
	    gridpane.setPadding(new Insets(15,15,15,15));
	    
	    Label labelTitel = new Label("Gefundene Datein");
	    gridpane.add(labelTitel, 2, 0);
	    GridPane.setHalignment(labelTitel, HPos.CENTER);
	    wurzel.getChildren().add(gridpane);
	    
		
		TableView<Datei> table = new TableView<Datei>();
		table.setPrefWidth(350);
		table.setPrefHeight(300);
		
		ObservableList<Datei> datenSatz = getDateienDatensatz();
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
		pathCol.setCellValueFactory(new PropertyValueFactory<Datei, String>("pfad"));
		pathCol.setPrefWidth(table.getPrefWidth() / 3);
		
		// Setup the third colum: groesse
		TableColumn<Datei, Integer> dataSizeCol = new TableColumn<Datei, Integer>("Groesse (Byte)");
		dataSizeCol.setMinWidth(100);
		dataSizeCol.setCellValueFactory(new PropertyValueFactory<Datei, Integer>("groesse"));
		dataSizeCol.setPrefWidth(table.getPrefWidth() / 3);

		
		dataSizeCol.setSortType(TableColumn.SortType.DESCENDING);
		
		table.getColumns().add(nameCol);
		table.getColumns().add(pathCol);
		table.getColumns().add(dataSizeCol);


		TextField startordner = new TextField("Bitte den Startordner eingeben");
		startordner.setPrefWidth(300);
		TextField anzDateien = new TextField("Bitte die Anzahl der Dateien eingeben");
		anzDateien.setPrefWidth(300);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//todo
			}
		};
			
		startordner.setOnAction(event);
		anzDateien.setOnAction(event);
		
		gridpane.add(new Label("Startordner: "), 2, 2);
		gridpane.add(startordner, 2, 2);
		gridpane.add(new Label("Anzahl Dateien: "), 2, 3);
		gridpane.add(anzDateien, 2, 3);
		
		// Finalize stage
	    primaryStage.setScene(szene);
	    primaryStage.show();
	}

	private ObservableList<Datei> getDateienDatensatz() {
		Datei datei1 = new Datei("1", "Dieter", 1);
		Datei datei2 = new Datei("Hans2", "Dieter", 2);
		Datei datei3 = new Datei("Hans3", "Dieter", 3);

		ObservableList<Datei> dateien = FXCollections.<Datei>observableArrayList(datei1, datei2, datei3);
		return dateien;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
