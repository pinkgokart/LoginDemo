package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView SwipeLeft;
    private TextView DDLeft;
    private Button Search;
    private Button Pages;
    private Button Home;
    private Button Events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SwipeLeft =(TextView)findViewById(R.id.tvswipesleft);
        DDLeft =(TextView)findViewById(R.id.tvdiningdollarsleft);
        Search =(Button)findViewById(R.id.btnsearch);
        Pages =(Button)findViewById(R.id.btnpages);
        Home =(Button)findViewById(R.id.btnhome);
        Events =(Button)findViewById(R.id.btnevents);

        Search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });

        Pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage1Activity();
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventsActivity();
            }
        });

        SwipeLeft.setText("Meal Swipes Left: " + 5);
        DDLeft.setText("Dining Dollars Left: $" + 100);
    }

    public void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openPage1Activity() {
        Intent intent = new Intent(this, Page1Activity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openEventsActivity() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }
}
