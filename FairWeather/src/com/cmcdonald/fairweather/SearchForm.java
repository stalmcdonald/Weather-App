/*
 * Crystal McDonald
 * Java 1 1302
 * Week 3
 */
package com.cmcdonald.fairweather;

import android.content.Context;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchForm extends LinearLayout{

	//global variables
	
	EditText _searchField;
	Button _searchButton;
									//adding parameters
	public SearchForm(Context context, String hint, String buttonText) {
		super(context);
		
		//makes it look the way I want it to using Layout Parameters
		LayoutParams lp;
		
		
		// executes value
		//text - edit text
		_searchField = new EditText(context);
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		//apply layout parameters
		_searchField.setLayoutParams(lp);
		//using the text
		_searchField.setHint(hint);
		
		
		//search button
		_searchButton = new Button(context);
		//using text on button
		_searchButton.setText(buttonText);
		
		//create views
		this.addView(_searchField);
		this.addView(_searchButton);
		
		//set layout params so text takes up the full width
		lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
		
		
	}

	//adding functionality with custom methods
	public EditText getField(){
		return _searchField;
	}
	public Button getButton(){
		return _searchButton;
	}
}
