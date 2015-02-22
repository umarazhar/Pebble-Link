package com.example.lenovo_user.pebblelink_app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.getpebble.android.kit.PebbleKit;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PebbleKit.registerPebbleConnectedReceiver(getApplicationContext(), new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(getLocalClassName(), "Pebble connected!");
            }

        });

        PebbleKit.registerPebbleDisconnectedReceiver(getApplicationContext(), new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(getLocalClassName(), "Pebble disconnected!");
        }

    });

    setContentView(R.layout.activity_main);
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

    public void  sendData(View view) {


        boolean pebbleConnected = PebbleKit.isWatchConnected(getApplicationContext());

        if (pebbleConnected) {
            Log.i("sendData", "Pebble is connected!");
        } else {
            Log.i("sendData", "Pebble is not connected!");
        }


        
    }
}
