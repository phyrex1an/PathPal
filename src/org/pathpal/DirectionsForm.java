package org.pathpal;

import java.io.IOException;
import java.util.*;

import android.location.Address;

public class DirectionsForm {
	
	public enum TravelMethod {BUS, WALK};
	// A waypoint is a point along the travel path as entered by the user
	// for example "closest pet store", "Ullevi" or "200m a head"
	// A waypoint is ambiguous and needs extra information to resolve to a
	// location
	public interface Waypoint {
		public Address findAddress(SearchApi api) throws IOException;
	}
	
	private class WaypointAddress implements Waypoint {
		private String address;
		WaypointAddress(String address) {
			this.address = address;
		}
		public Address findAddress(SearchApi api) throws IOException {
			return api.geocoder.getFromLocationName(address, 1, 57.44, 11.60, 57.82, 12.10).get(0);
		}
	}
	
	public class Leg {
		Leg(Waypoint destination) {
			this.destination = destination;
		}
		protected Waypoint destination;
		protected TravelMethod method = TravelMethod.WALK;
		
		public Waypoint destination() {
			return destination;
		}
		
		public TravelMethod method() {
			return method;
		}
	}

	private Waypoint startAt;
	private List<Leg> travelPath = new ArrayList<Leg>();
	
	public DirectionsForm(Waypoint startAt) {
		this.startAt = startAt;
	}
	
	public Waypoint startingLocation() {
		return startAt;
	}

	public void startAt(Waypoint startAt) {
		this.startAt = startAt;
	}
	
	public void startAt(String address) {
		startAt(new WaypointAddress(address));
	}
	
	public void byBus() {
		travelPath.get(travelPath.size()-1).method = TravelMethod.BUS;
	}

	public DirectionsForm travelTo(Waypoint destination) {
		this.travelPath.add(new Leg(destination));
		return this;
	}
	
	public DirectionsForm travelTo(String address) {
		return this.travelTo(new WaypointAddress(address));
	}

	public List<Leg> getTravelPath() {
		return new ArrayList<Leg>(travelPath);
	}
}
