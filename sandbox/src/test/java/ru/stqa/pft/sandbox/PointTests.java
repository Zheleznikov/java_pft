package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    Point p3 = new Point(3, 4);

    @Test
    public void pointPositive() {
        Assert.assertEquals(p1.distance(p3), 5.0);
    }

    @Test
    public void pointZero() {
        Assert.assertEquals(p1.distance(p2), 0);
    }

    @Test
    public void pointNegative() {
        Assert.assertFalse(p1.distance(p3) == 4);
    }
}
