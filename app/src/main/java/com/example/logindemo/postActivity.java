package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class postActivity extends AppCompatActivity {
    private Button Create;
    private Button Cancel;
   /*private ImageButton SelectPostImage;*/
    private EditText PostDescription;
    private EditText PostTitle;
    private String description;
    private String saveCurrentDate,saveCurrentTime,postRandomName, current_user_id;

    private ProgressDialog loadingbar;

    private DatabaseReference Userref, PostRef;
    private FirebaseAuth mAuth;



    private static int Gallery_Pick;
  /*  private Uri imageUri;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Userref = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef =  FirebaseDatabase.getInstance().getReference().child("Posts");

        mAuth= FirebaseAuth.getInstance();
        current_user_id= mAuth.getCurrentUser().getUid().toString();

        Create = (Button) findViewById(R.id.btn_Create);
        Cancel = (Button) findViewById(R.id.Btn_Cancel);
       /* SelectPostImage = (ImageButton) findViewById(R.id.Btn_Postimage);*/
        PostDescription = (EditText) findViewById(R.id.Text_post);
        loadingbar= new ProgressDialog(this);


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
            loadingbar.setTitle("Add New Post");
            loadingbar.setMessage("Please wait while we are creating your post");
            loadingbar.show();
            StoringPostToFirebaseStorage();
        }
    }

    private void StoringPostToFirebaseStorage() {
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

                    HashMap postMap = new HashMap();

                    postMap.put("uid",current_user_id);
                    postMap.put("Date",saveCurrentDate);
                    postMap.put("Time",saveCurrentTime);
                  //  postMap.put("PostTitle",PostTitle);
                    postMap.put("PostDescrip",description) ;
                  //  postMap.put("profileImage",UserProfileImg);
                    postMap.put("fullname",userFullName);

                    PostRef.child(current_user_id+postRandomName).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                loadingbar.dismiss();
                                Toast.makeText(postActivity.this, "New post is Posted", Toast.LENGTH_SHORT).show();
                                SendUsertoHome();
                            }else{
                                Toast.makeText(postActivity.this, "Error Occured when updating your post", Toast.LENGTH_SHORT).show();
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


    private void OpenGalley() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }
private void SendUsertoHome(){
        Intent homeIntent = new Intent();
        startActivity(homeIntent);


}
   /* protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            SelectPostImage.setImageURI(imageUri);

        }

    }*/


}
