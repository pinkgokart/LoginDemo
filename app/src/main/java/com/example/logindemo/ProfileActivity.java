package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView SwipeLeft;
    private TextView DDLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SwipeLeft =(TextView)findViewById(R.id.tvswipesleft);
        DDLeft =(TextView)findViewById(R.id.tvdiningdollarsleft);

        SwipeLeft.setText("Meal Swipes Left: " + 5);
        DDLeft.setText("Dining Dollars Left: $" + 100);
    }
}
