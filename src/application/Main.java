package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		TableView<Datei> table = new TableView<Datei>();

		
		TableColumn<Datei, String> nameCol = new TableColumn<Datei, String>("Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<Datei, String>("name"));
		
		
		TableColumn<Datei, String> pathCol = new TableColumn<Datei, String>("Pfad");
		pathCol.setMinWidth(100);
		pathCol.setCellValueFactory(new PropertyValueFactory<Datei, String>("pfad"));
		
		
		TableColumn<Datei, Integer> dataSizeCol = new TableColumn<Datei, Integer>("Groesse (Byte)");
		dataSizeCol.setMinWidth(100);
		dataSizeCol.setCellValueFactory(new PropertyValueFactory<Datei, Integer>("groesse"));	

		
		dataSizeCol.setSortType(TableColumn.SortType.DESCENDING);
		
		ObservableList<Datei> datenSatz = getDateienDatensatz();
		table.setItems(datenSatz);

		table.getColumns().addAll(nameCol, pathCol, dataSizeCol);

		//StackPane root = new StackPane();
		//root.setPadding(new Insets(5));
		//root.getChildren().add(table);

		primaryStage.setTitle("Dateirecherche)");

		Scene scene = new Scene(table);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setWidth(450);
		primaryStage.setHeight(550);
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
