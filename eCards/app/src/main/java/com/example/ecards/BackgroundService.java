package com.example.ecards;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by Lenovo-USER on 2/22/2015.
 */
public class BackgroundService extends IntentService{

    private final String IP = "104.131.17.137";
    private final int PORT = 4372;

    private static final UUID WATCHAPP_UUID = UUID.fromString("ac10f9fb-08c4-453c-b0be-825df186c6e6");

    private Socket socket;

    private static final int
            KEY_BUTTON = 0,
            KEY_VIBRATE = 1,
            BUTTON_UP = 0,
            BUTTON_SELECT = 1,
            BUTTON_DOWN = 2;

    private static final int TIMEOUT = 10000;
    private PebbleKit.PebbleDataReceiver appMessageReciever;
    private Handler handler = new Handler();

    public BackgroundService() {
        super("Background_Task");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate() {
        super.onCreate();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Log.i("Service On Create", "Attempting to create socket");
            socket = new Socket(IP, PORT);
        } catch (IOException e) {

        }
    }

    public void onHandleIntent(Intent intent) {
        Log.i("OnHandleIntent", "In Handle intent");
        if(appMessageReciever == null) {
            appMessageReciever = new PebbleKit.PebbleDataReceiver(WATCHAPP_UUID) {
                @Override
                public void receiveData(Context context, int transactionId, PebbleDictionary data) {
                    Log.d("On Resume", "I am inside received data method");
                    //Acknowledge the Pebble message
                    PebbleKit.sendAckToPebble(context, transactionId);
                    //Checks if button was pressed on watch
                    if(data.getInteger(KEY_BUTTON) != null) {
                        final int button = data.getInteger(KEY_BUTTON).intValue();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (button == BUTTON_SELECT)
                                    try {
                                        sendCard();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                            }

                        });
                    }
                }
            };
            // Add AppMessage capabilities
            PebbleKit.registerReceivedDataHandler(this, appMessageReciever);
        }
    }

    public void onPause() {
        if(appMessageReciever != null) {
			unregisterReceiver(appMessageReciever);
			appMessageReciever = null;
		}
    }

    public void sendCard() throws IOException{

        DataHandler.writeLine(socket.getOutputStream(), "Umar, Azhar, uazhar@gmail.com, 519-870-9275");

        String input[] = null;
        long initTime = System.currentTimeMillis();
        String reply = null;
        while (reply == null) {
            try {
                reply = DataHandler.readLine(socket.getInputStream());
                delay(20);
                if(System.currentTimeMillis() - initTime > TIMEOUT){
                    Log.d("In connection method", "Connection timed out.");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reply != null){
            //Separating reply into each eCard attribute
            input = reply.split(",");

            //Creating dictionary to send data to watch
            PebbleDictionary out = new PebbleDictionary();
            //Adding first name to pebble watch output
            out.addString(2, input[0]);
            //Adding last name to pebble watch output
//            out.addString(1,  input[1]);
            //Send dictionary to watch
            PebbleKit.sendDataToPebble(getApplicationContext(), WATCHAPP_UUID, out);

//            TextView tv = (TextView)findViewById(R.id.notification_text);
//            tv.setText(reply);
            Log.d("Reply received.", reply);
        }
        else{

        }
    }

    public static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
