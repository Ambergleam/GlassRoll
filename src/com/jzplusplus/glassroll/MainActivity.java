package com.jzplusplus.glassroll;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// The die value construed as user voice input
	private int dieValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Get the user voice input
		Bundle b = getIntent().getExtras();
		if (b != null) {
			ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
			// Parse it into a number
			NumberFormat nf = NumberFormat.getIntegerInstance();
			nf.setParseIntegerOnly(true);
			try {
				// Convert to a number
				String voiceResultsString = voiceResults.get(0);
				dieValue = nf.parse(voiceResultsString).intValue();
			} catch (Exception e) {
				// Didn't properly convert so exit out
				finish();
			}
		}
		// Display the die-type
		((TextView) findViewById(R.id.die)).setText("d" + Integer.toString(dieValue));
		rollIt();
	}

	/*
	 * Does the RNG for the die roll 
	 */
	void rollIt() {
		Random rand = new Random();
		int value = rand.nextInt(dieValue) + 1;
		((TextView) findViewById(R.id.result)).setText(Integer.toString(value));
		if (value == 1)
			// Turn screen red on lowest roll
			findViewById(R.id.mainview).setBackgroundColor(getResources().getColor(R.color.red));
		else if (value == dieValue)
			// Turn screen green on highest roll
			findViewById(R.id.mainview).setBackgroundColor(getResources().getColor(R.color.green));
		else
			// Default screen is black
			findViewById(R.id.mainview).setBackgroundColor(getResources().getColor(R.color.black));
	}

	/*
	 * On key pad tap, the app rolls again
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		super.onKeyUp(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			rollIt();
			return true;
		}
		return false;
	}
	
}
