/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pandemic.game.board.parts.tokens;

import j2a.Color;
import j2a.GraphicsCanvas;
import j2a.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import pandemic.game.board.parts.Outbreaks;

/**
 *
 * @author Pípa
 */
public class City {

    static City create(String point, String name, String color) {
        Color c = stringToColor(color);
        String[] pointParts = point.split(",");
        Point p = j2a.Factory.Point.newPoint(Integer.valueOf(pointParts[0]), Integer.valueOf(pointParts[1]));
        System.out.println("Creating: " + name + ", " + p.toString() + ", " + c);
        return new City(p, name, c);

    }

    public static Color stringToColor(String color1) throws IllegalArgumentException {
        switch (color1) {
            case "yellow":
                return j2a.Factory.Color.getYELLOW();
            case "blue":
                return j2a.Factory.Color.getBLUE();
            case "red":
                return j2a.Factory.Color.getRED();
            case "black":
                return j2a.Factory.Color.getBLACK();
            default:
                throw new IllegalArgumentException("Invalid type of color: " + color1);
        }
    }

    private Station station;
    private final List<Cubes> cubes = new ArrayList<>(3);

    private final Point center;
    private final String name;
    private final Color color;
    private final Collection<City> neighbors = new ArrayList<>();
    private final int radius = 10;

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
        return (center.distance(point)) < 2 * radius;
    }

    public void draw(GraphicsCanvas g2d) {
        g2d.setColor(color);
        g2d.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
        drawOutlined(j2a.Factory.Color.getWHITE(), color, g2d, center.getX(), center.getY(), name);
        for (City city : neighbors) {
            g2d.setColor(j2a.Factory.Color.getWHITE());
            g2d.drawLine(center.getX(), center.getY(), city.center.getX(), city.center.getY());
            drawOutlined(j2a.Factory.Color.getWHITE(), city.color, g2d, city.center.getX(), city.center.getY(), city.name);
        }

    }

    private void drawOutlined(Color c1, Color c2, GraphicsCanvas g2d, int x, int y, String s) {
        g2d.setColor(j2a.Factory.Color.newColor(c1.getRed(), c1.getGreen(), c1.getBlue(), 100));
        g2d.fillRect(x, y, g2d.getFontMetrics().stringWidth(s), g2d.getFontMetrics().getHeight());
        g2d.setColor(c2);
        g2d.drawString(s, x, y + g2d.getFontMetrics().getHeight());
    }

    public Point getCenter() {
        return center;
    }

    void drawStation(GraphicsCanvas g2d) {
        if (station != null) {
            station.draw(g2d);
        }
        for (Cubes cube : cubes) {
            cube.draw(g2d);
        }
    }

    /*
    Random order was abandoned. This is making line
    */
     Point randomize(int i, Point center) {
        int x = center.getX() + (radius * i) / 2;
        int y = center.getY() + (radius * i) / 2;
        return j2a.Factory.Point.newPoint(x, y);
    }

    public Color getColor() {
        return color;
    }

    public boolean isNigbouring(City found) {
        return neighbors.contains(found);
    }

    public boolean haveStation() {
        return station != null;
    }

    public void setStation() {
        station = new Station(center);
    }

    public List<Cubes> getCubes() {
        return cubes;
    }

    public boolean infect(Color c, List<City> processed) {
        int sameColors = 0;
        for (Cubes cube : cubes) {
            if (cube.getColor().equals(c)) {
                sameColors++;
            }
        }
        if (sameColors < 3) {
            cubes.add(new Cubes(randomize(cubes.size(), center), c));
            return false;
        }
        //this is hart of algotihm
        //disease canspread to cities where alredy was spread
        //but it can not spread back to cities,where already was panedmy
        //this algorithm is hyper fun when yo see it on real board!
        processed.add(this);
        Outbreaks.self.addOutbreak();
        for (City n : neighbors) {
            if (!processed.contains(n)) {
                n.infect(c, processed);
            }
        }
        return true;
    }
    /**
     * return the number of cubes of given color in this city
     * @param c desired color
     * @return number of cubes
     */
    public int getCubesCount(Color c) {
        int i = 0;
        for (Cubes cube : cubes) {
            if (cube.color.equals(c)) {
                i++;
            }
        }
        return i;
    }

}
