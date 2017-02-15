package com.inf8405.match3app.match3app;

/**
 * Created by habenah on 2017-02-11.
 */

class ComboRunnable implements Runnable {
    private final int SLEEP_DURATION_MS = 100;
    private final int SLEEP_DURATION_NS = 0;

    private boolean running = true;
    private GameView view;

    ComboRunnable(GameView v){
        view = v;
    }

    @Override
    public void run() {

        while (true) {
            // Check for combo chains
            for (int i = 0; i < GameView.dim_y ; i++) {
                for (int j = 0; j < GameView.dim_x; j++) {
                    if (running) {
                        view.findMatchHorizontal(j,i,true);
                        view.findMatchVertical(j,i,true);
                        try {
                            Thread.sleep(SLEEP_DURATION_MS, SLEEP_DURATION_NS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void pauseThread() {
        running = false;
    }

    public void resumeThread() {
        running = true;
    }
}
