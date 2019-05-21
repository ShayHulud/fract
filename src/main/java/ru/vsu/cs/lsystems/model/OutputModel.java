package ru.vsu.cs.lsystems.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

@Data
@AllArgsConstructor
public class OutputModel {
	private final BufferedImage image;
	private final Point2D.Double leftTopCorner;
	private final Point2D.Double rightBottomCorner;
}
