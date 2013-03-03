/*
 * Crystal McDonald
 * Java1 1302
 * Week4
 * for this portion I followed Internal storage tutorial www.mybringback.com/  the android 4.0 series
 */
package com.cmcdonald.fairweather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Settings extends Activity implements OnClickListener{
	CheckBox cb;
	EditText et;
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Setting up views Checkbox,Edit,Button
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		cb = (CheckBox)findViewById(R.id.checkBox1);
		et = (EditText)findViewById(R.id.zipText);
		b = (Button)findViewById(R.id.button1);
		//added listener to button
		b.setOnClickListener(this);
		//loadPrefs();
	}

	private void LoadPrefs(){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		//set up values and default false value if checkbox isn't checked
		boolean cbValue = sp.getBoolean("CHECKBOX", false);
		String favLocation = sp.getString("FAVLOCATION", "YourLocation");
		if(cbValue){
			cb.setChecked(true);
			
		}else{
			cb.setChecked(false);
		}
		et.setText(favLocation);
	}
	
	private void savePrefs(String key, boolean value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		//editor to change
		Editor edit = sp.edit();
		//change info wanted
		edit.putBoolean(key, value);
		//validate and finish
		edit.commit();
		
	}
	private void savePrefs(String key, String value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		//editor to change
		Editor edit = sp.edit();
		//change info wanted
		edit.putString(key, value);
		//validate and finish
		edit.commit();
	}

	//button saves preferences
	@Override
	public void onClick(View v) {
		// saving preferences only if checkbox is checked with a boolean
		savePrefs("CHECKBOX", cb.isChecked());
		if(cb.isChecked())
		//save string in edit text to string and add to text
		savePrefs("State", et.getText().toString());
		if(et == null){
    		Log.i("FAVORITE LOCATION", "FAVORITE LOCATION NOT SAVED");
		}else if(et != null){
			Log.i("FAVORITE LOCATION", et.toString());
		}
		finish();//starts up first we will close this if i setup in a diff activity
	}
}
