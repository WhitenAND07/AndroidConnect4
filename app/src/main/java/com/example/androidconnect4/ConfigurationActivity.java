package com.example.androidconnect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidconnect4.Utils.Variables;

public class ConfigurationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button twoplayers = (Button) findViewById(R.id.twoplayers);
        startBtn.setOnClickListener(this);
        twoplayers.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText player = (EditText) findViewById(R.id.aliasEditText1);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.size);
        RadioButton sizeGrid = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        CheckBox time = (CheckBox) findViewById(R.id.time);

        switch (view.getId()) {
            case R.id.startBtn:
                if (!player.getText().toString().isEmpty()) {
                    Intent intent = new Intent(this, GameActivity.class);
                    intent.putExtra(Variables.USER, player.getText().toString());
                    intent.putExtra(Variables.SIZE, Integer.parseInt(sizeGrid.getText().toString()));
                    intent.putExtra(Variables.TIME, time.isChecked());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, R.string.toastMSG, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.twoplayers:
                if (!player.getText().toString().isEmpty()) {
                    Intent i = new Intent(this, GameActivity.class);
                    i.putExtra(Variables.USER, player.getText().toString());
                    i.putExtra(Variables.SIZE, Integer.parseInt(sizeGrid.getText().toString()));
                    i.putExtra(Variables.TIME, time.isChecked());
                    i.putExtra("multiplayer", true);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, R.string.toastMSG, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}