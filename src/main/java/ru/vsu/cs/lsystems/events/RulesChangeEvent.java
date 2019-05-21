package ru.vsu.cs.lsystems.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import ru.vsu.cs.lsystems.model.LSystemModel;

public class RulesChangeEvent extends Event {

	public final static EventType<RulesChangeEvent> TYPE = new EventType<>(RulesChangeEvent.class.getName());

	@Getter
	private final LSystemModel rulesModel;

	public RulesChangeEvent(LSystemModel rulesModel) {
		super(TYPE);
		this.rulesModel = rulesModel;
	}
}
