package ru.stqa.pft.sandbox;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * 4 задание. Метод внутри класса, который рассчитывает расстояние между точкой
     * из класса и точкой из другого класса
     * @param anotherPoint
     * @return расстояние между двумя точками
     */

    public double distance(Point anotherPoint) {
        double distanceX = getX() - anotherPoint.getX();
        double distanceY = getY() - anotherPoint.getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
}
