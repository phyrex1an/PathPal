package org.pathpal;

import java.io.IOException;
import java.util.*;

import android.location.Address;

public class DirectionsForm {
//	private List<Waypoint> waypoints = new ArrayList<Waypoint>();
	
	private Waypoint to, from;
	
	public interface Waypoint {
		public abstract Address findLocation(SearchApi api) throws IOException;
	}
	
	private class AddressLine implements Waypoint {
		private String name;
		
		AddressLine(String name) {
			this.name = name;
		}
		
		public Address findLocation(SearchApi api) throws IOException {
			return api.geocoder.getFromLocationName(name, 1, 57.44,	11.60, 57.82, 12.10).remove(0);
		}
	}
	
	public void goToAddress(String name) {
		to = new AddressLine(name);
	}
	
	public void startAtAddress(String name) {
		from = new AddressLine(name);
	}
	
	public List<Address> getWalkPath(SearchApi api) throws IOException {
		List<Address> path = new ArrayList<Address>();
		path.add(from.findLocation(api));
		path.add(to.findLocation(api));
		return path;
	}
}
