package com.example.java1_week3;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class TempDisplay extends GridLayout{

	
	TextView _title;
	TextView _date;
	TextView _temp;
	TextView _text;
	Context _context;
	
	
	public TempDisplay(Context context){
		super(context);
		
		//assigning a value
		_context = context;
		
		//columns/rows  1 for labels and one for values
		this.setColumnCount(2);
		
		TextView locLabel = new TextView(_context);
		locLabel.setText("Location: ");
		_title = new TextView(_context);
		
		TextView dateLabel = new TextView(_context);
		dateLabel.setText("Date: ");
		_date = new TextView(_context);
		
		TextView tempLabel = new TextView(_context);
		tempLabel.setText("Temprature: ");
		_temp = new TextView(_context);
		
		TextView textLabel = new TextView(_context);
		textLabel.setText("Forecast: ");
		_text = new TextView(_context);
		
		
		//add views to display
		this.addView(locLabel);
		this.addView(_title);
		this.addView(dateLabel);
		this.addView(_date);
		this.addView(tempLabel);
		this.addView(_temp);
		this.addView(textLabel);
		this.addView(_text);
		
		
		
		
	}
}
