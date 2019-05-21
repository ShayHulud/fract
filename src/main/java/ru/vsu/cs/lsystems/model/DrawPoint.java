package ru.vsu.cs.lsystems.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.geom.Point2D;

@Data
@AllArgsConstructor
public class DrawPoint {
	public Point2D.Double start;
	public Point2D.Double end;
}
