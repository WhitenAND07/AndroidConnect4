package com.example.androidconnect4.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidconnect4.R;
import com.example.androidconnect4.Utils.ConnectBoard;
import com.example.androidconnect4.Utils.ImageAdapter;
import com.example.androidconnect4.Utils.Variables;


public class GameFragment extends Fragment {
    public GameListener listener;
    private int SIZE;
    private boolean time;
    private String player1;
    private int countDown = 40;
    private static boolean mulitplayer;
    private ImageView turn;
    private TextView timing;
    private ConnectBoard connectBoard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPreferences();
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else {
            connectBoard = new ConnectBoard(SIZE);
            connectBoard.initConnectBoard(time, countDown);
        }
        startGridView();
    }

    private void getPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        player1 = prefs.getString(getResources().getString(R.string.user_key),
                getResources().getString(R.string.user_default));
        time = prefs.getBoolean(getResources().getString(R.string.time_key), true);
        mulitplayer = prefs.getBoolean(getResources().getString(R.string.multiplayer_key), false);
        countDown = Integer.valueOf(prefs.getString(getResources().getString(R.string.selectTime_key),
                "60"));
        SIZE = Integer.valueOf(prefs.getString(getResources().getString(R.string.board_key),
                getResources().getString(R.string.board_Default_key)));
        timing = (TextView) getView().findViewById(R.id.timing);
        turn = (ImageView) getView().findViewById(R.id.turn);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        this.player1 = savedInstanceState.getString(getResources().getString(R.string.user_key));
        this.time = savedInstanceState.getBoolean(getResources().getString(R.string.time_key));
        this.countDown = savedInstanceState.getInt(getResources().getString(R.string.selectTime_key));
        this.SIZE = savedInstanceState.getInt(getResources().getString(R.string.board_key));
        this.connectBoard = savedInstanceState.getParcelable(Variables.ConnectBoard);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Variables.ConnectBoard, connectBoard);
        outState.putString(getResources().getString(R.string.user_key), player1);
        outState.putInt(getResources().getString(R.string.board_key), SIZE);
        outState.putBoolean(getResources().getString(R.string.time_key), time);
        outState.putInt(getResources().getString(R.string.selectTime_key), countDown);
    }

    private void startGridView() {
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), connectBoard, player1, SIZE, time,
                turn, timing, listener);
        GridView board = (GridView) getView().findViewById(R.id.board);
        board.setAdapter(imageAdapter);
        board.setNumColumns(SIZE);
    }

    public interface GameListener {
        void onGameItemSelected(Integer position, ConnectBoard connectBoard);
    }

    public void setGameListener(GameListener listener) {
        this.listener = listener;
    }

    public void onAttach(Activity c) {
        super.onAttach(c);
        try {
            listener = (GameListener) c;
        } catch (ClassCastException e) {
            throw new ClassCastException(c.toString() + "No listener");
        }
    }

    public static boolean isMulitplayer() {
        boolean is = mulitplayer;
        if (is) {
            return true;
        }
        return false;
    }
}
