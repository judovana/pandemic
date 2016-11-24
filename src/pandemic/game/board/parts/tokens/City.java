/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Pípa
 */
public class City {

    static City create(String point, String name, String color) {
        Color c = null;
        switch (color) {
            case "yellow":
                c = Color.YELLOW;
                break;
            case "blue":
                c = Color.BLUE;
                break;
            case "red":
                c = Color.red;
                break;
            case "black":
                c = Color.black;
                break;
            default:
                throw new IllegalArgumentException("Invalid type of color: " + color);
        }
        String[] pointParts = point.split(",");
        Point p = new Point(Integer.valueOf(pointParts[0]), Integer.valueOf(pointParts[1]));
        System.out.println("Creating: " + name + ", " + p.toString() + ", " + c);
        return new City(p, name, c);

    }
    private Station station;
    private Collection<Cubes> cubes;

    private final Point center;
    private final String name;
    private final Color color;
    private final Collection<City> neighbors = new ArrayList<>();

    private City(Point center, String name, Color color) {
        this.center = center;
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    void addNeighbor(City neighbor) {
        neighbors.add(neighbor);
    }

    public Collection<City> getNeighbors() {
        return Collections.unmodifiableCollection(neighbors);
    }

    boolean isOn(Point point) {
        return (center.distance(point)) < 20;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(center.x - 10, center.y - 10, 20, 20);
        drawOutlined(Color.white, color, g2d, center.x, center.y, name);
        for (City city : neighbors) {
            g2d.setColor(Color.white);
            g2d.drawLine(center.x, center.y, city.center.x, city.center.y);
            drawOutlined(Color.white, city.color, g2d, city.center.x, city.center.y, city.name);
        }

    }

    private void drawOutlined(Color c1, Color c2, Graphics2D g2d, int x, int y, String s) {
        g2d.setColor(new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 100));
        g2d.fillRect(x, y, g2d.getFontMetrics().stringWidth(s), g2d.getFontMetrics().getHeight());
        g2d.setColor(c2);
        g2d.drawString(s, x, y + g2d.getFontMetrics().getHeight());
    }

}
