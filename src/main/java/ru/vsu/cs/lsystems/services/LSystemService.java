package ru.vsu.cs.lsystems.services;

import ru.vsu.cs.lsystems.model.DrawPoint;
import ru.vsu.cs.lsystems.model.OutputModel;
import ru.vsu.cs.lsystems.util.GeometryUtil;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class LSystemService {
	public OutputModel draw(List<DrawPoint> points,
	                        double angle, Point2D.Double center, int scale,
	                        int fitWidth, int fitHeight) {
		if (fitWidth <= 0 || fitHeight <= 0) {
			return null;
		}
		//Rotate points and move points
		points = this.transformPoints(points, fitWidth, fitHeight, scale, angle, center);
		//Filter
		points = this.filterPoints(points, fitWidth, fitHeight);
		BufferedImage image = this.drawPoints(points, fitWidth, fitHeight);
		return new OutputModel(
			image,
			new Point2D.Double(0, 0),
			new Point2D.Double(0, 0)
		);
	}

	private BufferedImage drawPoints(List<DrawPoint> points, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		for (DrawPoint point : points) {
			g.drawLine(
				(int) point.getStart().getX(), (int) point.getStart().getY(),
				(int) point.getEnd().getX(), (int) point.getEnd().getY()
			);
		}
		return image;
	}

	private List<DrawPoint> filterPoints(List<DrawPoint> points, int fitWidth, int fitHeight) {
		return points;
	}

	private List<DrawPoint> transformPoints(List<DrawPoint> points, int fitWidth, int fitHeight,
	                                        int scale, double angle, Point2D.Double center) {
		//Rotate and make cop
		points = points.stream().map(_dp -> new DrawPoint(
			GeometryUtil.rotatePoint(_dp.getStart(), angle, center),
			GeometryUtil.rotatePoint(_dp.getEnd(), angle, center)
		)).collect(Collectors.toList());
		//Normalisation
		this.move(points, center, scale, fitWidth, fitHeight);
		return points;
	}

	private void move(List<DrawPoint> points, Point2D.Double center, int scale, int fitWidth, int fitHeight) {
		//Keep square form
		int size = Math.min(fitWidth, fitHeight);
		for (DrawPoint point : points) {
			point.getStart().setLocation(
				fitWidth / 2 + (point.getStart().getX() - center.getX()) * scale * size,
				fitHeight / 2 + (point.getStart().getY() - center.getY()) * scale * size
			);
			point.getEnd().setLocation(
				fitWidth / 2 + (point.getEnd().getX() - center.getX()) * scale * size,
				fitHeight / 2 + (point.getEnd().getY() - center.getY()) * scale * size
			);
		}
	}


}
