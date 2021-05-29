package ru.stqa.pft.sandbox;

import java.sql.SQLOutput;

class HelloWorld {
    public static void main(String[] args) {
        Point p1 = new Point(-3,-4);
        Point p2 = new Point(0,0);

        double distance1_2 = distance(p1, p2);
        System.out.println("Расстояние между точками 1 и 2. Расчитано с помощью функции distance");
        System.out.println(distance1_2);
        System.out.println();


        Point p3 = new Point(4, 5);
        Point p4 = new Point(0,0);

        double distance3_4 = p3.distance(p4);
        System.out.println("Расстояние между точками 3 и 4. Расчитано с помощью метода класса distance");
        System.out.println(distance3_4);
        System.out.println();

        // А сейчас создадим класс Point со случайными значениями и передадим в его метод distance также класс со случайными значениями

        Point p5 = new Point(getRandomPoint(),getRandomPoint());
        Point p6 = new Point(getRandomPoint(),getRandomPoint());
        System.out.println("Точки x и y p5");
        System.out.println(p5.getX());
        System.out.println(p5.getY());
        System.out.println("Точки x и y p6");
        System.out.println(p6.getX());
        System.out.println(p6.getY());
        double distance_r1_r2 = p5.distance(p6);
        System.out.println("Расстояние между Случайными точками. Расчитано с помощью метода класса distance");
        System.out.println(distance_r1_r2);
        System.out.println();
    }


    /**
     * 3 задание. Функция для расчета расстояния между 2 точками из разных классов
     * @param p1
     * @param p2
     * @return расстояние между двумя точками
     */
    public static double distance (Point p1, Point p2) {
        double distanceX = p1.getX() - p2.getX();
        double distanceY = p1.getY() - p2.getY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Создадим вспомогательную функцию, которая будет создавать случайные числа от -100 до 100
     */
    public static int getRandomPoint() {
        return (int) (Math.random()*(200+1)) - 100;
    }
}