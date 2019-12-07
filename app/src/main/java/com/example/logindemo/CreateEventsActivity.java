package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateEventsActivity extends AppCompatActivity {

    private Button Cancel;
    private Button Create;
    private Button Logout;
    private EditText EventName;
    private EditText Location;
    private EditText StartTime;
    private EditText EndTime;
    private EditText Date;
    private EditText Description;
    private EditText Sponsor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        Cancel = (Button)findViewById(R.id.btncancel);
        Create = (Button)findViewById(R.id.btncreate);
        Logout = (Button)findViewById(R.id.btnlogout);
        EventName = (EditText)findViewById(R.id.etnameevent);
        Location = (EditText)findViewById(R.id.etlocation);
        StartTime = (EditText)findViewById(R.id.etstarttime);
        EndTime = (EditText)findViewById(R.id.etendtime);
        Date = (EditText)findViewById(R.id.etdate);
        Description = (EditText)findViewById(R.id.etdescription);
        Sponsor = (EditText)findViewById(R.id.etsponsor);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventsActivity();
            }
        });

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewEvent();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    public void openEventsActivity() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void CreateNewEvent() {
        String eventname = EventName.getText().toString();
        String location = Location.getText().toString();
        String starttime = StartTime.getText().toString();
        String endtime = EndTime.getText().toString();
        String date = Date.getText().toString();
        String description = Description.getText().toString();
        String sponsor = Sponsor.getText().toString();

        if(TextUtils.isEmpty(eventname)){
            Toast.makeText(  this, "Please enter name of event.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please enter location of event.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(starttime)) {
            Toast.makeText(this, "Please enter a start time of event.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(endtime)) {
            Toast.makeText(this, "Please enter an end time of event.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please enter date fo event.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please enter a description of event.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sponsor)) {
            Toast.makeText(this, "Please enter sponsor of event.", Toast.LENGTH_SHORT).show();
        }

    }
}
