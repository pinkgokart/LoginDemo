package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
<application ... >
<activity android:name=".SearchableActivity" >
<intent-filter>
<action android:name="android.intent.action.SEARCH" />
</intent-filter>
<meta-data android:name="android.app.searchable"
        android:resource="@xml/searchable"/>
</activity>
        ...
</application>
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

}
