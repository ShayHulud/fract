package ru.vsu.cs.lsystems.util;

import java.awt.geom.Point2D;

public class GeometryUtil {

	public static Point2D.Double rotatePoint(Point2D.Double point, double angle, Point2D.Double center) {
		return new Point2D.Double(
			center.getX() + (point.getX() - center.getX()) * Math.cos(angle) - (point.getY() - center.getY()) * Math.sin(angle),
			center.getY() + (point.getX() - center.getX()) * Math.sin(angle) + (point.getY() - center.getY()) * Math.cos(angle)
		);
	}
}
