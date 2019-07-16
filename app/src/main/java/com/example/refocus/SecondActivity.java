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
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements OnItemSelectedListener {

    String selectedValue = "";
    String hoursSelectedValue;
    int hours;
    String minsSelectedValue;
    int mins;

    App app = new App();

    private Button buttonname;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonname = (Button) findViewById(R.id.submit_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

            //int hours2 = app.getHours();
            int hours2 = bundle.getInt("hours");
            String hours_string = Integer.toString(hours2);
            TextView hoursTextView = (TextView) findViewById(R.id.hoursTextView);
            hoursTextView.setText(hours_string);

            //int mins2 = app.getMinutes();
            int mins2 = bundle.getInt("minutes");
            String mins_string = Integer.toString(mins2);
            TextView minsTextView = (TextView) findViewById(R.id.minsTextView);
            minsTextView.setText(mins_string);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        Spinner hours_spinner = (Spinner) findViewById(R.id.hours_spinner);
        Spinner mins_spinner = (Spinner) findViewById(R.id.minutes_spinner);

        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        hours_spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.hours_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        hours_spinner.setAdapter(adapter2);

        mins_spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.minutes_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mins_spinner.setAdapter(adapter3);

        buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                app.setCategory(selectedValue);
                app.setHours(hours);
                app.setMinutes(mins);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",app);
                returnIntent.putExtra("position2",pos);
                returnIntent.putExtra("hours",hours);
                returnIntent.putExtra("mins",mins);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        int id2 = parent.getId();

        switch (id2) {
            case R.id.spinner1:
                selectedValue = (String) parent.getItemAtPosition(pos);
                app.setCategory(selectedValue);
                break;
            case R.id.hours_spinner:
                hoursSelectedValue = (String) parent.getItemAtPosition(pos);
                hours = Integer.parseInt(hoursSelectedValue);
                app.setHours(hours);
                break;
            case R.id.minutes_spinner:
                minsSelectedValue = (String) parent.getItemAtPosition(pos);
                mins = Integer.parseInt(minsSelectedValue);
                app.setMinutes(mins);
                break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}