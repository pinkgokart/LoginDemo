package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName,FullName;
    private Button SaveInformation;
    private ImageView ProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


        UserName= (EditText) findViewById(R.id.Setup_Username);
        FullName = (EditText) findViewById((R.id.Setup_FullName));
        SaveInformation = (Button) findViewById(R.id.btn_RegSave);
        ProfileImage= (ImageView) findViewById(R.id.Setup_Profile);



        SaveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSetupInfo();
            }
        });

    }

    private void SaveAccountSetupInfo() {
        String username = UserName.getText().toString();
        String fullname = FullName.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please write your username...", Toast.LENGTH_SHORT);
        }else if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this,"Please write your fullname...", Toast.LENGTH_SHORT);
        }else{
            HashMap userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("fullname", fullname);
            userMap.put("status", "Hey there" );
            userMap.put("gender", "none");
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        SendUsertoHome();
                        Toast.makeText(SetupActivity.this, "Your account is Create Successfully", Toast.LENGTH_LONG).show();
                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(SetupActivity.this,  "Error Occured" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    private void SendUsertoHome() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }
}
