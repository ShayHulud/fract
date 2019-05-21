package ru.vsu.cs.lsystems.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class SaveEvent extends Event {

	public final static EventType<SaveEvent> TYPE = new EventType<>(SaveEvent.class.getName());

	@Getter
	private final String fileName;

	public SaveEvent(String fileName) {
		super(TYPE);
		this.fileName = fileName;
	}
}
