package com.example;

import java.awt.*;

public class LinePaint extends PaintObject {
    Point startPoint;
    Point endPoint;

    public LinePaint() {
    }

    public double getStartX() { return startPoint.getX(); }
    public double getStartY() { return startPoint.getY(); }
    public double getEndX() { return endPoint.getX(); }
    public double getEndY() { return endPoint.getY(); }

    public void define(Point[] points) {
        // Assuming that the first point is the start and the last point is the end.
        this.startPoint = points[0];
        this.endPoint = points[points.length-1];
    }

    public Rectangle getBoundingBox() {
        int minX = Math.min((int)startPoint.getX(), (int)endPoint.getX()) - thickness / 2;
        int minY = Math.min((int)startPoint.getY(), (int)endPoint.getY()) - thickness / 2;
        int maxX = Math.max((int)startPoint.getX(), (int)endPoint.getX()) + thickness / 2;
        int maxY = Math.max((int)startPoint.getY(), (int)endPoint.getY()) + thickness / 2;

        return new Rectangle(minX, minY, maxX-minX, maxY-minY);
    }

    public void paint(Graphics2D g) {
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(thickness));
        g.setColor(color);
        g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
        g.setStroke(oldStroke);
    }
}