package com.inf8405.match3app.match3app;

/**
 * Created by habenah on 2017-02-11.
 */

class ComboThread implements Runnable {
    private boolean running = true;

    ComboThread(String name){

    }

    @Override
    public void run() {

        while (true) {
            // Check for combo chains
            for (int i = 0; i < GameView.dim_y ; i++) {
                for (int j = 0; j < GameView.dim_x; j++) {
                    if (running) {
                        GameView.findMatchHorizontal(j,i,true);
                        GameView.findMatchVertical(j,i,true);
                        try {
                            Thread.sleep(100,0);
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
