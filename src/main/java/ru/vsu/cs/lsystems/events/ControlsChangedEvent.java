package ru.vsu.cs.lsystems.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import ru.vsu.cs.lsystems.model.LSystemModel;

public class ControlsChangedEvent extends Event {

	public static final EventType<ControlsChangedEvent> TYPE = new EventType<>(ControlsChangedEvent.class.getName());

	@Getter
	private final LSystemModel rulesModel;
	@Getter
	private final double rotationAngle;

	public ControlsChangedEvent(LSystemModel rulesModel, double rotationAngle) {
		super(TYPE);
		this.rulesModel = rulesModel;
		this.rotationAngle = rotationAngle;
	}
}
