package com.inf8405.match3app.match3app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import static java.lang.Math.*;

/**
 * Created by habenah on 2017-01-24.
 */

public class GameView extends SurfaceView {
    Paint p = new Paint();
    Paint cellPaint = new Paint();

    Circle[][] circleMatrix;
    Rect[][] cellMatrix;
    int[] colors = new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.MAGENTA};

    int dim_x = 0;
    int dim_y = 0;
    int radius;
    int cellWidth;
    int cellHeight;
    int width;
    int height;
    Point center;

    // First touch pixel coordinates
    float x1,y1;

    // First touch matrix coordinates
    int matX, matY;

    Context context;

    public GameView(Context context_) {
        super(context_);

        Log.d("Constructor", "Start");
        context = context_;

        dim_x = 10;//((GameActivity) context).x;
        dim_y = 10;//((GameActivity) context).y;

        // Circle matrix
        circleMatrix = new Circle[dim_x][dim_y];
        cellMatrix = new Rect[dim_x][dim_y];

        cellPaint = new Paint();
        cellPaint.setColor(Color.WHITE);

        cellPaint.setStyle(Paint.Style.STROKE);
        cellPaint.setStrokeWidth(5.0f);

        setWillNotDraw(false);

        Log.d("Constructor", "Done");
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public GameView(Context context_, AttributeSet attrs) {
        super(context_, attrs);

        Log.d("Constructor", "Start");
        context = context_;

        dim_x = ((GameActivity) context).x;
        dim_y = ((GameActivity) context).y;

        // Circle matrix
        circleMatrix = new Circle[dim_x][dim_y];
        cellMatrix = new Rect[dim_x][dim_y];

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
        cellWidth = width / dim_x;
        cellHeight = height / dim_y;

        radius = cellWidth < cellHeight ? cellWidth / 3 : cellHeight / 3;

        for (int i = 0; i < dim_x; i++) {
            for (int j = 0; j < dim_y; j++) {
                // Create Circle
                center = new Point((int) (cellWidth / 2.0) + (i * cellWidth), (int) (cellHeight / 2.0) + (j * cellHeight));
                p = new Paint();
                // TODO : Assign random color
                p.setColor(colors[(int)(random() * 6)]);
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

        for (int i = 0; i < dim_x; i++) {
            for (int j = 0; j < dim_y; j++) {
                canvas.drawRect(cellMatrix[i][j], cellPaint);
                if (circleMatrix[i][j] != null)
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
                float x2 = touchevent.getX();
                float y2 = touchevent.getY();

                // TODO : Compare abs(x1 - x2) and abs(y1 - y2) to determine the main swipe direction
                if(abs(x2 - x1) > abs(y2 - y1)){
                    if (x1 < x2) {
                        // Swipe Right
                        Toast.makeText(context, "Swipe Right", Toast.LENGTH_SHORT).show();
                        // Make sure the swipe is possible
                        if (matX < (dim_x - 1)) {
                            // Try to find matches
                            Circle temp = circleMatrix[matX][matY];
                            circleMatrix[matX][matY] = circleMatrix[matX + 1][matY];
                            circleMatrix[matX + 1][matY] = temp;

                            // Try to find matches
                            findMatchVertical(matX + 1, matY);
                            findMatchVertical(matX, matY);
                            // No combo, reset
                        }
                    }
                    else {
                        // Swipe Left
                        Toast.makeText(context, "Swipe Left", Toast.LENGTH_SHORT).show();
                        // Make sure the swipe is possible
                        if (matX > 0 ) {
                            Circle temp = circleMatrix[matX][matY];
                            circleMatrix[matX][matY] = circleMatrix[matX - 1][matY];
                            circleMatrix[matX - 1][matY] = temp;
                        }
                    }
                }
                else { // Vertical swipe
                    if (y1 < y2) {
                        // Swipe Down
                        Toast.makeText(context, "Swipe Down", Toast.LENGTH_SHORT).show();
                        if (matY < (dim_y - 1)){
                            Circle temp = circleMatrix[matX][matY];
                            circleMatrix[matX][matY] = circleMatrix[matX][matY + 1];
                            circleMatrix[matX][matY + 1] = temp;
                        }

                    }
                    else {
                        // Swipe Up
                        Toast.makeText(context, "Swipe Up", Toast.LENGTH_SHORT).show();
                        if (matY > 0) {
                            Circle temp = circleMatrix[matX][matY];
                            circleMatrix[matX][matY] = circleMatrix[matX][matY - 1];
                            circleMatrix[matX][matY - 1] = temp;
                        }
                    }
                }

                break;
            }
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();

                // find in matrix
                matX = (int) (x1 / cellWidth);
                matY = (int) (y1 / cellHeight);

                break;
            }
        }
        return true;
    }

    private void findMatchVertical(int x, int y) {
        int matchCount = 1;
        int colorToMatch = circleMatrix[x][y].p.getColor();

        int i,j;
        // Run upwards
        for (i = y; i > 0; i--) {
            if (circleMatrix[x][i].p.getColor() != colorToMatch) {
                break;
            }

            matchCount++;
        }

        // Run downwards
        for (j = y; j < dim_y; j++) {
            if (circleMatrix[x][j].p.getColor() != colorToMatch) {
                break;
            }

            matchCount++;
        }

        // Have enough matches
        if (matchCount >= 3) {
            for (int k = i; k < j; k++) {
                circleMatrix[x][k] = null;
            }
        }
    }
}
