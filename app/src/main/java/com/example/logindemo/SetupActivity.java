package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName,FullName;
    private Button SaveInformation;
    private ImageView ProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        UserName= (EditText) findViewById(R.id.Setup_Username);
        FullName = (EditText) findViewById((R.id.Setup_FullName));
        SaveInformation = (Button) findViewById(R.id.btn_RegSave);
        ProfileImage= (ImageView) findViewById(R.id.Setup_Profile);



    }
}
