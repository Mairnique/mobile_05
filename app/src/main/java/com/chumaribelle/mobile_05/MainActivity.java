package com.chumaribelle.mobile_05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG = "com.example.mobile05.sharedpreferences";
    LifecycleData currentRun, lifetimeRun;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView tCurrent, tLifetime;
    Button bLeft, bRight;
    int countonCreate=0;
    int countonStart=0;
    int countonResume=0;
    int countonPause=0;
    int countonStop=0;
    int countonRestart=0;
    int countonDestroy=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate two buttons
        bLeft = findViewById(R.id.bL);
        bRight = findViewById(R.id.bR);

        // load shared preferences
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);

        // instantiate editor
        editor = sharedPreferences.edit();
        currentRun = new LifecycleData();
        currentRun.duration = "Current Run";

        // get lifecycledata from SharedPreferences as String
        String lifecycleDataAsString = sharedPreferences.getString("lifetime", "");

        // if empty, instantiate new  lifecycledata; else convert JSON string to lifecycle data object
        if (lifecycleDataAsString.equals("")){
            lifetimeRun = new LifecycleData();
            lifetimeRun.duration = "Lifetime Run";
        }
        else {
            lifetimeRun = LifecycleData.parseJSON(lifecycleDataAsString);
        }

        // instantiate textviews
        tLifetime = findViewById(R.id.lifetime);
        tCurrent = findViewById(R.id.current);

        // get current enclosing method name
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();
        updateCount(currentEnclosingMethod);
        // display data in textview
        displayData();
    }
    // display data in textview
    private void displayData() {
        tLifetime.setText(lifetimeRun.toString());
        tCurrent.setText(currentRun.toString());
    }

    // convert lifetime to string and store in SharedPreferences
    public void storeData() {
        editor.putString("lifetime", lifetimeRun.toJSON()).apply();

    }

    @Override
    public void onClick(View view) {
    }

    private void setInitialValues() {
        countonCreate = sharedPreferences.getInt("onCreate", 0);
        countonStart = sharedPreferences.getInt("onStart", 0);
        countonResume = sharedPreferences.getInt("onResume", 0);
        countonPause = sharedPreferences.getInt("onPause", 0);
        countonStop = sharedPreferences.getInt("onStop", 0);
        countonRestart = sharedPreferences.getInt("onRestart", 0);
        countonDestroy = sharedPreferences.getInt("onDestroy", 0);
    }

    private void storeValues() {
        editor.putInt("onCreate", countonCreate);
        editor.putInt("onStart", countonStart);
        editor.putInt("onResume", countonResume);
        editor.putInt("onPause", countonPause);
        editor.putInt("onStop", countonStop);
        editor.putInt("onRestart", countonRestart);
        editor.putInt("onDestroy", countonDestroy);
        editor.apply();
    }

    public void updateCount(String currentEnclosingMethod) {
        // pass name to LifecycleData to update count
        currentRun.updateEvent(currentEnclosingMethod);
        lifetimeRun.updateEvent(currentEnclosingMethod);
        displayData();
        storeData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        updateCount(currentEnclosingMethod);
    }
    @Override
    protected void onResume() {
        super.onResume();
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        updateCount(currentEnclosingMethod);
    }
    @Override
    protected void onPause() {
        super.onPause();
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        updateCount(currentEnclosingMethod);
    }
    @Override
    protected void onStop() {
        super.onStop();
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        updateCount(currentEnclosingMethod);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        updateCount(currentEnclosingMethod);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String currentEnclosingMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        updateCount(currentEnclosingMethod);
    }

    public void clearRunData(View view) {
        currentRun.reset();
        displayData();
        storeData();
    }

    public void clearLifetimeData(View view) {
        lifetimeRun.reset();
        displayData();
        storeData();
    }
}