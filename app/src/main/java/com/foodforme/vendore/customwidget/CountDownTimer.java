package com.foodforme.vendore.customwidget;

import android.os.Handler;
import android.util.Log;

import java.util.Date;

public class CountDownTimer {
    private static final String TAG = CountDownTimer.class.getSimpleName();
    private static Handler handler;
    private static Runnable runnable;

    public static void countDownStart(final long matchStartTime, final CustomTextView matchStatus) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date futureDate = new Date(matchStartTime);
                    Date currentDate = new Date();
                    if (currentDate.before(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        matchStatus.setText(days + "d " + hours + "h " + minutes + "m " + seconds + "s left");
                    } else {
                        Log.e(TAG, "match over...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
