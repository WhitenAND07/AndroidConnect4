package com.example.androidconnect4;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(appbar);
        PreferenceManager.setDefaultValues(this,R.xml.preferences, false);

        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        Button btnInit = (Button) findViewById(R.id.btnInit);
        Button btnConsult = (Button) findViewById(R.id.btnConsult);
        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnHelp.setOnClickListener(this);
        btnInit.setOnClickListener(this);
        btnConsult.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnConsult.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHelp:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnInit:
                Intent intent1 = new Intent(this, GameActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btnConsult:
                Intent intent2 = new Intent(this, ConsultActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btnExit:
                finish();
                break;

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.config_option:
                Intent intent1 = new Intent(this, PreferencesActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}