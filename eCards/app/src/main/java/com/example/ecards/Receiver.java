package com.example.ecards;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Lenovo-USER on 2/22/2015.
 */
public class Receiver extends PebbleKit.PebbleDataReceiver {

    private static final UUID WATCHAPP_UUID = UUID.fromString("ac10f9fb-08c4-453c-b0be-825df186c6e6");

    private static final int
            KEY_BUTTON = 0,
            KEY_VIBRATE = 1,
            BUTTON_UP = 0,
            BUTTON_SELECT = 1,
            BUTTON_DOWN = 2;

    private static final int KEY_NAME = 2;
    private static final int TIMEOUT = 10000;
    private PebbleKit.PebbleDataReceiver appMessageReciever;
    private Handler handler = new Handler();

    public Receiver() {
        super(WATCHAPP_UUID);
    }

    public void receiveData(final Context context, int transactionId, PebbleDictionary data) {
        Log.d("On Resume", "I am inside received data method");
        handler = new Handler();
        //Acknowledge the Pebble message
        PebbleKit.sendAckToPebble(context, transactionId);
        //Checks if button was pressed on watch
        if(data.getInteger(KEY_BUTTON) != null) {
            final int button = data.getInteger(KEY_BUTTON).intValue();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("Receive Data", "Handler run code");
                    if (button == BUTTON_SELECT) {
                        try {
                            sendCard(context);
                            Log.i("Receive Data", "Send card succeeded!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
        }
    }

    public void sendCard(Context context) throws IOException{

        Log.i("Send Card", "In Send card!");

//        DataHandler.writeLine(MainActivity.socket.getOutputStream(), "Umar, Azhar, uazhar@gmail.com, 519-870-9275");
        String tmp = "" + MainActivity.user.getFirstName() + "," + MainActivity.user.getLastName() + "," + MainActivity.user.getMainPhone() + "," + MainActivity.user.getLinkedin() + "," + MainActivity.user.getGithub();
        DataHandler.writeLine(MainActivity.socket.getOutputStream(), tmp);

        String input[] = null;
        long initTime = System.currentTimeMillis();
        String reply = null;
        while (reply == null) {
            try {
                reply = DataHandler.readLine(MainActivity.socket.getInputStream());
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
            out.addString(KEY_NAME, input[0] + " " + input[1]);
            //Adding last name to pebble watch output
//            out.addString(1,  input[1]);
            //Send dictionary to watch
            PebbleKit.sendDataToPebble(context, WATCHAPP_UUID, out);

            String FILENAME = "contacts.txt";
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(reply.getBytes());
            fos.close();

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
