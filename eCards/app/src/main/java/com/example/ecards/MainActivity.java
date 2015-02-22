package com.example.ecards;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class MainActivity extends FragmentActivity {
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
        LinearLayout contactCard = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard2 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard3 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard4 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard5 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard6 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard7 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard8 = (LinearLayout)li.inflate(R.layout.contact_card_layout, null);

        linearLayout.addView(contactCard);
        linearLayout.addView(contactCard2);
        linearLayout.addView(contactCard3);
        linearLayout.addView(contactCard4);
        linearLayout.addView(contactCard5);
        linearLayout.addView(contactCard6);
        linearLayout.addView(contactCard7);
        linearLayout.addView(contactCard8);

        setContentView(rl);

        initialisePaging();

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

    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, EditContactActivity.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);
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
}

class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
