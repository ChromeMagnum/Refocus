package com.example.refocus;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.jar.Pack200.Packer.ERROR;

public class MainActivity extends AppCompatActivity {

    //public static final Integer[] images = { R.drawable.one, R.drawable.two, R.drawable.three };

    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    public static final String ERROR = "error";

    private final String TAG = MainActivity.class.getSimpleName();
    private PackageManager pm;

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
        ArrayList apps = new ArrayList();
        int imageId = 1;
        int i = 0;
        while (iterator.hasNext()) {
            ApplicationInfo packageInfo = iterator.next();
            String app_name = (String) pm.getApplicationLabel(packageInfo);
            String query_url = GOOGLE_URL + packageInfo.packageName;
            Log.i(TAG, query_url);
            String category = "games";
            /*try {
                category = getAppCategory(query_url);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            App app = new App(packageInfo, app_name, category, imageId);
            apps.add(i, app);
        }


        ListView listView = (ListView) findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter(this, apps);
        listView.setAdapter(adapter);

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

   /* private String getAppCategory(String query_url) throws IOException {

    }*/
}
