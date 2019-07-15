package com.example.refocus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements OnItemSelectedListener {

    String selectedValue = "";

    App app = new App();

    private Button buttonname;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonname = (Button) findViewById(R.id.submit_button);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (App) getIntent().getExtras().getParcelable("app");

        bundle = getIntent().getExtras();

        final int pos = bundle.getInt("position");

        if ((bundle.getString("title")!= null) && (bundle.getString("category")!= null))
        {
            String title = bundle.getString ("title");
            String cat = bundle.getString ("category");

            TextView textView = (TextView) findViewById(R.id.app_title);
            textView.setText(title);

            TextView textView2 = (TextView) findViewById(R.id.app_category2);
            textView2.setText(cat);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //selectedValue = (String) parent.getItemAtPosition(pos);

                //String selectedValue = "changed";

                app.setCategory(selectedValue);

                //TextView displayTextView = (TextView)findViewById(R.id.app_category);
                //displayTextView.setText(selectedValue);


                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",app);
                returnIntent.putExtra("position2",pos);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        selectedValue = (String) parent.getItemAtPosition(pos);

        app.setCategory(selectedValue);

        //TextView displayTextView = (TextView)findViewById(R.id.app_category);

        //displayTextView.setText(selectedValue);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}