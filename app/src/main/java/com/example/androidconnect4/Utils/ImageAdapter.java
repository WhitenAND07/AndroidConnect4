package com.example.androidconnect4.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidconnect4.Fragments.GameFragment;
import com.example.androidconnect4.R;
import com.example.androidconnect4.ResultActivity;

public class ImageAdapter extends BaseAdapter {

    private Activity mContext;
    private ConnectBoard connectBoard;
    private TextView time;
    private boolean timeNotNull;
    private TextView turn;
    private int SIZE;
    private String alias;

    public ImageAdapter(Activity c, ConnectBoard connectBoard, String alias, int size,
                        boolean timeNotNull, TextView turn, TextView time){
        mContext = c;
        this.connectBoard = connectBoard;
        this.SIZE = size;
        this.time = time;
        this.timeNotNull = timeNotNull;
        this.alias = alias;
        this.turn = turn;
    }

    private void updateTime() {
        if (timeNotNull) {
            time.setText(String.valueOf(connectBoard.getTime() / Variables.SEGON));
            time.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            time.setText(String.valueOf((System.currentTimeMillis() / Variables.SEGON) -
                    connectBoard.time));
            time.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }
    }

    @Override
    public int getCount() {
        return SIZE *SIZE;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null){
            btn = new Button(mContext);
            if(getCount() == 25 ){
                btn.setLayoutParams(new GridView.LayoutParams(150, 150));
                btn.setPadding(5, 5, 5, 5);
            }
            else if(getCount() == 36 ){
                btn.setLayoutParams(new GridView.LayoutParams(130, 130));
                btn.setPadding(5, 5, 5, 5);
            }
            else {
                btn.setLayoutParams(new GridView.LayoutParams(100, 100));
                btn.setPadding(5, 5, 5, 5);
            }
        }
        else {
            btn = (Button) convertView;
        }

        btn.setBackgroundResource(setPiece(position));
        btn.setOnClickListener(new MyOnClickListener(position, mContext));
        btn.setId(position);
        return btn;
    }

    private int setPiece(int position) {
        if (connectBoard.getPositionsUser().contains(position)) {
            return R.drawable.celausuari;
        } else if (connectBoard.getPositionsComputer().contains(position)) {
            return R.drawable.celaoponent;
        } else if (connectBoard.getPositionsPossibleCells().contains(position)) {
            return R.drawable.celapossible;
        } else {
            return R.drawable.celabuida;
        }
    }


    private void updateTextViews() {
        this.turn.setText(String.valueOf(connectBoard.turn));
    }
    private void createNewActivity() {
        int timeLeft;
        if (timeNotNull) {
            if(connectBoard.timeEnd){
                timeLeft =0;
            }else {
                timeLeft = connectBoard.getTime() / Variables.SEGON;
            }
        } else {
            timeLeft = (int) (System.currentTimeMillis() / Variables.SEGON - connectBoard.time);
        }
        Intent intent = new Intent(mContext, ResultActivity.class);
        intent.putExtra(Variables.USER, alias);
        intent.putExtra(Variables.TIME, timeNotNull);
        intent.putExtra(Variables.TIME_LEFT, timeLeft);
        intent.putExtra(Variables.SIZE, SIZE);
        intent.putExtra("turn", connectBoard.turn);
        intent.putExtra("full", connectBoard.finalcells);
        mContext.startActivity(intent);
        mContext.finish();
    }

    private int randomCPU() {
        int randomInt = 100;
        while (!connectBoard.getPositionsPossibleCells().contains(randomInt)) {
            randomInt = (int) (Math.random() * (SIZE * SIZE));
        }
        return randomInt;
    }

    
    private class MyOnClickListener implements View.OnClickListener {
        private int position;
        private Context context;

        MyOnClickListener(int position, Context context) {
            this.position = position;
            this.context = context;

        }

        public void onClick(View v) {
            if (connectBoard.getPositionsPossibleCells().contains(position)) {
                doTheMovement(position);
                if (isFinal()){ createNewActivity();}
                else {
                    if (!GameFragment.isMulitplayer()) {
                        position = randomCPU();
                        doTheMovement(position);
                        if (isFinal()) {
                            createNewActivity();
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Invalid Movement. Try again", Toast.LENGTH_SHORT).show();
            }
        }
        private void doTheMovement(int position) {
            connectBoard.fillCell(position);
            connectBoard.changeTurn();
            connectBoard.getPositionsPossible();
            update();
        }
        private void update() {
            updateTextViews();
            updateTime();
            notifyDataSetChanged();
        }

        private boolean isFinal() {
            if (connectBoard.isEnd(position)) {
                return true;
            } else {
                if (connectBoard.timeEnd){

                    return true;
                }
                else {
                    return false;
                }
            }
        }


        }
    }
