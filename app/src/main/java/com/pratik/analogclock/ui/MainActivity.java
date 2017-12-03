package com.pratik.analogclock.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pratik.analogclock.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MyFragment())
                    .commit();
        }
    }
}
