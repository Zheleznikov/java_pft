package ru.stqa.pft.sandbox;

class HelloWorld {
    public static void main(String[] args) {
        PointP p1 = new PointP(-3,-4);
        PointP p2 = new PointP(0,0);

        double distance1_2 = distance(p1, p2);
        System.out.println("Расстояние между точками 1 и 2. Расчитано с помощью функции distance");
        System.out.println(distance1_2);
        System.out.println();


        PointP p3 = new PointP(4, 5);
        PointP p4 = new PointP(0,0);

        double distance3_4 = p3.distance(p4);
        System.out.println("Расстояние между точками 3 и 4. Расчитано с помощью метода класса distance");
        System.out.println(distance3_4);
        System.out.println();

        // А сейчас создадим класс Point со случайными значениями и передадим в его метод distance также класс со случайными значениями

        PointP p5 = new PointP(getRandomPoint(),getRandomPoint());
        PointP p6 = new PointP(getRandomPoint(),getRandomPoint());
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
    public static double distance (PointP p1, PointP p2) {
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