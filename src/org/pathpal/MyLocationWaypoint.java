package org.pathpal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.pathpal.DirectionsForm.Question;
import org.pathpal.DirectionsForm.Waypoint;
import org.pathpal.DirectionsForm.WaypointInfo;

import com.google.android.maps.GeoPoint;

import android.location.Address;

public class MyLocationWaypoint implements Waypoint {
	
	GeoPoint p;
	public MyLocationWaypoint(GeoPoint p){
		this.p = p;
	}

	public AddressPlace findAddress(SearchApi api) throws IOException {
		Address a = api.geocoder.getFromLocation(fromE6(p.getLatitudeE6()) , fromE6(p.getLongitudeE6()), 1).remove(0);
		// TODO  : return AddressPlace
		return null;
	}
	
	private double fromE6(int l){
		return (((double)l) / 1000000);
	}

	public List<Question> questions(WaypointInfo w) {
		// TODO Auto-generated method stub
		return new ArrayList<Question>();
	}

}
