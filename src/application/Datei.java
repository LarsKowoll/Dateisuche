package application;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Repraesentiert eine Datei, bestehend aus Name, Dateipfad und Dateigroesse
 * @author Lars, Philip
 *
 */
public class Datei {
	private StringProperty name;
	private StringProperty path;
	private LongProperty dataSize;
	
	public Datei(String name, String path, long dataSize) {
		this.name = new SimpleStringProperty(name);
		this.path = new SimpleStringProperty(path);
		this.dataSize = new SimpleLongProperty(dataSize);
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getPath() {
		return path.get();
	}
	
	public void setPath(String path) {
		this.path.set(path);
	}
	
	public long getDataSize() {
		return dataSize.get();
	}
	
	public void setDataSize(long dataSize) {
		this.dataSize.set(dataSize);
	}
	
	
}
