package com.example.androidconnect4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.androidconnect4.Utils.ConnectBoard;
import com.example.androidconnect4.Utils.ImageAdapter;
import com.example.androidconnect4.Utils.Variables;

public class GameActivity extends AppCompatActivity {

    private int SIZE;
    private boolean time;
    private String player1;
    private int countDown = 40;

    private TextView turn,timing;

    private ConnectBoard connectBoard;
    private GridView board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getConfiguration();
        if (savedInstanceState == null) initGame();
        else getBackState(savedInstanceState);
        initGridView();
    }

    private void initGridView() {
        ImageAdapter imageAdapter = new ImageAdapter(this, connectBoard, player1, SIZE, time,
                turn, timing);
        this.board = (GridView) findViewById(R.id.board);
        this.board.setAdapter(imageAdapter);
        this.board.setNumColumns(SIZE);
    }

    private void getBackState(Bundle savedInstanceState) {
        connectBoard = savedInstanceState.getParcelable(Variables.ConnectBoard);
        this.player1 = savedInstanceState.getString(Variables.USER);
        this.SIZE = savedInstanceState.getInt(Variables.SIZE);
        this.time = savedInstanceState.getBoolean(Variables.TIME);
    }

    private void initGame() {
        connectBoard = new ConnectBoard(SIZE);
        connectBoard.initConnectBoard(time, countDown);
    }

    private void getConfiguration() {
        player1 = getIntent().getStringExtra(Variables.USER);
        SIZE = getIntent().getIntExtra(Variables.SIZE, 7);
        time = getIntent().getBooleanExtra(Variables.TIME, false);
        turn = (TextView) findViewById(R.id.turn);
        timing = (TextView) findViewById(R.id.timing);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Variables.ConnectBoard, connectBoard);
        outState.putString(Variables.USER, player1);
        outState.putInt(Variables.SIZE, SIZE);
        outState.putBoolean(Variables.TIME, time);
    }
}
