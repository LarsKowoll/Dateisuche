package application;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class Dateisuche extends Task<Boolean>{

	private File path;
	private int anzahlDateien;
	private ObservableList<Datei> datensatz;
	
	public Dateisuche(File path, int anzahlDateien, ObservableList<Datei> datensatz) {
		this.path = path;
		this.anzahlDateien = anzahlDateien;
		this.datensatz = datensatz;
	}
	
	@Override
	protected Boolean call() throws Exception {
		if (path.isDirectory()) {
			File[] files = path.listFiles();
			
			int max = Math.min(files.length, anzahlDateien);
			
			for (int i = 0; i < max; i++) {
				if (files[i].isFile()) {
					datensatz.add(new Datei(files[i].getName(), files[i].getPath(), files[i].length()));
				}
			}
		}
		return null;
	}
}
