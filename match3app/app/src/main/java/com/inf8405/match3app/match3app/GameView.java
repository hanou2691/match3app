package com.inf8405.match3app.match3app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by habenah on 2017-01-24.
 */

public class GameView extends SurfaceView {
    Paint p = new Paint();
    Paint cellPaint = new Paint();

    Circle[][] circleMatrix;
    Rect[][] cellMatrix;
    int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.MAGENTA};

    int x;
    int y;
    int radius;
    int cellWidth;
    int cellHeight;
    int width;
    int height;
    Point center;

    // Touch coordinates
    float x1,y1,x2,y2;

    Context context;

    public GameView(Context context_) {
        super(context_);

        Log.d("Constructor", "Start");
        context = context_;

        x = ((GameActivity) context).x;
        y = ((GameActivity) context).y;

        // Circle matrix
        circleMatrix = new Circle[x][y];
        cellMatrix = new Rect[x][y];


        cellPaint = new Paint();
        cellPaint.setColor(Color.WHITE);

        cellPaint.setStyle(Paint.Style.STROKE);
        cellPaint.setStrokeWidth(5.0f);

        setWillNotDraw(false);

        Log.d("Constructor", "Done");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.d("onSizeChanged", "Start");

        width = w;
        height = h;
        cellWidth = width / x;
        cellHeight = height / y;

        radius = cellWidth < cellHeight ? cellWidth / 3 : cellHeight / 3;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                // Create Circle
                center = new Point((int) (cellWidth / 2.0) + (i * cellWidth), (int) (cellHeight / 2.0) + (j * cellHeight));
                p = new Paint();
                // TODO : Assign random color
                p.setColor(colors[(int)(Math.random() * 6)]);
                p.setStyle(Paint.Style.FILL);
                p.setStrokeWidth(3.0f);
                circleMatrix[i][j] = new Circle(radius, center, p);

                // Create cell
                cellMatrix[i][j] = new Rect(i * cellWidth, j * cellHeight, (i * cellWidth) + cellWidth,
                        (j * cellHeight) + cellHeight);
            }
        }

        Log.d("onSizeChanged", "Done");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("onDraw", "Start");

        super.onDraw(canvas);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                canvas.drawRect(cellMatrix[i][j], cellPaint);
                canvas.drawCircle(circleMatrix[i][j].center.x, circleMatrix[i][j].center.y,
                        circleMatrix[i][j].radius, circleMatrix[i][j].p);
            }
        }

        Log.d("onDraw", "Done");
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                // TODO : Compare abs(x1 - x2) and abs(y1 - y2) to determine the main swipe direction

                // if left to right sweep event on screen
                if (x1 < x2)
                {
                    Toast.makeText(context, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                }

                // if right to left sweep event on screen
                if (x1 > x2)
                {
                    Toast.makeText(context, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                }

                // if UP to Down sweep event on screen
                if (y1 < y2)
                {
                    Toast.makeText(context, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                }

                // if Down to UP sweep event on screen
                if (y1 > y2)
                {
                    Toast.makeText(context, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
        }
        return true;
        //return super.onTouchEvent(touchevent);
    }
}
