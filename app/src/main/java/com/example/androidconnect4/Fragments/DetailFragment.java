package com.example.androidconnect4.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidconnect4.R;
import com.example.androidconnect4.Utils.SQLite;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    public void viewDetails(int cellSelected) {
        ((TextView) getView().findViewById(R.id.TxtDetail)).setText(makeText(cellSelected));
    }

    private String makeText(int position) {
        SQLite database = SQLite.getInstance(getContext());
        Cursor cursor = database.getDataFromDB();
        cursor.moveToPosition(position);
        String textGame = "";
        textGame += SQLite.GameTable.USER + ": " + cursor.getString(1) + '\n' +
                SQLite.GameTable.DATE + ": " + cursor.getString(2) + "\n" +
                SQLite.GameTable.SIZE + ": " + cursor.getString(3) + "\n" +
                SQLite.GameTable.TIME + ": " + cursor.getString(4) + "\n" +
                SQLite.GameTable.PLAYERS + ": " + cursor.getString(5) + "\n" +
                SQLite.GameTable.FINAL_TIME + ": " + cursor.getString(6) + "\n" +
                SQLite.GameTable.POSITION + ": " +
                getResources().getString(Integer.valueOf(cursor.getString(7))) + "\n";
        return textGame;
    }

}
