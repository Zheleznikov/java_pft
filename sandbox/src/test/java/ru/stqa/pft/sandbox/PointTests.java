package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    PointP p1 = new PointP(0, 0);
    PointP p2 = new PointP(0, 0);
    PointP p3 = new PointP(3, 4);

    @Test
    public void pointPositive() {
        Assert.assertEquals(p1.distance(p3), 4.0);
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
