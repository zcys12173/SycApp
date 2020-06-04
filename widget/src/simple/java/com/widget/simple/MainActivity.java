package com.mvvm.simple;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.syc.widget.R;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
