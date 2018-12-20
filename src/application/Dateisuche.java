package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Klasse zur Suche nach Dateien in Verzeichnissen
 * @author Lars, Philip
 *
 */
public class Dateisuche extends Task<Boolean>{

	private File path;
	private int anzahlDateien;
	private ObservableList<Datei> datensatz;
	private static int max;
	
	/**
	 * Konstruktor
	 * @param path Verzeichnis, das durchsucht wird
	 * @param anzahlDateien Anzahl der Dateien, die angezeigt werden sollen
	 * @param datensatz ObservableList, die mit Tabelle im GUI verknuepft ist
	 */
	public Dateisuche(File path, int anzahlDateien, ObservableList<Datei> datensatz) {
		this.path = path;
		this.anzahlDateien = anzahlDateien;
		this.datensatz = datensatz;
	}
	
	/**
	 * Durchsucht ein Verzeichnis inklusive aller Unterverzeichnisse nach Dateien
	 * und fuegt die 'anzahlDateien' groessten Dateien einer ObservableList hinzu
	 */
	@Override
	protected Boolean call() throws Exception {
		List<File> dateien = getFiles(path);
		
		int max = Math.min(anzahlDateien, dateien.size());
		
		for (int i = 0; i < max; i++) {
			long size = 0;
			int indexGroessteDatei = 0;
			for (int j = 0; j < dateien.size(); j++) {
				if (dateien.get(j).length() > size) {
					indexGroessteDatei = j;
					size = dateien.get(j).length();
				}
			}
			datensatz.add(new Datei(dateien.get(indexGroessteDatei).getName(), dateien.get(indexGroessteDatei).getPath(), dateien.get(indexGroessteDatei).length()));
			dateien.remove(indexGroessteDatei);
		}
		
		return null;
	}
	
	/**
	 * Durchsucht ein Verzeichnis nach Dateien
	 * @param path Verzeichnis
	 * @return Liste mit allen im Verzeichnis enthaltenen Dateien
	 */
	private List<File> getFiles(File path) {
		List<File> dateien = new ArrayList<File>();
		
		if (path.isDirectory()) {
			File[] files = path.listFiles();
			
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						dateien.add(files[i]);
					}
					else if (files[i].isDirectory()) {
						dateien.addAll(getFiles(files[i]));
					}
				}
			}
		}		
		
		return dateien;		
	}
	
	public static int getMax() {
		return max;
	}
}
