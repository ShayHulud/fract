package ru.vsu.cs.lsystems.view;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import ru.vsu.cs.lsystems.events.SaveEvent;

import java.io.File;

public class SavePanel extends GridPane {

	private final FileChooser dialog;
	private final Button saveButton;

	public SavePanel() {
		super();
		//
		this.dialog = new FileChooser();
		this.dialog.setTitle("Choose image to save");
		this.dialog.setInitialDirectory(
			new File(System.getProperty("user.home"))
		);
		this.dialog.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			new FileChooser.ExtensionFilter("PNG", "*.png"),
			new FileChooser.ExtensionFilter("BMP", "*.bmp"),
			new FileChooser.ExtensionFilter("GIF", "*.gif")
		);
		//
		this.saveButton = new Button("Choose file to save...");
		this.saveButton.setOnAction(event -> {
			File file = this.dialog.showSaveDialog(this.getScene().getWindow());
			if (file != null) {
				this.fireEvent(new SaveEvent(file.getAbsolutePath()));
			}
		});
		saveButton.prefWidthProperty().bind(this.widthProperty());
		this.add(saveButton, 0, 0);
	}
}
