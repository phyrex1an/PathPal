package org.pathpal;


import android.location.Geocoder;

public class SearchApi {
	public Geocoder geocoder;
	public AddressPlace startLocation;
	
	public SearchApi(Geocoder g, AddressPlace s) {
		this.geocoder = g;
		this.startLocation = s;
	}
}