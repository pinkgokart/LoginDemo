package com.example.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


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
    private FirebaseRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;


    private RecyclerView postlist;

    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Postref = FirebaseDatabase.getInstance().getReference().child("Posts");


        Name = (TextView) findViewById((R.id.Txt_Name));
        Search = (Button) findViewById(R.id.Btn_search);
        Pages = (Button) findViewById(R.id.btnpages);
        Events = (Button) findViewById(R.id.btnevents);
        Profile = (Button) findViewById(R.id.btnprofile);
        Logout = (Button) findViewById(R.id.btnlogout);
     //   AddnewPostButton = (ImageButton) findViewById(R.id.Btn_Post);

       postlist = (RecyclerView) findViewById(R.id.all_userpostlist);
      linearLayoutManager = new LinearLayoutManager(this);
        postlist.setLayoutManager(linearLayoutManager);
        postlist.setHasFixedSize(true);
        fetch();







/*
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

        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEventsActivity();
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
*/


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

/*        AddnewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToPost();
            }
        });
*/

    }


    private void SendUserToPost() {
        Intent postintent = new Intent(HomeActivity.this, postActivity.class);
        startActivity(postintent);

    }

    public void openSearchActivity() {
        Intent Searchintent = new Intent(HomeActivity.this, SearchActivity.class);
        startActivity(Searchintent);
    }

    public void openPage1Activity() {
        Intent Pageintent = new Intent(HomeActivity.this, Page1Activity.class);
        startActivity(Pageintent);
    }

    public void openEventsActivity() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void openProfileActivity() {
        Intent Profileintent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(Profileintent);
    }

    public void openMainActivity() {
        Intent Logoutintent = new Intent(HomeActivity.this, MainActivity.class);
        // mAuth.signOut();
        startActivity(Logoutintent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtName;
        public TextView txtDate;
        public TextView txtTime;
        public TextView txtPostDec;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById((R.id.post_user_name));
            txtDate = itemView.findViewById(R.id.post_date);
            txtTime = itemView.findViewById(R.id.post_time);
            txtPostDec = itemView.findViewById(R.id.post_description);
            //  txtTime = itemView.findViewById(R.id.post_profile_image);
        }

        public void settxtName(String string) {
            txtName.setText(string);
        }


        public void settxtDate(String string) {
            txtDate.setText(string);
        }

        public void settxtTime(String string) {
            txtTime.setText(string);
        }

        public void settxtPostDec(String string) {
            txtPostDec.setText(string);
        }
    }

        private void fetch() {
            Query query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Posts");

            FirebaseRecyclerOptions<posts> options =
                    new FirebaseRecyclerOptions.Builder<posts>()
                            .setQuery(query, new SnapshotParser<posts>() {
                                @NonNull
                                @Override
                                public posts parseSnapshot(@NonNull DataSnapshot snapshot) {
                                    return new posts(snapshot.child("date").getValue().toString(), snapshot.child("postdescrip").getValue().toString(), snapshot.child("time").getValue().toString()
                                            , snapshot.child("fullname").getValue().toString(), snapshot.child("uid").getValue().toString());
                                }
                            })
                            .build();

            adapter = new FirebaseRecyclerAdapter<posts, ViewHolder>(options) {
                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.all_post_layout, parent, false);

                    return new ViewHolder(view);
                }


                @Override
                protected void onBindViewHolder(ViewHolder holder, final int position, posts model) {
                    holder.settxtDate(model.getDate());
                    holder.settxtTime(model.getTime());
                    holder.settxtName(model.getFullname());
                    holder.settxtPostDec(model.getPostdescrip());


                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(HomeActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            };
        }
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

