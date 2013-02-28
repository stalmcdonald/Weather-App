/*
 * Crystal McDonald
 * Java 1 1302
 * Week 4
 */
package com.cmcdonald.fairweather;

public class XMLDataCollected {
	private int temp = 0;
	private String city = null;

	public void setCity(String c){
		city = c;
	}
	
	public void setTemp(int t){
		temp = t;
	}
	
	public String dataToString(){
		return "In " + city + " the Current Temp in F is " + temp + " degrees";
	}	
}
