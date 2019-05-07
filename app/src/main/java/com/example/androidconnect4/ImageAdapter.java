package com.example.androidconnect4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

    private Activity mContext;
    private ConnectBoard connectBoard;
    private TextView time;
    private boolean timeNotNull;
    private TextView cells;
    private int SIZE;
    private String alias;

    public ImageAdapter(Activity c, ConnectBoard connectBoard, String alias, int size,
                        boolean timeNotNull, TextView cells, TextView time){
        mContext = c;
        this.connectBoard = connectBoard;
        this.SIZE = size;
        this.time = time;
        this.timeNotNull = timeNotNull;
        this.alias = alias;
        this.cells = cells;
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
                btn.setLayoutParams(new GridView.LayoutParams(80, 80));
                btn.setPadding(5, 5, 5, 5);
            }
            if(getCount() == 36 ){
                btn.setLayoutParams(new GridView.LayoutParams(60, 60));
                btn.setPadding(5, 5, 5, 5);
            }
            else {
                btn.setLayoutParams(new GridView.LayoutParams(45, 45));
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
            return R.drawable.connect4_logo; //TODO: Afegir fitxa USER
        } else if (connectBoard.getPositionsComputer().contains(position)) {
            return R.drawable.connect4_logo; //TODO: Afegir Fitxa Computer
        } else {
            return R.drawable.connect4_logo; // TODO: Afegir casella buida
        }
    }
    private void updateTextViews() {
        //TODO: Editar textView del torn
    }
    private void createNewActivity() {
        int timeLeft;
        if (timeNotNull) {
            timeLeft = connectBoard.getTime() / Variables.SEGON;
        } else {
            timeLeft = (int) (System.currentTimeMillis() / Variables.SEGON - connectBoard.time);
        }
        Intent intent = new Intent(mContext, ResultActivity.class);
        intent.putExtra(Variables.USER, alias);
        intent.putExtra(Variables.TIME, timeNotNull);
        intent.putExtra(Variables.TIME_LEFT, timeLeft);
        intent.putExtra(Variables.SIZE, SIZE);
        mContext.startActivity(intent);
        mContext.finish();
    }

}
