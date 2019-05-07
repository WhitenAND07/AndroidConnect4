package com.example.androidconnect4;

import android.os.CountDownTimer;

public class CounterTime extends CountDownTimer {

    private long timeToFinish;
    private ConnectBoard connectBoard;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    CounterTime(long millisInFuture, long countDownInterval, ConnectBoard connectBoard) {
        super(millisInFuture, countDownInterval);
        this.connectBoard = connectBoard;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.timeToFinish = millisUntilFinished;
    }

    @Override
    public void onFinish() {

    }

    public long getTime(){
        return timeToFinish;
    }
}
