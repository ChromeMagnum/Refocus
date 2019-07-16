package com.example.refocus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    public static final String ERROR = "error";

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private final String TAG = MainActivity.class.getSimpleName();
    private PackageManager pm;
    //private ActivityUtil mActivityUtil;

    ArrayList<App> apps = new ArrayList<App>();

    App app2 = new App();

    ListView listView;

    int pos;

    String hours_string2, mins_string2;

    int FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Iterator<ApplicationInfo> iterator = packages.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            ApplicationInfo packageInfo = iterator.next();
            String app_name = (String) pm.getApplicationLabel(packageInfo);
            String query_url = GOOGLE_URL + packageInfo.packageName;
            Drawable icon = pm.getApplicationIcon(packageInfo);
            Log.i(TAG, query_url);
            String category = "none";
            Log.e("CATEGORY", category);
            App app = new App();
            app.setName(app_name);
            app.setCategory(category);
            app.setImage(icon);
            app.setHours("0");
            app.setMinutes("0");
            apps.add(i, app);
        }

        listView = (ListView) findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter(this, apps);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), SecondActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                app2 = apps.get(position);
                String title = app2.getName();
                String category = app2.getCategory();
                String hours = app2.getHours();
                String mins = app2.getMinutes();
                myIntent.putExtra("title", title);
                myIntent.putExtra("category", category);
                myIntent.putExtra("position", position);
                myIntent.putExtra("hours", hours);
                myIntent.putExtra("minutes", mins);
                myIntent.putExtra("app", app2);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {

                pos = data.getIntExtra("position2", 0);

                hours_string2 = data.getExtras().getString("hrs");

                mins_string2 = data.getExtras().getString("mins");

                app2 = (App) data.getExtras().getParcelable("result");

                app2.setHours(hours_string2);
                app2.setMinutes(mins_string2);

                listView = (ListView) findViewById(R.id.list);

                apps.set(pos, app2);

                MyAdapter adapter2 = new MyAdapter(this, apps);
                listView.setAdapter(adapter2);

                listView.smoothScrollToPosition(pos);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(view.getContext(), SecondActivity.class);
                        app2 = apps.get(position);
                        String title = app2.getName();
                        String category = app2.getCategory();
                        String hours = app2.getHours();
                        String mins = app2.getMinutes();
                        myIntent.putExtra("title", title);
                        myIntent.putExtra("category", category);
                        myIntent.putExtra("position", position);
                        myIntent.putExtra("hours", hours);
                        myIntent.putExtra("minutes", mins);
                        myIntent.putExtra("app", app2);
                        startActivityForResult(myIntent, 0);
                    }
                });

                /*String name = app2.getName();
                String cat = app2.getCategory();
                TextView displayTextView = (TextView)findViewById(R.id.subtitle);
                displayTextView.setText(cat);*/
            }
        }
    }
}
