package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Page1Activity extends AppCompatActivity {

    private Button Follow;
    private Button Following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        Follow = (Button)findViewById(R.id.btnFollow_0);
        Following = (Button)findViewById(R.id.btnFollowing_0);

        Follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Follow.setText("following");
                 Follow.setBackgroundColor(000000);

            }
        });

        Following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ();
            }
        });
    }
}
