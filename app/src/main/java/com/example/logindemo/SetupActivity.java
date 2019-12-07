package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName,FullName;
    private Button SaveInformation;
    private ImageView ProfileImage;
    private ProgressDialog loadingbar;



    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private StorageReference UserProfilePicRef;

    String currentUserID;
    final static int Gallery_Pick =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        UserProfilePicRef = FirebaseStorage.getInstance().getReference().child("profile Images");


        UserName= (EditText) findViewById(R.id.Setup_Username);
        FullName = (EditText) findViewById((R.id.Setup_FullName));
        SaveInformation = (Button) findViewById(R.id.btn_RegSave);
        ProfileImage= (ImageView) findViewById(R.id.Setup_Profile);
        loadingbar = new ProgressDialog(this );




        SaveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSetupInfo();
            }
        });

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("Image/*");
                startActivityForResult(galleryIntent,Gallery_Pick);
            }
        });

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String image = dataSnapshot.child("profileimage").getValue().toString();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            Uri ImageUri = data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1 ).start(this);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK) {
                loadingbar.setTitle("updating");
                loadingbar.setMessage("Please Wait While updating your image");
                loadingbar.show();
                loadingbar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();
                StorageReference filepath = UserProfilePicRef.child(currentUserID + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SetupActivity.this, "Profile Picture stored successfully to firebase", Toast.LENGTH_SHORT).show();
                            final String downloadUrl = task.getResult().getUploadSessionUri().toString();

                            UsersRef.child("profileImage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        loadingbar.dismiss();
                                        Intent setupintent = new Intent(SetupActivity.this, SetupActivity.class);
                                        startActivity(setupintent);
                                        Toast.makeText(SetupActivity.this, "Profile Picture stored successfully to firebase database", Toast.LENGTH_SHORT).show();
                                    }else{
                                        String message = task.getException().getMessage();
                                        loadingbar.dismiss();
                                        Toast.makeText(SetupActivity.this, "Error Occured" +  message, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    }
                });

            }else{
                loadingbar.dismiss();
                Toast.makeText(SetupActivity.this, "Image Can not be cropped ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveAccountSetupInfo() {
            String username = UserName.getText().toString();
            String fullname = FullName.getText().toString();

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Please write your username...", Toast.LENGTH_SHORT);
            } else if (TextUtils.isEmpty(fullname)) {
                Toast.makeText(this, "Please write your fullname...", Toast.LENGTH_SHORT);
            } else {

                loadingbar.setTitle("Saving Infromation");
                loadingbar.setMessage("Please Wait While Registering you");
                loadingbar.show();
                loadingbar.setCanceledOnTouchOutside(true);


                HashMap userMap = new HashMap();
                userMap.put("username", username);
                userMap.put("fullname", fullname);
                userMap.put("status", "Hey there");
                userMap.put("gender", "none");
                UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            SendUsertoHome();
                            Toast.makeText(SetupActivity.this, "Your account is Created Successfully", Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(SetupActivity.this, "Error Occured" + message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
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
