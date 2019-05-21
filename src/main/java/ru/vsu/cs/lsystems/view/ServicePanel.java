package ru.vsu.cs.lsystems.view;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.vsu.cs.lsystems.services.LSystemService;

import java.util.HashMap;
import java.util.Map;

public class ServicePanel extends GridPane {

	private Map<String, LSystemService> serviceMap = new HashMap<>();
	private final ChoiceBox<String> serverSelector;

	public LSystemService getDefaultService() {
		return this.serviceMap.entrySet().iterator().next().getValue();
	}

	public LSystemService getCurrentService() {
		return this.serviceMap.getOrDefault(this.serverSelector.getValue(), getDefaultService());
	}

	private void addService(LSystemService lSystemService) {
		serviceMap.put(lSystemService.getClass().getSimpleName(), lSystemService);
	}

	public ServicePanel() {
		super();
		//Fill map of service
		addService(new LSystemService());
		//Add layout
		int currentRow = 0;
		//Header
		Label header = new Label("Service for computing:");
		header.setFont(Font.font(null, FontWeight.BOLD, 12));
		header.setAlignment(Pos.CENTER);
		header.prefWidthProperty().bind(this.widthProperty());
		this.add(header, 0, currentRow++);
		//Add selector of services
		this.serverSelector = new ChoiceBox<>(FXCollections.observableArrayList(this.serviceMap.keySet()));
		this.serverSelector.getSelectionModel().selectFirst();
		this.serverSelector.prefWidthProperty().bind(this.widthProperty());
		this.add(this.serverSelector, 0, currentRow++);
	}

}
