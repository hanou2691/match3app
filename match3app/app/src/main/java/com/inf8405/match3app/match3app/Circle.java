package com.inf8405.match3app.match3app;

import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by habenah on 2017-01-26.
 */

public class Circle {
    public int radius;
    public Point center;
    public Paint p = new Paint();

    public Circle(int radius, Point center, Paint p) {
        this.radius = radius;
        this.center = center;
        this.p = p;
    }
}
