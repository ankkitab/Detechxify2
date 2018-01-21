package com.example.ankkitabose.detechxify2;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppSelector extends AppCompatActivity {

    Spinner dropdown;
    Spinner duration;
    Button add;
    List<String> selectedList;
    DatabaseHandler db;
    Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_selector);

        selectedList = new ArrayList<>();
        map = new HashMap<>();
        Intent intent = getIntent();
        String[] list = intent.getStringArrayExtra("appList");
        /*for(String str:list)
            System.out.println(str);*/
        List<String> packages = new ArrayList<>(Arrays.asList(list));
        List<String> finalPackages = new ArrayList<>();
        PackageManager manager = this.getPackageManager();
        db = new DatabaseHandler(this);
        String[] dur = {"24 hours","48 hours","72 hours"};
        try {
            int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
            for (String pkg : packages) {

                ApplicationInfo app = manager.getApplicationInfo(pkg,0);
                if(((app.flags & mask) == 0) && !pkg.matches("(.*)detechxify(.*)") ) {
                    finalPackages.remove(pkg);
                    finalPackages.add(pkg);
                    System.out.println(pkg);
                }

            }

            String[] drops = new String[finalPackages.size()];
            int i=0;
            for(String pkg:finalPackages) {
                drops[i]=manager.getApplicationLabel(manager.getApplicationInfo(pkg,0)).toString();
                map.put(drops[i],pkg);
                i++;
            }

            dropdown = (Spinner)findViewById(R.id.spinner3);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drops);
            dropdown.setAdapter(spinnerAdapter);

            duration = (Spinner)findViewById(R.id.timer);
            ArrayAdapter<String> dadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dur);
            duration.setAdapter(dadapter);
        }
        catch(PackageManager.NameNotFoundException e) {

        }

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedList.add(dropdown.getSelectedItem().toString());
                db.insertApp(map.get(dropdown.getSelectedItem().toString()), Integer.parseInt(duration.getSelectedItem().toString().split(" ")[0]), System.currentTimeMillis());
                System.out.println(db.size());
            }
        });
    }
}
