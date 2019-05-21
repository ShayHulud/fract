package ru.vsu.cs.lsystems.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;
import lombok.extern.java.Log;
import ru.vsu.cs.lsystems.events.ControlsChangedEvent;
import ru.vsu.cs.lsystems.events.SaveEvent;
import ru.vsu.cs.lsystems.model.DrawPoint;
import ru.vsu.cs.lsystems.model.OutputModel;
import ru.vsu.cs.lsystems.services.LSystemGenerationService;
import ru.vsu.cs.lsystems.util.GeometryUtil;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

@Log
public class MainView extends GridPane {

	@Getter
	private final ImageView imageView;
	@Getter
	private final ControlPanel controlPanel;

	private final LSystemGenerationService generationService = new LSystemGenerationService();

	private List<DrawPoint> points = Collections.emptyList();
	private double currentRotationAngle = 0d;
	//
	private Point2D.Double center = new Point2D.Double(0, 0);
	private int scale = 1;

	private void onControlChange(ControlsChangedEvent event) {
		this.points = this.generationService.generatePoints(event.getRulesModel(), this.controlPanel.getIterationNum());
		this.redraw(this.points, event.getRotationAngle());
	}

	public void refresh() {
		this.redraw(this.points, this.currentRotationAngle);
	}

	private void redraw(List<DrawPoint> points, double angle) {
		//Save last parameters
		//Draw
		long start = System.currentTimeMillis();

		OutputModel output = this.controlPanel.getService().draw(
			points, angle,
			center, scale,
			(int) imageView.getFitWidth(),
			(int) imageView.getFitHeight()
		);
		if (output != null) {
			this.imageView.setImage(SwingFXUtils.toFXImage(output.getImage(), null));
			this.controlPanel.setInfo(center, scale, System.currentTimeMillis() - start);
			this.currentRotationAngle = angle;
		}
	}

	public void onImageClicked(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			//Zoom in
			double clickX = event.getX() / (((ImageView) event.getSource()).getFitWidth()) - 0.5;
			double clickY = event.getY() / (((ImageView) event.getSource()).getFitHeight()) - 0.5;
			Point2D.Double viewBorders = new Point2D.Double(1. / scale, 1. / scale);
			Point2D.Double newCenter = new Point2D.Double(
				center.getX() + viewBorders.getX() * clickX,
				center.getY() + viewBorders.getY() * clickY
			);
			//Rotate corresponding to current rotation
			center = GeometryUtil.rotatePoint(newCenter, this.currentRotationAngle, center);
			//Increase scale
			scale *= 2;
		} else {
			//Zoom out
			if (scale == 1) return;
			scale /= 2;
		}
		this.refresh();
	}

	public void onImageSave(SaveEvent event) {
		String fileName = event.getFileName();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		try {
			BufferedImage src = SwingFXUtils.fromFXImage(this.imageView.getImage(), null);
			BufferedImage convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
			convertedImg.getGraphics().drawImage(src, 0, 0, null);
			ImageIO.write(convertedImg, extension, new File(fileName));
		} catch (IOException e) {
			log.log(Level.SEVERE, "Image was not saved", e);
		}
	}

	public MainView() {
		super();
		//
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS);
		this.getColumnConstraints().add(column1);
		//
		this.imageView = new ImageView();
		this.imageView.setPreserveRatio(true);
		this.imageView.setOnMouseClicked(this::onImageClicked);
		BorderPane imagePane = new BorderPane();
		imagePane.setCenter(imageView);
		this.add(imagePane, 0, 0);
		//
		this.controlPanel = new ControlPanel();
		this.controlPanel.addEventHandler(ControlsChangedEvent.TYPE, this::onControlChange);
		this.controlPanel.addEventHandler(SaveEvent.TYPE, this::onImageSave);
		this.add(controlPanel, 1, 0);
		//
		this.imageView.fitHeightProperty().bind(this.heightProperty());
		this.imageView.fitWidthProperty().bind(this.widthProperty().subtract(this.controlPanel.minWidthProperty()));
		this.controlPanel.prefHeightProperty().bind(this.heightProperty());
		this.controlPanel.setDefaultValues();
	}
}
