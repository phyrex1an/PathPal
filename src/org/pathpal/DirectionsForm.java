package org.pathpal;

import java.io.IOException;
import java.util.*;
import com.amelie.driving.DrivingDirections.Mode;

import android.location.Address;

public class DirectionsForm {
	
	public enum TravelMethod {
			UNKNOWN (Mode.DRIVING), 
			DRIVE (Mode.DRIVING), 
			WALK (Mode.WALKING);
	
			private Mode gmode; 
			TravelMethod(Mode mode) {
				gmode = mode;
			}
			
			public Mode realMode() {
				return gmode;
			}
	};
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
	
	private class CurrentLocation implements Waypoint {
		public Address findAddress(SearchApi api) throws IOException {
			return api.startLocation;
		}
	}
	
	public class Leg {
		Leg(Waypoint destination) {
			this.destination = destination;
		}
		private Waypoint destination;
		private TravelMethod method = TravelMethod.UNKNOWN;
		
		public Waypoint destination() {
			return destination;
		}
		
		public TravelMethod method() {
			return method;
		}
	}

	private Waypoint startAt;
	private List<Leg> travelPath;
	
	public DirectionsForm() {
		this.reset();
	}
	
	public void reset() {
		this.startAt = new CurrentLocation();
		this.travelPath = new ArrayList<Leg>();
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
	
	public void byCar() {
		travelPath.get(travelPath.size()-1).method = TravelMethod.DRIVE;
	}

	public DirectionsForm travelTo(Waypoint destination) {
		this.travelPath.add(new Leg(destination));
		return this;
	}
	
	public DirectionsForm travelTo(String address) {
		return this.travelTo(new WaypointAddress(address));
	}
	
	public DirectionsForm andBackHere() {
		return this.travelTo(new CurrentLocation());
	}

	public List<Leg> getTravelPath() {
		return new ArrayList<Leg>(travelPath);
	}
}
