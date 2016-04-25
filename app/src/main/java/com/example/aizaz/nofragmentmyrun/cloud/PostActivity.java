package com.example.aizaz.nofragmentmyrun.cloud;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aizaz.nofragmentmyrun.MainActivity;
import com.example.aizaz.nofragmentmyrun.R;
import com.example.aizaz.nofragmentmyrun.db.ToursDataSource;
import com.example.aizaz.nofragmentmyrun.model.Tour;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	Button Home,History,Cloud;
	public AsyncDbCloud aDB;

	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console.
	 */
	private String SENDER_ID = "amex-cloud";

	private static final String TAG = "PostActivity";
	private EditText mHistoryText;
	private EditText mPostText;
	private GoogleCloudMessaging gcm;
	private Context context;
	private String regid;

	private IntentFilter mMessageIntentFilter;
	private BroadcastReceiver mMessageUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra("message");
			if (msg != null && msg.equals("update")) {
				refreshPostHistory();
			}
		}
	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cloud);

		Home= (Button) findViewById(R.id.button);
		History= (Button) findViewById(R.id.button2);
		Cloud= (Button) findViewById(R.id.button3);
		Home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PostActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		History.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PostActivity.this, com.example.aizaz.nofragmentmyrun.History.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		Cloud.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});;

		mHistoryText = (EditText) findViewById(R.id.post_text);
		mPostText = (EditText) findViewById(R.id.input_text);

		mHistoryText.setClickable(false);

		mMessageIntentFilter = new IntentFilter();
		mMessageIntentFilter.addAction("GCM_NOTIFY");

		context = getApplicationContext();

		// Check device for Play Services APK. If check succeeds, proceed with
		// GCM registration.
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);

			if (regid.isEmpty()) {
				registerInBackground();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
		return true;
	}

	@Override
	protected void onResume() {
		registerReceiver(mMessageUpdateReceiver, mMessageIntentFilter);
		super.onResume();
        checkPlayServices();

	}

	@Override
	protected void onPause() {
//aDB.cancel(true);
		unregisterReceiver(mMessageUpdateReceiver);
		super.onPause();
	}
	List<Tour> payloadSend = new ArrayList<Tour>();

	public void onPostClicked(View v) {
		//String postText = mPostText.getText().toString();
		//Tour tour = new Tour();
		//List<Tour> toursSend = new ArrayList<Tour>();

		//tour.setread();
		//	aDB=new AsyncDbCloud(this);

		//	aDB.execute(tour);

/*
		if(payloadSend!=null){
			for(int i=0; i<payloadSend.size(); i++) {
				if (payloadSend.get(i).getActLabel() != null) {

					if (payloadSend.get(i).getActLabel() == "Still") {
						postMsg("S"+" "+payloadSend.get(i).getActLabel()+" Latitude "+payloadSend.get(i).getLatitude()+" Longitude "+payloadSend.get(i).getLongitude());
					}

					if (payloadSend.get(i).getActLabel() == "UpStairs") {
						postMsg("U"+" "+payloadSend.get(i).getActLabel()+" Latitude "+payloadSend.get(i).getLatitude()+" Longitude "+payloadSend.get(i).getLongitude());
					}

					if (payloadSend.get(i).getActLabel() == "DownStairs") {
					}

					if (payloadSend.get(i).getActLabel() == "Walking") {
						postMsg("W"+" "+payloadSend.get(i).getActLabel()+" Latitude "+payloadSend.get(i).getLatitude()+" Longitude "+payloadSend.get(i).getLongitude());
					}

					if (payloadSend.get(i).getActLabel() == "On Foot") {
						postMsg("W"+" "+payloadSend.get(i).getActLabel()+" Latitude "+payloadSend.get(i).getLatitude()+" Longitude "+payloadSend.get(i).getLongitude());
					}

					if (payloadSend.get(i).getActLabel() == "Unknown") {
						postMsg("N"+" "+payloadSend.get(i).getActLabel()+" Latitude "+payloadSend.get(i).getLatitude()+" Longitude "+payloadSend.get(i).getLongitude());
					}
					if (payloadSend.get(i).getActLabel() == "unknown") {
						postMsg("N"+" "+payloadSend.get(i).getActLabel()+" Latitude "+payloadSend.get(i).getLatitude()+" Longitude "+payloadSend.get(i).getLongitude());
					}
else{
						postMsg(payloadSend.get(i).getActLabel());
					}

				}  */
				//refreshPostHistory();
			//}
//Log.d("payload","="+payloadSend.get(i).getActLabel());}


		//}


		postMsg("from sensor");
	}

	public void onRefreshClicked(View v) {
		refreshPostHistory();
	}

	private void postMsg(String msg) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... arg0) {
				String url = getString(R.string.server_addr) + "/post.do";
				String res = "";
				Map<String, String> params = new HashMap<String, String>();
				params.put("post_text", arg0[0]);
				params.put("from", "phone");
				try {
					res = ServerUtilities.post(url, params);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				return res;
			}

			@Override
			protected void onPostExecute(String res) {
				mPostText.setText("");
				refreshPostHistory();
			}

		}.execute(msg);
	}

	private void refreshPostHistory() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... arg0) {
				String url = getString(R.string.server_addr)
						+ "/get_history.do";
				String res = "";
				Map<String, String> params = new HashMap<String, String>();
				try {
					res = ServerUtilities.post(url, params);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				return res;
			}

			@Override
			protected void onPostExecute(String res) {
				if (!res.equals("")) {
					mHistoryText.setText(res);
				}
			}

		}.execute();
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(PostActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					ServerUtilities.sendRegistrationIdToBackend(context, regid);

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.i(TAG, "gcm register msg: " + msg);
			}
		}.execute(null, null, null);
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	@Override
	public void onBackPressed() {

		if (aDB != null) {
			aDB.cancel(true);
		}

		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		if (aDB != null) {
			aDB.cancel(true);
		}
		finish();
		super.onDestroy();
	}




	///////////


	public class AsyncDbCloud extends AsyncTask<Tour,Void,List<Tour>>  {




		Context contextD;
		Tour tour;
		ToursDataSource datasource;
		public AsyncDbCloud(Context con){
			this.contextD=con;

		}
		@Override
		protected void onPostExecute(List<Tour> aVoid) {
		//	Toast.makeText(contextD, "db task done", Toast.LENGTH_LONG).show();
			if(aVoid!=null){
				Toast.makeText(contextD, "payload here", Toast.LENGTH_LONG).show();
				payloadSend=aVoid;


			}

		}

		@Override
		protected List<Tour> doInBackground(Tour... params) {

			tour = new Tour();

			datasource = new ToursDataSource(contextD);
			datasource.open();


			if(params[0].getread()){
				List<Tour> toursSend = new ArrayList<Tour>();
				toursSend=  datasource.findAll();
				datasource.deleteAll();
				return toursSend;

			}
			if(!params[0].getread()) {

			//	tour = datasource.create(params[0]);
				//Log.d("DBwrite", "Tour created with id " + tour.getId());
				//  sendMessageToUI(1, "thread");
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			datasource.close();
			super.onCancelled();
		}
	}



	//////////////////






}