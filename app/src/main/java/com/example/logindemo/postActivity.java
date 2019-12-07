package com.example.logindemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class postActivity extends AppCompatActivity {
    private Button Create;
    private Button Cancel;
   /*private ImageButton SelectPostImage;*/
    private EditText PostDescription;
    private String description;
    private String saveCurrentDate,saveCurrentTime,postRandomName, current_user_id;

    private StorageReference Postref;
    private DatabaseReference Userref;
    private FirebaseAuth mAuth;



    private static int Gallery_Pick;
  /*  private Uri imageUri;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Postref = FirebaseStorage.getInstance().getReference();
        Userref = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth= FirebaseAuth.getInstance();
        current_user_id= mAuth.getCurrentUser().getUid();

        Create = (Button) findViewById(R.id.btn_Create);
        Cancel = (Button) findViewById(R.id.Btn_Cancel);
       /* SelectPostImage = (ImageButton) findViewById(R.id.Btn_Postimage);*/
        PostDescription = (EditText) findViewById(R.id.Text_post);

        /*SelectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGalley();
            }
        });
        */
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostInfo();
            }
        });

    }

    private void ValidatePostInfo() {

         description= PostDescription.getText().toString();
       /* if(imageUri == null){
            Toast.makeText(this, "Please Select Post Image", Toast.LENGTH_SHORT).show();
        }else */

        if(TextUtils.isEmpty(description)){

            Toast.makeText(this, "Please Add something", Toast.LENGTH_SHORT).show();
        }else{
            StoringPostToFirebaseStorage();
        }
    }

    private void StoringPostToFirebaseStorage() {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filepath;



    }


    private void OpenGalley() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

   /* protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            SelectPostImage.setImageURI(imageUri);

        }

    }*/


}
