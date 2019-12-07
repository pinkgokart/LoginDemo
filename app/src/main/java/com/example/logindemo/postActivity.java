package com.example.logindemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserModel;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class postActivity extends AppCompatActivity {
    private Button Create;
    private Button Cancel;
    private ImageButton SelectPostImage;
    private EditText PostDescription;

    private static int Gallery_Pick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Create = (Button) findViewById(R.id.btn_Create);
        Cancel = (Button) findViewById(R.id.Btn_Cancel);
        SelectPostImage = (ImageButton) findViewById(R.id.Btn_Postimage);
        PostDescription = (EditText) findViewById(R.id.Text_post);

        SelectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGalley();
            }
        });

    }


    private void OpenGalley() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data!=null){
            ImageUri
        }

    }


}
