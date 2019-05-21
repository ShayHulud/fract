package ru.vsu.cs.lsystems.services;

import ru.vsu.cs.lsystems.model.DrawPoint;
import ru.vsu.cs.lsystems.model.LSystemModel;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class LSystemGenerationService {

	private final double startAngle = 0;
	private final double rotationAngle = Math.PI / 2;

	public List<DrawPoint> generatePoints(LSystemModel model, int depth) {
		List<DrawPoint> pointsToDraw = new LinkedList<>();
		String axiom = this.generateFinalRule(model, depth);
		//Initial
		double alpha = startAngle;
		double x = 0;
		double y = 0;
		double x0 = 0;
		double y0 = 0;
		for (int i = 0; i < axiom.length(); i++) {
			Character c = axiom.charAt(i);
			switch (c) {
				case '+': {
					alpha += rotationAngle;
				}
				break;
				case '-': {
					alpha -= rotationAngle;
				}
				break;
				case 'f': {
					x = x0 + Math.cos(alpha);
					y = y0 + Math.sin(alpha);
					pointsToDraw.add(new DrawPoint(
						new Point2D.Double(x0, y0),
						new Point2D.Double(x, y)
					));
					x0 = x;
					y0 = y;
				}
				break;
				case 'b': {
					x = x0 + Math.cos(alpha);
					y = y0 + Math.sin(alpha);
					x0 = x;
					y0 = y;
				}
			}
		}
		return normalisation(pointsToDraw);
	}

	private List<DrawPoint> normalisation(List<DrawPoint> points) {
		//Normalisation
		double xMax = 0, yMax = 0;
		double xMin = 0, yMin = 0;
		for (DrawPoint point : points) {
			if (xMax < point.getStart().getX()) {
				xMax = point.getStart().getX();
			}
			if (yMax < point.getStart().getY()) {
				yMax = point.getStart().getY();
			}
			if (xMin > point.getStart().getX()) {
				xMin = point.getStart().getX();
			}
			if (yMin > point.getStart().getY()) {
				yMin = point.getStart().getY();
			}
			if (xMax < point.getEnd().getX()) {
				xMax = point.getEnd().getX();
			}
			if (yMax < point.getEnd().getY()) {
				yMax = point.getEnd().getY();
			}
			if (xMin > point.getEnd().getX()) {
				xMin = point.getEnd().getX();
			}
			if (yMin > point.getEnd().getY()) {
				yMin = point.getEnd().getY();
			}
		}
		// Perform
		List<DrawPoint> normalisation = new LinkedList<>();
		for (DrawPoint point : points) {
			normalisation.add(new DrawPoint(
					new Point2D.Double(
						(point.getStart().getX() - xMin) / (xMax - xMin) - 0.5,
						(point.getStart().getY() - yMin) / (yMax - yMin) - 0.5
					),
					new Point2D.Double(
						(point.getEnd().getX() - xMin) / (xMax - xMin) - 0.5,
						(point.getEnd().getY() - yMin) / (yMax - yMin) - 0.5
					)
				)
			);
		}
		return normalisation;
	}

	private String generateFinalRule(LSystemModel model, int depth) {
		String axiom = model.getAxiom();
		for (int iDepth = 0; iDepth < depth; iDepth++) {
			StringBuilder builder = new StringBuilder();
			//Generate next step
			for (int i = 0; i < axiom.length(); i++) {
				Character currentToken = axiom.charAt(i);
				switch (currentToken) {
					case 'f': {
						builder.append(model.getNewF());
					}
					break;
					case 'b': {
						builder.append(model.getNewB());
					}
					break;
					case '+':
					case '-': {
						builder.append(currentToken);
					}
					break;
				}
			}
			axiom = builder.toString();
		}
		return axiom;
	}

}
