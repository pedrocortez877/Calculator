package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private boolean modeResult;
    private TextView fieldResults;
    private TextView fieldHistory;
    private boolean isOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fieldHistory = findViewById(R.id.txtHistory);
        this.fieldResults = findViewById(R.id.txtDisplay);
        this.clearFields();
    }

    public void onClickButton(View view) {
        Button button = (Button) view;
        String buttonValue = button.getText().toString();

        switch(buttonValue){
            case "+":
            case "-":
            case "x":
            case "÷":
            case "C":
            case "CE":
                this.applyOperation(buttonValue);
                break;
            case ",":
                this.decimalNumber();
                break;
            case "=":
                this.calcResults();
                modeResult = true;
                break;
            default:
                this.applyNumbers(buttonValue);
        }
    }

    public void applyOperation(String operation){
        if(operation.equals("C") || operation.equals("CE")){
            this.clearFields();
        }else{
            if(!modeResult && !isOperation && !fieldHistory.getText().toString().isEmpty()){
                this.calcResults();
            }
            this.fieldHistory.setText(this.fieldResults.getText().toString().concat(operation));
            isOperation = true;
        }
    }

    public void decimalNumber(){
        if(modeResult || isOperation){
            this.fieldResults.setText("0");
            modeResult = false;
        }
        if(!fieldResults.getText().toString().contains(".")){
            fieldResults.append(".");
        }
        isOperation = false;
    }

    public void applyNumbers(String number) {
        String txtResults = this.fieldResults.getText().toString();
        String txtHistory = this.fieldHistory.getText().toString();
        if(txtResults.equals("0")){
            this.fieldResults.setText(number);
        }else{
            if(txtHistory.isEmpty()){
                this.fieldResults.append(number);
            }else {
                if (txtHistory.endsWith("=")) {
                    this.fieldResults.setText(number);
                    this.fieldHistory.setText("");
                } else {
                    if (modeResult || isOperation) {
                        this.fieldResults.setText("");
                    }
                    this.fieldResults.append(number);
                }
            }
        }
        modeResult = false;
        isOperation = false;
    }

    public void clearFields() {
        this.fieldResults.setText("0");
        this.fieldHistory.setText("");
        this.modeResult = true;
        this.isOperation = false;
    }

    public void calcResults() {
        String txtHistory = this.fieldHistory.getText().toString();

        if(txtHistory.contains("=")){
            txtHistory = txtHistory.replaceAll("=", "");
            if(txtHistory.contains("+")){
                this.repeatOperation("+", txtHistory, "\\+");
            }else if(txtHistory.contains("x")){
                this.repeatOperation("x", txtHistory, "x");
            }else if(txtHistory.contains("÷")){
                this.repeatOperation("÷", txtHistory, "÷");
            }else if(txtHistory.contains("-")){
                this.repeatOperation("-", txtHistory, "-");
            }
        }else{
            this.fieldHistory.append(this.fieldResults.getText().toString());
        }
        txtHistory = this.fieldHistory.getText().toString();
        if(txtHistory.contains("+")){
            this.sum(txtHistory);
        }else if(txtHistory.contains("x")){
            this.multiply(txtHistory);
        }else if(txtHistory.contains("÷")){
            this.division(txtHistory);
        }else if(txtHistory.contains("-")){
            this.subtract(txtHistory);
        }
        this.fieldHistory.append("=");
    }

    public void sum(String equation) {
        String[] numbers = getNumbers(equation, "\\+");
        if(numbers.length >= 2){
            Double results = new BigDecimal(numbers[0]).add(new BigDecimal(numbers[1])).doubleValue();
            this.fieldResults.setText(results.toString());
        }
    }

    public void multiply(String equation){
        String[] numbers = getNumbers(equation, "x");
        if(numbers.length >= 2){
            Double results = new BigDecimal(numbers[0]).multiply(new BigDecimal(numbers[1])).doubleValue();
            this.fieldResults.setText(results.toString());
        }
    }

    public void division(String equation){
        String[] numbers = getNumbers(equation, "÷");
        if(numbers.length >= 2){
            Double results = new BigDecimal(numbers[0]).divide(new BigDecimal(numbers[1])).doubleValue();
            this.fieldResults.setText(results.toString());
        }
    }

    public void subtract(String equation){
        String[] numbers = getNumbers(equation, "-");
        if(numbers.length >= 2){
            Double results = new BigDecimal(numbers[0]).subtract(new BigDecimal(numbers[1])).doubleValue();
            this.fieldResults.setText(results.toString());
        }
    }

    public String[] getNumbers(String equation, String operation){
        String[] numbers;
        if(equation.startsWith("-")){
            numbers = equation.substring(1).split(operation);
            numbers[0] = "-".concat(numbers[0]);
        }else{
            numbers = equation.split(operation);
        }
        return numbers;
    }

    public void repeatOperation(String operation, String numbersOperation, String symbol){
        String[] numbers = getNumbers(numbersOperation, symbol);
        if(numbers.length >= 2){
            numbers[0] = this.fieldResults.getText().toString();
            this.fieldHistory.setText(numbers[0].concat(operation).concat(numbers[1]));
        }
    }
}