package ru.vsu.cs.lsystems.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RotationPanel extends GridPane {

	private final Slider angleSlider;

	public double getAngle() {
		return this.angleSlider.getValue();
	}

	public RotationPanel() {
		super();
		//Header
		Label header = new Label("Rotation:");
		header.setFont(Font.font(null, FontWeight.BOLD, 12));
		header.setAlignment(Pos.CENTER);
		header.prefWidthProperty().bind(this.widthProperty());
		this.add(header, 0, 0);
		//
		this.angleSlider = new Slider();
		this.angleSlider.setMax(Math.PI * 2);
		this.angleSlider.setMin(0);
		this.angleSlider.setShowTickMarks(true);
		this.angleSlider.setShowTickLabels(true);
		this.angleSlider.setMajorTickUnit(Math.PI / 2);
		this.angleSlider.setBlockIncrement(Math.PI / 8);
		this.add(this.angleSlider, 0, 1);
	}

}
