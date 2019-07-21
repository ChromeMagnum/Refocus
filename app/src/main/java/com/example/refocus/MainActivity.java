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

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private final String TAG = MainActivity.class.getSimpleName();
    private PackageManager pm;

    ArrayList<App> apps = new ArrayList<App>();

    App app2 = new App();

    ListView listView;

    int pos;

    String hours_string2, mins_string2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Iterator<ApplicationInfo> iterator = packages.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            ApplicationInfo packageInfo = iterator.next();

            String test_name = (String) pm.getApplicationLabel(packageInfo);;

            ArrayList<String> app_array = new ArrayList<String>();
            app_array.add("Refocus");
            app_array.add("Chrome");
            app_array.add("Phone");
            //app_array.add("Smart Fitness");
            app_array.add("Camera");
            app_array.add("Messages");
            app_array.add("Google");
            app_array.add("YouTube");

            if (app_array.contains(test_name)) {
                String app_name = (String) pm.getApplicationLabel(packageInfo);
                String package_name = (String) packageInfo.packageName;
                Drawable icon = pm.getApplicationIcon(packageInfo);
                String category = "none";
                App app = new App();
                app.setName(app_name);
                app.setPackageName(package_name);
                app.setCategory(category);
                app.setImage(icon);
                app.setHours("0");
                app.setMinutes("0");
                apps.add(i, app);
            }

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
                String pname = app2.getPackageName();
                myIntent.putExtra("title", title);
                myIntent.putExtra("pname", pname);
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

            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
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
            }
        }
    }
}
