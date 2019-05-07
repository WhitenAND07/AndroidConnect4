package com.example.androidconnect4;

import java.util.List;

public class ConnectBoard {

    public long time;
    private CounterTime clock;
    private List<Integer> UserCells;
    private List<Integer> ComputerCells;

    public int getTime(){
        return 0;
    }

    List<Integer> getPositionsUser() {
        return UserCells;
    }

    List<Integer> getPositionsComputer() {
        return ComputerCells;
    }

    //TODO : Logica del joc.
}
