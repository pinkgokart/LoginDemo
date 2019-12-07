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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity {

        private EditText Useremail,UserPass,UsercomPass;
        private Button CreateAccount;
        private FirebaseAuth mauth;
        private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mauth = FirebaseAuth.getInstance();

        Useremail = (EditText) findViewById(R.id.reg_email);
        UserPass = (EditText) findViewById(R.id.reg_pwd);
        UsercomPass = (EditText) findViewById(R.id.reg_pwd_com);
        CreateAccount = (Button) findViewById(R.id.btnReg);
        loadingbar = new ProgressDialog(this );


        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });

    }

    private void CreateNewAccount() {
        String email = Useremail.getText().toString();
        String passsword =  UserPass.getText().toString();
        String ComPassword = UsercomPass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(  this, "please write your email....", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(passsword)) {
            Toast.makeText(this, "please write your Password....", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(ComPassword)) {
            Toast.makeText(this, "please comfirm your password....", Toast.LENGTH_SHORT).show();
        }
        else if(!passsword.equals(ComPassword)){
            Toast.makeText(this, "Your passwords do not match....", Toast.LENGTH_SHORT).show();

        }else{
            loadingbar.setTitle("Creating New Account");
            loadingbar.setMessage("Creating Account Right now");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);

            mauth.createUserWithEmailAndPassword(email,passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegActivity.this  , "You are Authenticated Successfully", Toast.LENGTH_SHORT).show();
                        SendUsertoSetup();
                        loadingbar.dismiss();

                    }else{
                        String message= task.getException().getMessage();
                        Toast.makeText(RegActivity.this  , "Error occured" + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();

                    }
                }
            });
        }




        }

    private void SendUsertoSetup() {
        Intent setIntent = new Intent(this, SetupActivity.class);
        startActivity(setIntent);
    }
}
