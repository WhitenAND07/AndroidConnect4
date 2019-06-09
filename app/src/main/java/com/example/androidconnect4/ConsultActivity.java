package com.example.androidconnect4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.androidconnect4.DetailActivity;
import com.example.androidconnect4.Fragments.DetailFragment;
import com.example.androidconnect4.Fragments.ListFragment;
import com.example.androidconnect4.MainActivity;
import com.example.androidconnect4.R;

public class ConsultActivity extends FragmentActivity implements ListFragment.GameListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        ListFragment fragmentList = (ListFragment) getSupportFragmentManager().
                findFragmentById(R.id.ListFrag);
        fragmentList.setGameListener(this);
    }
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onGameSelected(int position) {
        DetailFragment fgdet = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.FrgDetalle);
        if (fgdet != null && fgdet.isInLayout()) {
            fgdet.viewDetails(position);
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.CellSelected, position);
            startActivity(intent);
        }
    }
}
