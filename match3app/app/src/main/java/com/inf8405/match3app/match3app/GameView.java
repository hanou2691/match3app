package com.inf8405.match3app.match3app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.*;

/**
 * Created by habenah on 2017-01-24.
 */

public class GameView extends SurfaceView {

    Paint p = new Paint();
    Paint cellPaint = new Paint();

    public static Circle[][] circleMatrix;
    Rect[][] cellMatrix;

    Thread comboThread;

    public static int dim_x = 0;
    public static int dim_y = 0;
    int cellWidth;
    int cellHeight;

    // First touch pixel coordinates
    float x1,y1;

    // First touch matrix coordinates
    int matX, matY;

    static Context context;

    public GameView(Context context_) {
        super(context_);

        Log.d("Constructor", "Start");
        context = context_;

        dim_x = 1;
        dim_y = 1;

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
    }

    public GameView(Context context_, AttributeSet attrs) {
        super(context_, attrs);

        Log.d("Constructor", "Start");
        context = context_;

        dim_x = ((GameActivity) context).x;
        dim_y = ((GameActivity) context).y;

        // Circle matrix
        circleMatrix = new Circle[dim_y][dim_x];
        cellMatrix = new Rect[dim_y][dim_x];

        cellPaint = new Paint();
        cellPaint.setColor(Color.WHITE);

        cellPaint.setStyle(Paint.Style.STROKE);
        cellPaint.setStrokeWidth(5.0f);

        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cellWidth = w / dim_x;
        cellHeight = h / dim_y;

        int radius = cellWidth < cellHeight ? cellWidth / 3 : cellHeight / 3;

        for (int i = 0; i < dim_y; i++) {
            for (int j = 0; j < dim_x; j++) {
                // Create Circle
                Point center = new Point((int) (cellWidth / 2.0) + (j * cellWidth), (int) (cellHeight / 2.0) + (i * cellHeight));
                p = new Paint();
                p.setColor(GameActivity.colors[i * dim_x + j]);
                p.setStyle(Paint.Style.FILL);
                p.setStrokeWidth(3.0f);
                circleMatrix[i][j] = new Circle(radius, center, p);

                // Create cell
                cellMatrix[i][j] = new Rect(j * cellWidth, i * cellHeight, (j * cellWidth) + cellWidth,
                        (i * cellHeight) + cellHeight);
            }
        }

        // Start combo thread
        comboThread = new ComboThread("ComboThread");
        //comboThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < dim_y; i++) {
            for (int j = 0; j < dim_x; j++) {
                canvas.drawRect(cellMatrix[i][j], cellPaint);
                //if (circleMatrix[i][j] != null)
                    canvas.drawCircle(circleMatrix[i][j].center.x, circleMatrix[i][j].center.y,
                        circleMatrix[i][j].radius, circleMatrix[i][j].p);
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                // Stop combo thread
                /*try {
                    comboThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                // Get second
                float x2 = touchevent.getX();
                float y2 = touchevent.getY();

                // TODO : Compare abs(x1 - x2) and abs(y1 - y2) to determine the main swipe direction
                if(abs(x2 - x1) > abs(y2 - y1)){ // Horizontal swipe
                    if (x1 < x2) {
                        // Swipe Right
                        Toast.makeText(context, "Swipe Right", Toast.LENGTH_SHORT).show();
                        // Make sure the swipe is possible
                        if (matX < (dim_x - 1)) {
                            // Try to find matches
                            int temp_color= circleMatrix[matY][matX].p.getColor();
                            circleMatrix[matY][matX].p.setColor(circleMatrix[matY][matX + 1].p.getColor());
                            circleMatrix[matY][matX + 1].p.setColor(temp_color);

                            // Try to find matches
                            if (!(findMatchVertical(matX + 1, matY) || findMatchVertical(matX, matY) ||
                                    findMatchHorizontal(matX + 1, matY) || findMatchHorizontal(matX,matY))) {
                                // Invalid move
                                circleMatrix[matY][matX + 1].p.setColor(circleMatrix[matY][matX].p.getColor());
                                circleMatrix[matY][matX].p.setColor(temp_color);
                            }
                        }
                    }
                    else {
                        // Swipe Left
                        Toast.makeText(context, "Swipe Left", Toast.LENGTH_SHORT).show();
                        // Make sure the swipe is possible
                        if (matX > 0 ) {
                            int temp_color = circleMatrix[matY][matX].p.getColor();
                            circleMatrix[matY][matX].p.setColor(circleMatrix[matY][matX - 1].p.getColor()) ;
                            circleMatrix[matY][matX - 1].p.setColor(temp_color);

                            // Try to find matches
                            if (!(findMatchVertical(matX - 1, matY) || findMatchVertical(matX, matY) ||
                                    findMatchHorizontal(matX - 1, matY) || findMatchHorizontal(matX,matY))) {
                                // Ivalid move
                                circleMatrix[matY][matX - 1].p.setColor(circleMatrix[matY][matX].p.getColor()) ;
                                circleMatrix[matY][matX].p.setColor(temp_color);
                            }
                        }
                    }
                }
                else { // Vertical swipe
                    if (y1 < y2) {
                        // Swipe Down
                        Toast.makeText(context, "Swipe Down", Toast.LENGTH_SHORT).show();
                        if (matY < (dim_y - 1)){
                            int temp_color = circleMatrix[matY][matX].p.getColor();
                            circleMatrix[matY][matX].p.setColor(circleMatrix[matY + 1][matX].p.getColor()) ;
                            circleMatrix[matY + 1][matX].p.setColor(temp_color);

                            if(!(findMatchVertical(matX, matY + 1) || findMatchVertical(matX, matY) ||
                                    findMatchHorizontal(matX, matY + 1) || findMatchHorizontal(matX,matY))){
                                // Ivalid move
                                circleMatrix[matY + 1][matX].p.setColor(circleMatrix[matY][matX].p.getColor()) ;
                                circleMatrix[matY][matX].p.setColor(temp_color);
                            }
                        }
                    }
                    else {
                        // Swipe Up
                        Toast.makeText(context, "Swipe Up", Toast.LENGTH_SHORT).show();
                        if (matY > 0) {
                            int temp_color = circleMatrix[matY][matX].p.getColor();
                            circleMatrix[matY][matX].p.setColor(circleMatrix[matY - 1][matX].p.getColor()) ;
                            circleMatrix[matY - 1][matX].p.setColor(temp_color);
                            if(!(findMatchVertical(matX, matY - 1) ||  findMatchVertical(matX, matY) ||
                                    findMatchHorizontal(matX, matY - 1) || findMatchHorizontal(matX,matY))){
                                // Ivalid move
                                circleMatrix[matY -1][matX].p.setColor(circleMatrix[matY][matX].p.getColor()) ;
                                circleMatrix[matY][matX].p.setColor(temp_color);
                            }
                        }
                    }
                }

                // Restart combo thread
                //comboThread.run();
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

    public static boolean findMatchVertical(int x, int y) {
        int matchCount = 1;
        int colorToMatch = circleMatrix[y][x].p.getColor();

        int i,j;

        // Run upwards
        for (i = y - 1; i > 0; i--) {
            int color = circleMatrix[i][x].p.getColor();
            if (color != colorToMatch) {
                break;
            }

            matchCount++;
        }

        // Run downwards
        for (j = y + 1; j < dim_y; j++) {
            int color = circleMatrix[j][x].p.getColor();
            if (color != colorToMatch) {
                break;
            }

            matchCount++;
        }

        // Have a chain
        if (matchCount >= 3) {
            for (int k = j - 1; k >= i + 1; k--) {
                int rand_color = GameActivity.colors[(int)(Math.random() * GameActivity.colors.length)];
                if (k - matchCount < 0) {
                    circleMatrix[k][x].p.setColor(rand_color);
                }
                else {
                    circleMatrix[k][x].p.setColor(circleMatrix[k - matchCount][x].p.getColor());
                    circleMatrix[k - matchCount][x].p.setColor(rand_color);
                }
            }

            ((GameActivity) context).calculateScore(matchCount);
            return true;
        }

        return false;
    }

    public static boolean findMatchHorizontal(int x, int y) {
        int matchCount = 1;
        int colorToMatch = circleMatrix[y][x].p.getColor();

        int i,j;

        // Run left
        for (i = x - 1; i > 0; i--) {
            int color = circleMatrix[y][i].p.getColor();
            if (color != colorToMatch) {
                break;
            }

            matchCount++;
        }

        // Run right
        for (j = x + 1; j < dim_x; j++) {
            int color = circleMatrix[y][j].p.getColor();
            if (color != colorToMatch) {
                break;
            }

            matchCount++;
        }

        // Have enough matches
        if (matchCount >= 3) {
            for (int k = j - 1; k >= i + 1; k--) {
                int rand_color = GameActivity.colors[(int) (Math.random() * GameActivity.colors.length)];
                if (y == 0) {
                    // Generate random
                    circleMatrix[y][k].p.setColor(rand_color);
                }
                else {
                    // Drop colors
                    for (int l = y; l > 0; l--) {
                        circleMatrix[l][k].p.setColor(circleMatrix[l - 1][k].p.getColor());
                        circleMatrix[l - 1][k].p.setColor(rand_color);
                    }
                }
            }

            ((GameActivity) context).calculateScore(matchCount);
            return true;
        }

        return false;
    }
    public void moveToNextLevel(int score){


    }
}
