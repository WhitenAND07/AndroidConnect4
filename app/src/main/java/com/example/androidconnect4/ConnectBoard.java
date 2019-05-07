package com.example.androidconnect4;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ConnectBoard implements Parcelable {
    public static final Creator<ConnectBoard> CREATOR = new Creator<ConnectBoard>() {
        @Override
        public ConnectBoard createFromParcel(Parcel in) {
            return new ConnectBoard(in);
        }

        @Override
        public ConnectBoard[] newArray(int size) {
            return new ConnectBoard[size];
        }
    };

    long time;
    private int turn = 1;
    boolean timeEnd = false;
    private int size;
    private CounterTime clock;
    private int[][] connectBoard;
    private List<Integer> UserCells;
    private List<Integer> ComputerCells;

    int getTime(){
        return 0;
    }

    public ConnectBoard(int size) {
        this.size = size;
        this.connectBoard = new int[size][size];
    }

    private ConnectBoard(Parcel in) {
        turn = in.readInt();
        size = in.readInt();
    }
    public void initConnectBoard(boolean withTime, int time) {
        UserCells = new ArrayList<>();
        ComputerCells = new ArrayList<>();
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                this.connectBoard[x][y] = 0;
            }
        }
        if (withTime) {
            clock = new CounterTime(time * Variables.SEGON, Variables.SEGON, this);
            clock.start();
        } else {
            this.time = System.currentTimeMillis() / Variables.SEGON;
        }
    }

    List<Integer> getPositionsUser() {
        return UserCells;
    }

    List<Integer> getPositionsComputer() {
        return ComputerCells;
    }

    void changeTurn() {
        if (turn == 1) {
            this.turn = 2;
        } else {
            this.turn = 1;
        }
    }

    boolean isEnd() {
        return size * size - getPositionsUser().size() - getPositionsComputer().size() == 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(connectBoard);
        parcel.writeInt(size);
        parcel.writeInt(turn);
        parcel.writeList(UserCells);
        parcel.writeList(ComputerCells);
    }

    //TODO : Logica del joc.
}
