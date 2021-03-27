package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean modeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.modeResult = true;
    }

    public void onClickButtonNumbers(View view){
        Button button = (Button) view;
        String buttonValue = button.getText().toString();
        TextView txtDisplay = findViewById(R.id.txtDisplay);
        if(this.modeResult){
            txtDisplay.setText(buttonValue);
            this.modeResult = false;
        }else{
            txtDisplay.append(buttonValue);
        }
    }

    public void onClickButtonOperation(View view){
        Button button = (Button) view;
        String buttonValue = button.getText().toString();
        TextView txtDisplay = findViewById(R.id.txtDisplay);
        if(this.modeResult){
            txtDisplay.setText(buttonValue);
            this.modeResult = false;
        }else{
            txtDisplay.append(buttonValue);
        }
    }
}