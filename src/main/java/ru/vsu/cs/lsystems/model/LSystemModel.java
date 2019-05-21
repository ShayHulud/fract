package ru.vsu.cs.lsystems.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LSystemModel {
	private final String name;
	private final String axiom;
	private final String newF;
	private final String newB;
}
