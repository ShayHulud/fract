package ru.vsu.cs.lsystems.util;

import java.util.Arrays;
import java.util.HashSet;

public class RulesUtil {

	private final static HashSet<Character> VALID_CHARACTERS = new HashSet<>(Arrays.asList('f', 'b', '+', '-'));

	public static boolean isEmpty(String rule) {
		return rule == null || rule.isEmpty();
	}

	public static String trimRule(String rule) {
		if (rule == null) {
			return null;
		}
		StringBuilder trimmed = new StringBuilder();
		rule = rule.toLowerCase();
		for (int i = 0; i < rule.length(); i++) {
			if (VALID_CHARACTERS.contains(rule.charAt(i))) {
				trimmed.append(rule.charAt(i));
			}
		}
		return trimmed.toString();
	}

}
