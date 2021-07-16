package ru.stqa.pft.sandbox;

public class PointP {
    private double x;
    private double y;

    public PointP(double x, double y) {
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

    public double distance(PointP anotherPoint) {
        double distanceX = getX() - anotherPoint.getX();
        double distanceY = getY() - anotherPoint.getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
}
