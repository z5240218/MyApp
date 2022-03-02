package com.example.myapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView mTvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mTvResult = findViewById(R.id.mTvResult);
        //receiving the data
        mTvResult.setText(getIntent().getStringExtra("result"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(getIntent().getStringExtra("result"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();

    }
}
