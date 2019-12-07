package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private Button Search;
    private Button Pages;
    private Button Home;
    private Button Profile;
    private Button Logout;
    private TextView Name;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    String currentUserId;

       @Override
        protected void onCreate (Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);



            mAuth = FirebaseAuth.getInstance();
            currentUserId = mAuth.getCurrentUser().getUid();
           UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


           Name = (TextView) findViewById((R.id.Txt_Name));
            Search = (Button) findViewById(R.id.Btn_search);
            Pages = (Button) findViewById(R.id.btnpages);
            Home = (Button) findViewById(R.id.btnhome);
            Profile = (Button) findViewById(R.id.btnprofile);
            Logout = (Button) findViewById(R.id.btnlogout);


           UsersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists()){
                       if(dataSnapshot.hasChild("fullname")) {
                           String Fullname = dataSnapshot.child("fullname").getValue().toString();
                           Name.setText(Fullname);

                       }
                   }else{
                       Toast.makeText(HomeActivity.this, "Profile Name not found", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });



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

        public void openProfileActivity() {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        public void openMainActivity() {
            Intent intent = new Intent(this, MainActivity.class);
            mAuth.signOut();
            startActivity(intent);
        }

    }

