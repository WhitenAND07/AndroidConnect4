package com.example.androidconnect4;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.androidconnect4.Fragments.GameFragment;
import com.example.androidconnect4.Fragments.GameLogsFragment;
import com.example.androidconnect4.Utils.ConnectBoard;
import com.example.androidconnect4.Utils.ImageAdapter;
import com.example.androidconnect4.Utils.LogCreator;
import com.example.androidconnect4.Utils.Variables;

public class GameActivity extends FragmentActivity implements GameFragment.GameListener {

    private LogCreator logCreator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GameFragment gameF = (GameFragment) getSupportFragmentManager().findFragmentById
                (R.id.fragmentGame);

        gameF.setGameListener(this);
        logCreator = LogCreator.getINSTANCE(this);
    }

    @Override
    public void onGameItemSelected(Integer position, ConnectBoard connectBoard){
        GameLogsFragment gameLogsFragment = (GameLogsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.logFragment);

        if(gameLogsFragment != null && gameLogsFragment.isInLayout()){
            GameLogsFragment gameFragmentDetail = (GameLogsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.logFragment);
            gameFragmentDetail.mostrarLogs(logCreator.logValues(connectBoard, position));
        }

    }
}
