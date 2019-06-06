package com.example.androidconnect4;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.androidconnect4.Fragments.GameFragment;
import com.example.androidconnect4.Fragments.PreferencesFragment;
import com.example.androidconnect4.Utils.SQLite;
import com.example.androidconnect4.Utils.Variables;

import java.util.Date;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar appbar;
    private int size;
    private boolean withTime;
    private int timeLeft;
    private String alias;
    private int turn;
    private int finalcells;

    private EditText date;
    private EditText resume;
    private EditText email;
    private ContentValues registerLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        appbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(appbar);

        date = (EditText) findViewById(R.id.date);
        resume = (EditText) findViewById(R.id.resume);
        email = (EditText) findViewById(R.id.email);

        Button exit = (Button) findViewById(R.id.ResultExitButton);
        exit.setOnClickListener(this);

        Button newGame = (Button) findViewById(R.id.ResultNewButton);
        newGame.setOnClickListener(this);

        Button send = (Button) findViewById(R.id.resultButton);
        send.setOnClickListener(this);

        SQLite sqLite = SQLite.getInstance(getApplicationContext());

        if (savedInstanceState != null) {
            recuperateInstances(savedInstanceState);
        } else {
            Intent intent = getIntent();
            registerLog = new ContentValues();
            getIntentValues(intent);
            setEditTexts();
            if (sqLite.register(registerLog)!= -1){
                createToast(R.string.save, R.drawable.ic_save);
            }
        }
    }
    private void getIntentValues(Intent intent) {
        size = intent.getIntExtra(Variables.SIZE, 0);
        withTime = intent.getBooleanExtra(Variables.TIME, false);
        timeLeft = intent.getIntExtra(Variables.TIME_LEFT, 20);
        alias = intent.getStringExtra(Variables.USER);
        turn = intent.getIntExtra("turn",1);
        finalcells = intent.getIntExtra("full", -1);
        registerLog.put(SQLite.GameTable.SIZE, size);
        registerLog.put(SQLite.GameTable.TIME, withTime);
        registerLog.put(SQLite.GameTable.FINAL_TIME, timeLeft);
        registerLog.put(SQLite.GameTable.USER, alias);
    }

    private void recuperateInstances(Bundle savedInstanceState) {
        date.setText(savedInstanceState.getString(Variables.ResultDate));
        resume.setText(savedInstanceState.getString(Variables.ResultLog));
        email.setText(savedInstanceState.getString(Variables.ResultEmail));
        size = savedInstanceState.getInt(Variables.SIZE, 0);
        withTime = savedInstanceState.getBoolean(Variables.TIME);
        timeLeft = savedInstanceState.getInt(Variables.TIME_LEFT, 0);
        alias = savedInstanceState.getString(Variables.USER);
    }

    private void setEditTexts() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Date actualDate = new Date();
        date.setText(actualDate.toString());
        registerLog.put(SQLite.GameTable.DATE, actualDate.toString());
        registerLog.put(SQLite.GameTable.PLAYERS, prefs.getString(getResources()
                .getString(R.string.PLAYERS), "1"));
        createLog();
    }

    private void createLog() {
        String moreLog = "";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int timetotal = Integer.valueOf(prefs.getString(getResources().
                        getString(R.string.selectTime_key),"60"));
        if (withTime) {
            int totaltime = timetotal - timeLeft;
            moreLog += "\n" + Variables.TotalTime + totaltime + Variables.NANOSEGONS +".";
        }
        else {
            moreLog += "\n" + Variables.TotalTime + timeLeft + Variables.NANOSEGONS +".";
        }
        if (timeLeft == 0) {
            resume.setText(getString(R.string.Alias) + alias + ".\n " +
                    getString(R.string.SizeOfTheGrid) + String.valueOf(size) + "." + moreLog + "\n" +
                    getString(R.string.Time));
            createToast(R.string.Time, R.drawable.connect4_logox30);
            registerLog.put(SQLite.GameTable.POSITION, R.string.Time);
        }

            else if (finalcells == 0) {
                resume.setText(getString(R.string.Alias) + alias + ".\n " +
                        getString(R.string.SizeOfTheGrid) + String.valueOf(size) + "." + moreLog +"\n"+
                        getString(R.string.Draw));
                createToast(R.string.Draw, R.drawable.connect4_logox30);
            registerLog.put(SQLite.GameTable.POSITION, R.string.Draw);

        } else if (turn == 2) {
            resume.setText(getString(R.string.Alias) + alias + ".\n " +
                    getString(R.string.SizeOfTheGrid) + String.valueOf(size) + "." + moreLog +"\n"+
                    getString(R.string.Win));
            createToast(R.string.Win, R.drawable.win);
            registerLog.put(SQLite.GameTable.POSITION, R.string.Win);
        } else if (turn == 1) {
            resume.setText(getString(R.string.Alias) + alias + ".\n " +
                    getString(R.string.SizeOfTheGrid) + String.valueOf(size) + "." + moreLog +"\n"+
                    getString(R.string.Lose));
            createToast(R.string.Lose, R.drawable.fail);
            registerLog.put(SQLite.GameTable.POSITION, R.string.Lose);
        }
    }

    private void createToast(int resourceText, int resourceImage) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.ToastImage);
        image.setImageResource(resourceImage);
        TextView text = (TextView) layout.findViewById(R.id.ToastText);
        text.setText(resourceText);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ResultExitButton:
                finish();
                break;
            case R.id.ResultNewButton:
                Intent intent = new Intent(this, GameActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.resultButton:
                if (!email.getText().toString().isEmpty()) {
                    Intent intent1 = new Intent(Intent.ACTION_SENDTO,
                            Uri.parse("mailto:" + email.getText().toString()));
                    intent1.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
                    intent1.putExtra(Intent.EXTRA_TEXT, resume.getText().toString());
                    startActivity(intent1);
                } else {
                    Toast.makeText(this, "The field email it's empty", Toast.LENGTH_SHORT).show();
                }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.config_option:
                Intent intent1 = new Intent(this, PreferencesFragment.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Variables.ResultDate, date.getText().toString());
        outState.putString(Variables.ResultLog, resume.getText().toString());
        outState.putString(Variables.ResultEmail, email.getText().toString());
        outState.putInt(Variables.SIZE, size);
        outState.putBoolean(Variables.TIME, withTime);
        outState.putInt(Variables.TIME_LEFT, timeLeft);
        outState.putString(Variables.USER, alias);
    }
}