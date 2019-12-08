package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*public static final String ACTION_SEARCH {
<application ...>
<activity android:name=".SearchableActivity">
<intent-filter>
<action android:name="android.intent.action.SEARCH"/>
</intent-filter>
<meta-data android:name="android.app.searchable"
        android:resource="@xml/searchable"/>
</activity>
        ...
</application>
        };

public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        doMySearch(query);
        }

public class boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;*/
public class SearchActivity extends AppCompatActivity {

    private Button Pages;
    private Button Home;
    private Button Events;
    private Button Profile;
    private Button Logout;
//}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Pages = (Button)findViewById(R.id.Btn_Pages);
        Home = (Button)findViewById(R.id.Btn_home);
        Events = (Button)findViewById(R.id.Btn_Events);
        Profile = (Button)findViewById(R.id.Btn_Profile);
        Logout = (Button)findViewById(R.id.Btn_logout);

        Pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage1Activity();
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
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
    }

    public void openPage1Activity() {
        Intent intent = new Intent(this, Page1Activity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openEventsActivity() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }

    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

//}
