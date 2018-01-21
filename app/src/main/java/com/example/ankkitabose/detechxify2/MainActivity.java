package com.example.ankkitabose.detechxify2;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

//import static android.R.attr.entries;


public class MainActivity extends ActionBarActivity {

    private GoogleApiClient client;
    private Button pledgeButton;
    private  Button progress;
    ImageView done, active ;
    TextView result; //displays nothing when the DB is empty, timer when pledge
    DatabaseHandler db;
    Map<String, Integer> pkgMap;
    Map<Integer, Integer> durMap;
    Map<Integer, Long> timeMap;

    UsageStatsManager mUsageStatsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabaseHandler(this);
        //boolean flag=true;

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

        pledgeButton = (Button) findViewById(R.id.btnPledge);
        result = (TextView) findViewById(R.id.result);
        progress = (Button) findViewById(R.id.progress);
        done =(ImageView) findViewById(R.id.imageView3);
        active =(ImageView) findViewById(R.id.imageView4);



        //done.setVisibility(View.INVISIBLE);
        //result.setVisibility(View.INVISIBLE);
        //set that other msg also as invisible
        if (db.size()==0) {
            result.setText("NO PLEDGES YET");
        }
      //  else if () //timer is one
        else {

            pledgeButton.setText("Progress!");
        }



        /* Finding the facebook share button
        ShareButton shareButton = (ShareButton)findViewById(R.id.imageButton2);
        // Sharing the content to facebook
        ShareLinkContent content = new ShareLinkContent.Builder()
                // Setting the title that will be shared
                .setContentTitle("Pledged to use my phone less")
                // Setting the image that will be shared
                .setImageUrl(Uri.parse("http://www.livetothebeat.com/images/pledge.png"))
                .build();
        shareButton.setShareContent(content);*/

        pledgeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                if(!((pledgeButton.getText().toString())=="Progress!")) {
                    Intent intent = new Intent(v.getContext(), AppUsageStatisticsActivity.class);
                    startActivity(intent);
                }
                else {
                    pledgeButton.setText("TAKE A PLEDGE");
                    boolean flag = true;
                    Cursor cur = db.getAllApps();
                    cur.moveToFirst();
                    if(cur.getCount()>0) {
                        pkgMap = new HashMap<>();
                        durMap = new HashMap<>();
                        timeMap = new HashMap<>();
                    }
                    int i=0;
                    while(!cur.isAfterLast()) {
                        pkgMap.put(cur.getString(0),i);
                        durMap.put(i,cur.getInt(1));
                        timeMap.put(i,cur.getLong(2));
                    }
                    long totTime=0;
                    Calendar cal = Calendar.getInstance();
                    List<UsageStats> queryUsageStats;
                    mUsageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
                    if((timeMap.get(0)+(durMap.get(0)*60*60*1000))<System.currentTimeMillis()) {
                        //cal.add(Calendar.HOUR_OF_DAY, -durMap.get(0));

                        queryUsageStats = mUsageStatsManager
                                .queryUsageStats(0, timeMap.get(0),
                                        ((timeMap.get(0) + (durMap.get(0) * 60 * 60 * 1000))));


                    }

                    else {
                        flag=false;
                        queryUsageStats = mUsageStatsManager
                                .queryUsageStats(0, timeMap.get(0),
                                        System.currentTimeMillis());
                    }

                    for(UsageStats us: queryUsageStats) {
                        if(us.getPackageName().equals(pkgMap.get(0))) {
                            totTime+=us.getTotalTimeInForeground();
                        }
                    }
                    if(flag==true) {
                        result.setText("Total Usage : "+totTime/(1000*60*60)+"/"+durMap.get(0)+"hours");
                        done.setVisibility(View.VISIBLE);
                        active.setVisibility(View.INVISIBLE);
                        pledgeButton.setVisibility(View.VISIBLE);
                    }
                    else {
                        result.setText("Usage till now : "+totTime/(1000*60*60)+"/"+durMap.get(0)+"hours");
                        done.setVisibility(View.INVISIBLE);
                        active.setVisibility(View.VISIBLE);
                        pledgeButton.setVisibility(View.INVISIBLE);
                    }
                }

                // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

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


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }



}
