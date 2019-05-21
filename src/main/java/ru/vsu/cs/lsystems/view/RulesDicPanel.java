package ru.vsu.cs.lsystems.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import ru.vsu.cs.lsystems.model.FilledLSystem;
import ru.vsu.cs.lsystems.model.LSystemModel;

import java.util.Arrays;

public class RulesDicPanel extends GridPane {

	private final RulesPanel rulesPanel;
	private final StringConverter<FilledLSystem> converter;

	/**
	 * DDList with some ready rules.
	 */
	private ChoiceBox<FilledLSystem> lRulesSelector;

	public void onChange(ObservableValue<? extends FilledLSystem> observable, FilledLSystem oldValue, FilledLSystem newValue) {
		if (newValue != null) {
			this.rulesPanel.setModel(newValue.getLSystem());
		}
	}

	public LSystemModel getCurrentSelectedLSystem() {
		return Arrays.stream(FilledLSystem.values())
			.filter(_filled -> _filled.equals(this.lRulesSelector.getSelectionModel().getSelectedItem()))
			.findFirst()
			.get().getLSystem();
	}

	public RulesDicPanel(RulesPanel rulesPanel) {
		this.rulesPanel = rulesPanel;

		this.converter = new StringConverter<FilledLSystem>() {
			@Override
			public String toString(FilledLSystem object) {
				return object.getLSystem().getName();
			}

			@Override
			public FilledLSystem fromString(String string) {
				return FilledLSystem.getByLSystemName(string);
			}
		};

		this.setBackground(new Background(
			new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null)
		));
		int currentRow = 0;

		//Header
		Label header = new Label("Pre-filled L-Systems");
		header.setFont(Font.font(null, FontWeight.BOLD, 12));
		header.setAlignment(Pos.CENTER);
		header.setMaxWidth(Double.MAX_VALUE);
		this.add(header, 0, currentRow++, 4, 1);
		//
		this.lRulesSelector = new ChoiceBox<>(FXCollections.observableArrayList(FilledLSystem.values()));
		this.lRulesSelector.prefWidthProperty().bind(this.widthProperty());
		this.lRulesSelector.converterProperty().setValue(this.converter);
		this.lRulesSelector.getSelectionModel().selectedItemProperty().addListener(this::onChange);
		this.lRulesSelector.getSelectionModel().selectFirst();
		this.rulesPanel.setModel(this.lRulesSelector.getSelectionModel().getSelectedItem().getLSystem());
		this.add(this.lRulesSelector, 0, currentRow++);
	}

}
