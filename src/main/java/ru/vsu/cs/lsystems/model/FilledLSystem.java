package ru.vsu.cs.lsystems.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum FilledLSystem {

	/**
	 * Цепочка Ян-Си-Ло.
	 */
	YAN_SI_LO(new LSystemModel("Yan-Si-Lo chain", "f+f+f+f", "f+b-f-fff+f+b-f", "bbb")),
	/**
	 * Мозайка Хагерти.
	 */
	HAGERTY(new LSystemModel("Hagerty mosaic", "f-f-f-f", "f-b+ff-f-ff-fb-ff+b-ff+f+ff+fb+fff", "bbbbbb"));

	@Getter
	private final LSystemModel lSystem;

	public static Set<LSystemModel> getFilledLSystems() {
		return Arrays.stream(FilledLSystem.values())
			.map(FilledLSystem::getLSystem)
			.collect(Collectors.toSet());
	}

	public static Set<String> getFilledLSystemsNames() {
		return Arrays.stream(FilledLSystem.values())
			.map(FilledLSystem::getLSystem)
			.map(LSystemModel::getName)
			.collect(Collectors.toSet());
	}

	public static FilledLSystem getByLSystemName(String name) {
		return Arrays.stream(FilledLSystem.values())
			.filter(_filled -> _filled.getLSystem().getName().equals(name))
			.findFirst()
			.get();
	}
}
