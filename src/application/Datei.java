package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Datei {
	private String _name;
	private String _path;
	private int _dataSize;
	
	public Datei (String name, String path, int dataSize) {
		this._name = name;
		this._path = path;
		this._dataSize = dataSize;	
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public String getPath() {
		return _path;
	}
	
	public void setPath(String path) {
		_path = path;
	}
	
	public long getDataSize() {
		return _dataSize;
	}
	
	public void setDataSize(int dataSize) {
		_dataSize = dataSize;
	}
	
	
}
