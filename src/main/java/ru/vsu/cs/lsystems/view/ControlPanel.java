package ru.vsu.cs.lsystems.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ru.vsu.cs.lsystems.events.ControlsChangedEvent;
import ru.vsu.cs.lsystems.events.RulesChangeEvent;
import ru.vsu.cs.lsystems.events.SaveEvent;
import ru.vsu.cs.lsystems.model.LSystemModel;
import ru.vsu.cs.lsystems.services.LSystemService;

import java.awt.geom.Point2D;

public class ControlPanel extends GridPane {

	private final RulesPanel rulesPanel;
	private final CurrentBordersPanel currentBordersPanel;
	private final RotationPanel rotationPanel;
	private final ServicePanel servicePanel;
	private final SavePanel savePanel;
	private final RulesDicPanel rulesDicPanel;

	private void onRulesChanged(RulesChangeEvent event) {
		this.fireEvent(new ControlsChangedEvent(
			event.getRulesModel(),
			this.rotationPanel.getAngle()
		));
	}

	public LSystemService getService() {
		return this.servicePanel.getCurrentService();
	}

	public void setInfo(Point2D.Double center, int scale, long time) {
		this.currentBordersPanel.setCenter(center);
		this.currentBordersPanel.setScale(scale);
		this.currentBordersPanel.setLastTime(time);
	}

	public void setValues(LSystemModel model) {
		this.rulesPanel.setModel(model);
	}

	public void setDefaultValues() {
		this.rulesPanel.setModel(this.rulesDicPanel.getCurrentSelectedLSystem());
	}

	public Integer getIterationNum() {
		return this.rulesPanel.getIterationValue();
	}

	public ControlPanel() {
		super();
		this.setBackground(new Background(
			new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, null, null)
		));
		int currentRow = 0;
		//
		this.currentBordersPanel = new CurrentBordersPanel();
		this.add(this.currentBordersPanel, 0, currentRow++);
		//
		this.rotationPanel = new RotationPanel();
		this.add(this.rotationPanel, 0, currentRow++);
		//
		this.servicePanel = new ServicePanel();
		this.add(this.servicePanel, 0, currentRow++);
		//
		this.rulesPanel = new RulesPanel();
		this.rulesDicPanel = new RulesDicPanel(this.rulesPanel);
		this.add(this.rulesDicPanel, 0, currentRow++);
		this.addEventHandler(RulesChangeEvent.TYPE, this::onRulesChanged);
		this.add(this.rulesPanel, 0, currentRow++);
		//
		this.savePanel = new SavePanel();
		savePanel.addEventHandler(SaveEvent.TYPE, this::fireEvent);
		this.add(this.savePanel, 0, currentRow++);
		//
		this.setMinWidth(200);
		this.setMaxWidth(200);
	}
}
