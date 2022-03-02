package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mEtAmt,mEtRate,mEtDate;
    RadioGroup mRadioGroup;
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize controller
        mEtAmt = findViewById(R.id.mEtAmt);
        mEtRate = findViewById(R.id.mEtRate);
        mEtDate = findViewById(R.id.mEtDate);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        mTvResult = findViewById(R.id.mTvResult);

        //bind with click intent
        findViewById(R.id.mBtnSure).setOnClickListener(v -> doAction());
        findViewById(R.id.mBtnNext).setOnClickListener(v -> {
            if(mTvResult.getText().toString().contains("Average monthly")){
                //jump to the result page
                Intent intent = new Intent(this,ResultActivity.class);
                //delivering the parameters
                intent.putExtra("result", mTvResult.getText().toString());
                startActivity(intent);
            }

        });

    }

    private void doAction(){
        //checking if the input is
        if(TextUtils.isEmpty(mEtAmt.getText().toString())){
            showToast("The total loan amount is empty");
            return;
        }
        if(TextUtils.isEmpty(mEtRate.getText().toString())){
            showToast("Please Input Interest Rate!!");
            return;
        }
        if(TextUtils.isEmpty(mEtDate.getText().toString())){
            showToast("Please Input Loan Terms!");
            return;
        }

        //monthly payment method
        float amt = Float.parseFloat(mEtAmt.getText().toString());
        float rate = Float.parseFloat(mEtRate.getText().toString())/100;
        int time = Integer.parseInt(mEtDate.getText().toString());
        if(mRadioGroup.getCheckedRadioButtonId()==R.id.mRadioButton1){
            //average capital plus interest calculation method:
            // monthly payment= total loan amount ×[monthly rate ×（1+monthly interest rate）^loan term by month]÷[（1+monthly interest rate）^loan term-1]
            //principle should be paid monthly:
            //=loan principle × monthly interest rate×(1+ monthly interest rate)^(the number of monthly payment-1)÷〔(1+monthly interest rate)^loan terms by month-1〕
            //interest should be paid monthly:
            // =loan principle × monthly interest rate ×〔(1+monthly interest rate)^loan terms by month-(1+monthly interest rate)^(the number of monthly payment-1)〕÷〔(1+monthly interest rate)^loan terms-1〕

            double result = amt * (rate * Math.pow(1+rate,time))/(Math.pow(1+rate,time)-1);
            double result2 = (result*time-amt)/time;
            double result3 = result-result2;
            mTvResult.setText("result：\nAverage monthly repayment："+result+"\nMonthly interest："+result2+"\nMonthly principal："+result3);
        }else {
            //average principle repayment
            // : monthly payment =(loan principle ÷loan terms)+(loan principle -the cumulative amount of returned principle )×monthly interest rate
            double result = amt/time + amt*rate;
            mTvResult.setText("result：\nAverage monthly repayment："+result);
        }
    }

    //吐司提示
    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}