package org.pathpal;

import android.location.Address;
import android.location.Geocoder;

public class SearchApi {
	public Geocoder geocoder;
	public Address startLocation;
	
	public SearchApi(Geocoder g, Address s) {
		this.geocoder = g;
		this.startLocation = s;
	}
}