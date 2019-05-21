package ru.vsu.cs.lsystems.view;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Getter;
import ru.vsu.cs.lsystems.events.RulesChangeEvent;
import ru.vsu.cs.lsystems.model.LSystemModel;
import ru.vsu.cs.lsystems.util.RulesUtil;

public class RulesPanel extends GridPane {

	/**
	 * Start value.
	 */
	private TextField axiom;
	/**
	 * Value for F expansion.
	 */
	private TextField newF;
	/**
	 * Value for B expansion.
	 */
	private TextField newB;
	/**
	 * Number of iterations.
	 */
	private TextField iterationNum;

	/**
	 * Apply button
	 */
	private Button applyButton;

	@Getter
	private LSystemModel model;

	private void onApply(ActionEvent e) {
		//Validate input and pass it to subscribers
		String axiom = RulesUtil.trimRule(this.axiom.getText());
		String newF = RulesUtil.trimRule(this.newF.getText());
		String newB = RulesUtil.trimRule(this.newB.getText());
		if (RulesUtil.isEmpty(axiom)) {
			new Alert(
				Alert.AlertType.WARNING,
				"Axiom can not be empty!"
			).show();
			return;
		}
		LSystemModel newModel = new LSystemModel("name", axiom, newF, newB);
		this.setModel(newModel);
	}

	public void setModel(LSystemModel model) {
		this.model = model;
		this.axiom.setText(this.model.getAxiom());
		this.newF.setText(this.model.getNewF());
		this.newB.setText(this.model.getNewB());
		this.fireEvent(new RulesChangeEvent(model));
	}

	public Integer getIterationValue() {
		return Integer.parseInt(this.iterationNum.textProperty().getValue());
	}

	private void addParameter(int baseRow, String label, String defaultValue,
	                          TextField realText) {
		//Set real properties
		realText.setText(defaultValue);
		//Generate layout
		this.add(new Text(label + ":"), 0, baseRow);
		//
		this.add(realText, 1, baseRow);
	}

	private void onIterationNumChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if (!newValue.matches("\\d*")) {
			newValue = newValue.replaceAll("[^\\d]", "");
		}
		if (newValue.equals("")) {
			this.iterationNum.setText(newValue);
			return;
		}
		int newIntValue = Integer.parseInt(newValue);
		if (newIntValue < 1) {
			newIntValue = 1;
			newValue = Integer.toString(newIntValue);
		}
		if (newIntValue > 10) {
			newIntValue = 10;
			newValue = Integer.toString(newIntValue);
		}
		this.iterationNum.setText(newValue);
	}

	public RulesPanel() {
		super();
		this.setHgap(5);
		this.setVgap(5);
//		this.setPadding(new Insets(0, 5, 0, 5));
		//Add children
		int currentRow = 0;
		//Header
		Label header = new Label("L-System rules");
		header.setFont(Font.font(null, FontWeight.BOLD, 12));
		header.setAlignment(Pos.CENTER);
		header.setMaxWidth(Double.MAX_VALUE);
		this.add(header, 0, currentRow++, 4, 1);
		// Axiom
		this.axiom = new TextField();
		this.addParameter(currentRow++, "Axiom", "", this.axiom);
		// F rule
		this.newF = new TextField();
		this.addParameter(currentRow++, "F rule", "", this.newF);
		// B rule
		this.newB = new TextField();
		this.addParameter(currentRow++, "B rule", "", this.newB);
		// IterNum
		this.iterationNum = new TextField();
		this.iterationNum.textProperty().addListener(this::onIterationNumChanged);
		this.addParameter(currentRow++, "Iteration", Integer.toString(5), this.iterationNum);
		//Apply button
		this.applyButton = new Button("Apply");
		this.applyButton.setOnAction(this::onApply);
		this.applyButton.prefWidthProperty().bind(this.widthProperty());
		this.add(applyButton, 0, currentRow, 4, 1);
	}
}