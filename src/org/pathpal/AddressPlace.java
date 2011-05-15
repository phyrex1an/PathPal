package org.pathpal;

import com.google.android.maps.GeoPoint;

public class AddressPlace {
	
	public GeoPoint geopoint;
	public String description;
	
	public AddressPlace(GeoPoint geopoint, String description){
		this.geopoint = geopoint;
		this.description = description;
	}
	

}
