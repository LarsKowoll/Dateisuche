package application;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class FortschrittsanzeigeTask extends Task<Boolean> {
	
	private final Label label;
	
	public FortschrittsanzeigeTask (Label label) {
		this.label = label;
	}
	
	protected Boolean call() throws Exception {
		int numberOfSteps = Dateisuche.getMax();
		updateProgress(0, numberOfSteps -1);
		
		for (int i = 0; i < numberOfSteps; i++) {
			updateProgress(i, numberOfSteps -1);
		}
		
		updateLabel();
		return true;
	}
	
	private void updateLabel() {
		Platform.runLater(() -> {
			label.setText("Done.");		
		});
	} 
}
