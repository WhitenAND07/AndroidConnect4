package com.example.androidconnect4.Utils;

import java.io.Serializable;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.androidconnect4.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogCreator implements Serializable {

    private static LogCreator INSTANCE = null;

    private final boolean timeActive;
    private String log = "";
    private String time;
    private String size;


    private LogCreator(Activity ac) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ac);
        timeActive = prefs.getBoolean(ac.getString(R.string.time_key), false);
        size = prefs.getString(ac.getString(R.string.board_key), "ERROR, can't get it");
        time = new SimpleDateFormat("hh:mm:ss").format(new Date());
        log += "Alias: " + prefs.getString(ac.getString(R.string.user_key), "Alias invàlid") + "\n"
                + "Grid Size: " + size + "\n";
        if (timeActive) {
            log += "Time Enabled \n";
        } else {
            log += "Time Diasbled \n";

        }
    }

    public static LogCreator getINSTANCE(Activity c) {
        if (INSTANCE == null) {
            INSTANCE = new LogCreator(c);
        }
        return INSTANCE;
    }

    static void deleteLog() {
        INSTANCE = null;
    }

    public String logValues(ConnectBoard connectBoard, Integer position){

        if(connectBoard.getTurn()==2){
            log += "Turn 1" + "\n";
        } else{
            log += "Turn 2" + "\n";
        }

        log += "Position selected: " + String.valueOf(position / Integer.valueOf(size)) + "," +
                String.valueOf(position % Integer.valueOf(size)) + "\n";

        log += "Remaining positions: " + String.valueOf(Integer.valueOf(size)* Integer.valueOf(size) -
                connectBoard.getPositionsComputer().size() - connectBoard.getPositionsUser().size())
                + "\n";


        log += "Time start: " + this.time + "seconds;  Time finish: " + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "\n";;

        if (timeActive) {
            log += "Remaining time: " + String.valueOf(connectBoard.getTime() / Variables.SEGON) +
                    " secs. \n";
        }

        return log;
    }
}
