package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView NewUserAccount;
    private Button Login;
    private ProgressDialog loadingbar;
   // private int counter=5;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){

        }else{
            CheckUserExistence();
        }
    }

    private void CheckUserExistence() {

        final String CurrentUser_ID = mAuth.getCurrentUser().getUid();
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(CurrentUser_ID)){
                    SendUserToSetup();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SendUserToSetup() {
        Intent setIntent = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(setIntent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");

        NewUserAccount = (TextView) findViewById(R.id.Reg_Acc_link);


        Name =(EditText)findViewById(R.id.login_email);
        Password =(EditText)findViewById(R.id.login_pwd);
        Login =(Button)findViewById(R.id.btnLogin);
        loadingbar = new ProgressDialog(this);


        NewUserAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SendUserToRegActivity();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void SendUserToRegActivity() {
        Intent regIntent = new Intent(MainActivity.this, RegActivity.class);
        startActivity(regIntent);
    }


    private void validate(String userName, String userPassword){

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(  this, "please write your email....", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(  this, "please write your Password....", Toast.LENGTH_SHORT).show();
        }else{
            loadingbar.setTitle("Login");
            loadingbar.setMessage("Please wait, While we are Logging you in");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);


            mAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        SendUserToHomeActivity();
                        Toast.makeText(MainActivity.this  , "You are Logged In Successfully", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();

                    }else{
                        String message= task.getException().getMessage();
                        Toast.makeText(MainActivity.this  , "Error occured" + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();

                    }
                }
            });
        }

        /*if((userName.equals("admin@ferrum.edu"))&&(userPassword.equals("1234"))){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }else{
            Info.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter==0){
                Login.setEnabled(false);
            }
        }*/

    }


    private void SendUserToHomeActivity() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
         homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
      //  finish();
    }
}
