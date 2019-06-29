package com.example.refocus;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if ((bundle.getString("title")!= null) && (bundle.getString("category")!= null))
        {
            String title = bundle.getString ("title");
            String cat = bundle.getString ("category");

            TextView textView = (TextView) findViewById(R.id.app_title);
            textView.setText(title);

            TextView textView2 = (TextView) findViewById(R.id.app_category);
            textView2.setText(cat);
        }
    }

}
