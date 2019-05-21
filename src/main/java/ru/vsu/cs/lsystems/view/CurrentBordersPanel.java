package ru.vsu.cs.lsystems.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Getter;

import java.awt.geom.Point2D;

public class CurrentBordersPanel extends GridPane {

	private final static String CENTER_TEXT = "Center: [%e, %e]";
	private final static String Y_BORDERS_TEXT = "Y: [%e, %e]";
	private final static String SCALE_TEXT = "Scale: x%d";
	private final static String TIME_TEXT = "Last execution: %d ms";

	@Getter
	private Point2D.Double center;
	@Getter
	private int scale = 1;
	@Getter
	private long lastTime = 0;
	//Layout
	private Text centerText;
	private Text scaleText;
	private Text timeText;

	public void setCenter(Point2D.Double center) {
		this.center = center;
	}

	public void setScale(int scale) {
		this.scale = scale;
		this.updateText();
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
		this.updateText();
	}

	private void updateText() {
		if (center == null) {
			this.centerText.setText(String.format(CENTER_TEXT, Double.NaN, Double.NaN));
		} else {
			this.centerText.setText(String.format(CENTER_TEXT, center.getX(), center.getY()));
		}
		this.scaleText.setText(String.format(SCALE_TEXT, scale));
		this.timeText.setText(String.format(TIME_TEXT, lastTime));
	}

	public CurrentBordersPanel() {
		super();
		//Header
		Label header = new Label("Current info:");
		header.setFont(Font.font(null, FontWeight.BOLD, 12));
		header.setAlignment(Pos.CENTER);
		header.prefWidthProperty().bind(this.widthProperty());
		int currentRow = 0;
		this.add(header, 0, currentRow++);
		//
		this.centerText = new Text();
		this.add(this.centerText, 0, currentRow++);
		//
		this.scaleText = new Text();
		this.add(this.scaleText, 0, currentRow++);
		//
		this.timeText = new Text();
		this.add(this.timeText, 0, currentRow++);
		//
		this.updateText();
	}
}