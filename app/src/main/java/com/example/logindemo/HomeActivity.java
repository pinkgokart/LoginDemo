package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class HomeActivity extends AppCompatActivity {

    private Button Search;
    private Button Pages;
    private Button Events;
    private Button Profile;
    private Button Logout;
    private ImageButton AddnewPostButton;
    private TextView Name;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private DatabaseReference Postref;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<posts, ViewHolder> adapter;


    private RecyclerView postlist;

    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        Name = (TextView) findViewById((R.id.Txt_Name));
        Search = (Button) findViewById(R.id.Btn_search);
        Pages = (Button) findViewById(R.id.btnpages);
        Events = (Button) findViewById(R.id.btnevents);
        Profile = (Button) findViewById(R.id.btnprofile);
        Logout = (Button) findViewById(R.id.btnlogout);
      //  AddnewPostButton = (ImageButton) findViewById(R.id.Btn_Post);

        postlist = (RecyclerView) findViewById(R.id.all_userpostlist);
        postlist.setLayoutManager(new LinearLayoutManager(this));

        AddnewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToPost();
            }
        });

        Postref = FirebaseDatabase.getInstance().getReference().child("Posts");
        Query query = Postref.child("Posts");
        final FirebaseRecyclerOptions<posts> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<posts>()
                .setQuery(query, posts.class).build();

        adapter = new FirebaseRecyclerAdapter<posts, ViewHolder>((firebaseRecyclerOptions)) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull posts posts) {

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_post_layout, parent, false);

                return new ViewHolder(view);
            }

        };
        postlist.setAdapter(adapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDate;
        private TextView txtTime;
        private TextView txtPostDec;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById((R.id.post_user_name));
            txtDate = itemView.findViewById(R.id.post_date);
            txtTime = itemView.findViewById(R.id.post_time);
            txtPostDec = itemView.findViewById(R.id.post_description);
            //  txtTime = itemView.findViewById(R.id.post_profile_image);
        }

        void setTimePost(posts post) {
            String date = post.getDate();
            txtDate.setText((date));
            String postdescrip = post.getPostdescrip();
            txtPostDec.setText((postdescrip));
            String time = post.getTime();
            txtTime.setText((time));
            String fullname = post.getFullname();
            txtName.setText((fullname));


        }
    }


/*
        UsersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("fullname")) {
                        String Fullname = dataSnapshot.child("fullname").getValue().toString();
                        Name.setText(Fullname);

                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Profile Name not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/







    private void SendUserToPost() {
        Intent postintent = new Intent(HomeActivity.this, postActivity.class);
        startActivity(postintent);

    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}

