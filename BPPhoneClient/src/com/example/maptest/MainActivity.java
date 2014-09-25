package com.example.maptest;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptest.RegisterDialog.EditNameDialogListener;
import com.example.maptest.json.Area;
import com.example.maptest.json.Auth;
import com.example.maptest.json.Data;
import com.example.maptest.json.GeneralRequest;
import com.example.maptest.json.Settings;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity implements EditNameDialogListener {

	private final String TAG = "Map Project";
	
	private ConnectivityManager connManager;
	private NetworkInfo mWifi;
	private Runnable r;
	private Circle circle;
	private CircleOptions circleOptions;
	private LatLng location;
	long meterValue;
	float[] distance = new float[2];
	TextView txt = null;
	SendPostRequest request = new SendPostRequest();
	String userId = "";
	String areaSize = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	
		// turns phone silent if wifi is on
		connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//		if (mWifi.isConnected()) {
//			audio.setRingerMode(0);
//		}


	
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
		switch (item.getItemId()) {

		// Single menu item is selected do something
		// Ex: launching new activity/screen or show alert message
		case R.id.menu_layer:


			break;

		case R.id.register:
			FragmentManager dialogFragment = getFragmentManager();
			RegisterDialog testRegisterDialog = new RegisterDialog();
			testRegisterDialog.setRetainInstance(true);
			testRegisterDialog.show(dialogFragment, "fragment_name");

			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return false;

	}

	

	public GeneralRequest generateSavePayload(Area[] areas, String id) {

		Auth myAuth = new Auth(id);
		Data myData = new Data("save", areas);
		GeneralRequest myGeneralRequest = new GeneralRequest(myAuth, myData);

		return myGeneralRequest;
	}

	public GeneralRequest generateSavePayload(String id) {

		Area[] emptyArea = new Area[0];
		Auth myAuth = new Auth(id);
		Data myData = new Data("delete", emptyArea);
		GeneralRequest myGeneralRequest = new GeneralRequest(myAuth, myData);

		return myGeneralRequest;
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "The activity is visible and about to be started.");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "The activity is visible and about to be restarted.");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG,
				"Another activity is taking focus (this activity is about to be \"paused\")");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "The activity is no longer visible (it is now \"stopped\")");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i(TAG, "The activity is about to be destroyed.");
	}

	@Override
	public void onFinishEditDialog(String inputText) {

		Auth auth = new Auth(inputText);

		request.sendMessage(auth, "register");

		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG)
				.show();

	}

	
}
