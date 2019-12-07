package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Page2Activity extends AppCompatActivity {

    private Button Search;
    private Button Pages;
    private Button Home;
    private Button Events;
    private Button Profile;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        Search = (Button)findViewById(R.id.btnsearch);
        Pages = (Button)findViewById(R.id.btnpages);
        Home = (Button)findViewById(R.id.btnhome);
        Events = (Button)findViewById(R.id.btnevents);
        Profile = (Button)findViewById(R.id.btnprofile);
        Logout = (Button)findViewById(R.id.btnlogout);

        Search.setOnClickListener(new View.OnClickListener() {
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

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
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

    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
