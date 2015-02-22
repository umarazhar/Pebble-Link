package com.example.ecards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class MainActivity extends Activity {
	private static final String SERVER_IP = "104.131.17.137";
	private static final int SERVER_PORT = 4732;
	private static final UUID WATCHAPP_UUID = UUID.fromString("ac10f9fb-08c4-453c-b0be-825df186c6e6");
	private static final int KEY_NAME = 2;
	private static final int
	KEY_BUTTON = 0,
	KEY_VIBRATE = 1,
	BUTTON_UP = 0,
	BUTTON_SELECT = 1,
	BUTTON_DOWN = 2;

	private static final int TIMEOUT = 10000;
	private PebbleDataReceiver appMessageReciever;
	private Handler handler = new Handler();
	public static Socket socket;

    public static User user;

    private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Log.d("On Create Method", "Before Strict mode");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayoutInflater li = this.getLayoutInflater();
        RelativeLayout rl = (RelativeLayout)li.inflate(R.layout.activity_main, null);
        LinearLayout linearLayout = (LinearLayout)rl.findViewById(R.id.contact_card_container);

//        loadUserData();

        try {
            FileInputStream fin = openFileInput("contacts.txt");
            Log.i("Read File", "Opened file for reading");
            int c;
            while ((c = fin.read()) != -1) {
                String temp = "";
                while (c != '\n' && c != -1) {
                    Log.i("Reading Characters", "Character: " + Character.toString((char)c));
                    temp += Character.toString((char) c);
                    c = fin.read();
                }
                String input[] = temp.split(",");

                LinearLayout contactCard = (LinearLayout) li.inflate(R.layout.contact_card_layout, null);

                Log.i("Read File", "Reading user data from file");

                TextView name = (TextView) contactCard.findViewById(R.id.contact_name);
                name.setText(input[0] + " " + input[1]);
                TextView email = (TextView) contactCard.findViewById(R.id.contact_email);
                email.setText(input[2]);
                TextView phone = (TextView) contactCard.findViewById(R.id.contact_phone);
                phone.setText(input[3]);
                TextView linkedIn = (TextView) contactCard.findViewById(R.id.contact_linkedin);
                linkedIn.setText(input[4]);
                TextView gitHub = (TextView) contactCard.findViewById(R.id.contact_github);
                gitHub.setText(input[5]);
                linearLayout.addView(contactCard);
            }

            Log.i("Read File", "Finished reading file");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        LinearLayout card1 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
//        LinearLayout card2 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
//        LinearLayout card3 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);


//        TextView name = (TextView) card1.findViewById(R.id.contact_name);
//        name.setText("Cathy Lei");
//        TextView email = (TextView) card1.findViewById(R.id.contact_email);
//        email.setText("cathy@gmail.com");
//        TextView phone = (TextView) card1.findViewById(R.id.contact_phone);
//        phone.setText("3122345444");
//        TextView linkedIn = (TextView) card1.findViewById(R.id.contact_linkedin);
//        linkedIn.setText("www.linkedin.com/in/cathy");
//        TextView gitHub = (TextView) card1.findViewById(R.id.contact_github);
//        gitHub.setText("github.com/cathy");
//
//        TextView name1 = (TextView) card2.findViewById(R.id.contact_name);
//        name1.setText("Albert Tai");
//        TextView email1 = (TextView) card2.findViewById(R.id.contact_email);
//        email1.setText("ttai3@gmail.com");
//        TextView phone1 = (TextView) card2.findViewById(R.id.contact_phone);
//        phone1.setText("2262395218");
//        TextView linkedIn1 = (TextView) card2.findViewById(R.id.contact_linkedin);
//        linkedIn1.setText("www.linkedin.com/in/albert");
//        TextView gitHub1 = (TextView) card2.findViewById(R.id.contact_github);
//        gitHub1.setText("github.com/albert");
//
//        TextView name2 = (TextView) card3.findViewById(R.id.contact_name);
//        name2.setText("Umar Azhar");
//        TextView email2 = (TextView) card3.findViewById(R.id.contact_email);
//        email2.setText("uazhar@gmail.com");
//        TextView phone2 = (TextView) card3.findViewById(R.id.contact_phone);
//        phone2.setText("5198704662");
//        TextView linkedIn2 = (TextView) card3.findViewById(R.id.contact_linkedin);
//        linkedIn2.setText("www.linkedin.com/in/umar");
//        TextView gitHub2 = (TextView) card3.findViewById(R.id.contact_github);
//        gitHub2.setText("github.com/umar");
//
//        linearLayout.addView(card1);
//        linearLayout.addView(card2);
//        linearLayout.addView(card3);


        setContentView(rl);


        PebbleKit.registerReceivedDataHandler(this, new Receiver());

//        Intent intent = new Intent(this, BackgroundService.class);
//        startService(intent);
		//sideloadInstall(getApplicationContext(), WATCHAPP_FILENAME);
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Log.d("On Create Method", "This is working");
	}

    private void loadUserData() {
        try {
            FileInputStream fin = openFileInput("user.txt");
            int c;
            String tmp = "";
            while ((c = fin.read()) != -1) {
                tmp += Character.toString((char)c);
            }

            String[] input = tmp.split(",");

            user.setFirstName(input[0]);
            user.setLastName(input[1]);
            user.setEmail(input[2]);
            user.setMainPhone(input[3]);
            user.setLinkedin(input[4]);
            user.setGithub(input[5]);
        } catch (IOException e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_next) {
            Intent intent = new Intent(this, EditContactActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onResume() {
		super.onResume();
//		if(appMessageReciever == null) {
//			appMessageReciever = new PebbleDataReceiver(WATCHAPP_UUID) {
//				@Override
//				public void receiveData(Context context, int transactionId, PebbleDictionary data) {
//					Log.d("On Resume", "I am inside received data method");
//					//Acknowledge the Pebble message
//					PebbleKit.sendAckToPebble(context, transactionId);
//					//Checks if button was pressed on watch
//					if(data.getInteger(KEY_BUTTON) != null) {
//						final int button = data.getInteger(KEY_BUTTON).intValue();
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								if (button == BUTTON_SELECT)
//									try {
//										sendCard();
//									} catch (IOException e) {
//										e.printStackTrace();
//									}
//							}
//
//						});
//					}
//				}
//			};
//			// Add AppMessage capabilities
//			PebbleKit.registerReceivedDataHandler(this, appMessageReciever);
//		}
	}

//	public void sendCard() throws IOException{
//
//		DataHandler.writeLine(socket.getOutputStream(), "Umar, Azhar, uazhar@gmail.com, 519-870-9275");
//
//		String input[] = null;
//		long initTime = System.currentTimeMillis();
//		String reply = null;
//		while (reply == null) {
//			try {
//				reply = DataHandler.readLine(socket.getInputStream());
//				delay(20);
//				if(System.currentTimeMillis() - initTime > TIMEOUT){
//					Log.d("In connection method", "Connection timed out.");
//					break;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (reply != null){
//			//Separating reply into each eCard attribute
//			input = reply.split(",");
//
//			//Creating dictionary to send data to watch
//			PebbleDictionary out = new PebbleDictionary();
//			//Adding first name to pebble watch output
//			out.addString(KEY_FIRST, input[0]);
//			//Send dictionary to watch
//			PebbleKit.sendDataToPebble(getApplicationContext(), WATCHAPP_UUID, out);
//
//			TextView tv = (TextView)findViewById(R.id.notification_text);
//			tv.setText(reply);
//			Log.d("Reply received.", reply);
//		}
//		else{
//
//		}
//	}

	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void onPause() {
		super.onPause();
//		if(appMessageReciever != null) {
//			unregisterReceiver(appMessageReciever);
//			appMessageReciever = null;
//		}
	}

    protected void onDestroy() {
        super.onDestroy();
        if(appMessageReciever != null) {
			unregisterReceiver(appMessageReciever);
			appMessageReciever = null;
		}
    }
}