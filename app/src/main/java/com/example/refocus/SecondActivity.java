package com.example.refocus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.Calendar;
import java.util.Map;

import static java.sql.Types.NULL;

public class SecondActivity extends AppCompatActivity implements OnItemSelectedListener {

    String selectedValue = "";
    String hoursSelectedValue;
    String minsSelectedValue;

    App app = new App();

    private Button buttonname;

    Bundle bundle;

    int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1;

    String package_name;

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
            package_name = bundle.getString("pname");
            String cat = bundle.getString ("category");

            TextView textView = (TextView) findViewById(R.id.app_title);
            textView.setText(title);

            TextView textView2 = (TextView) findViewById(R.id.app_category2);
            textView2.setText(cat);

            String hours_string = bundle.getString("hours");
            TextView hoursTextView = (TextView) findViewById(R.id.hoursTextView);
            hoursTextView.setText(hours_string);

            String mins_string = bundle.getString("minutes");
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

                //app.setCategory(selectedValue);
                //app.setHours(hoursSelectedValue);
                //app.setMinutes(minsSelectedValue);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",app);
                returnIntent.putExtra("position2",pos);
                returnIntent.putExtra("hrs",hoursSelectedValue);
                returnIntent.putExtra("mins",minsSelectedValue);
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
                app.setPackageName(package_name);

                // testing stat usage manager here
                AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());

                if (mode == AppOpsManager.MODE_ALLOWED) {
                    UsageStatsManager usageStatsManager = (UsageStatsManager) view.getContext().getSystemService(Context.USAGE_STATS_SERVICE);

                    long end = System.currentTimeMillis();
                    long start = end - 1000*3600*3;
                    Map<String, UsageStats> stats = usageStatsManager.queryAndAggregateUsageStats(start, end);

                    String pname = app.getPackageName();

                    if (stats.containsKey(pname)) {
                        long totalTimeUsageInMillis = stats.get(pname).getTotalTimeInForeground();

                        long usageTimeMinutes = (totalTimeUsageInMillis / 1000) / 60;

                        String usage_string = Long.toString(usageTimeMinutes);

                        TextView usageTimeTextView = (TextView) findViewById(R.id.usage_time);
                        usageTimeTextView.setText(usage_string);
                    }
                }

                else {
                    startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
                }

                break;
            case R.id.hours_spinner:
                hoursSelectedValue = (String) parent.getItemAtPosition(pos);
                app.setHours(hoursSelectedValue);
                break;
            case R.id.minutes_spinner:
                minsSelectedValue = (String) parent.getItemAtPosition(pos);
                app.setMinutes(minsSelectedValue);
                break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}