package com.example.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
    private String eventname,location,starttime,endtime,date,description,sponsor;

    private EditText Sponsor;
    private String saveCurrentTime, saveCurrentDate, postRandomName, current_user_id;
    private DatabaseReference Userref, Eventref;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        Userref = FirebaseDatabase.getInstance().getReference().child("Users");
        Eventref = FirebaseDatabase.getInstance().getReference().child("Events");

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid().toString();

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
        loadingbar = new ProgressDialog(this);

        /*Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventsActivity();
            }
        });*/


        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewEvent();
            }
        });
/*
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });*/

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
         eventname = EventName.getText().toString();
         location = Location.getText().toString();
         starttime = StartTime.getText().toString();
         endtime = EndTime.getText().toString();
         date = Date.getText().toString();
         description = Description.getText().toString();
         sponsor = Sponsor.getText().toString();

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
        }else
            StoringEventtoFirebaseDatabase();

    }

    private void StoringEventtoFirebaseDatabase() {
        Calendar calFordDate = Calendar.getInstance();
        final SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        Userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userFullName = dataSnapshot.child("fullname").getValue().toString();
                    // String UserProfileImg = dataSnapshot.child("profileimage").getValue().toString();

                    HashMap eventMap = new HashMap();

                    eventMap.put("uid",current_user_id);
                    eventMap.put("Event Name", eventname);
                    eventMap.put("Location", location);
                    eventMap.put("Start Time", starttime);
                    eventMap.put("End Time", endtime);
                    eventMap.put("Date", date);
                    eventMap.put("Description", description);
                    eventMap.put("Sponsor", sponsor);

                    Eventref.child(current_user_id+postRandomName).updateChildren(eventMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        loadingbar.dismiss();
                                        Toast.makeText(CreateEventsActivity.this, "Event has been created.", Toast.LENGTH_SHORT).show();
                                        SendUsertoEvents();
                                    }else{
                                        Toast.makeText(CreateEventsActivity.this, "Error occurred when updating your post", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                }
                            });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendUsertoEvents() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }
}
