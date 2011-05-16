package org.pathpal;

import java.io.IOException;
import java.util.*;

import com.amelie.driving.DrivingDirections.Mode;

import com.google.android.maps.GeoPoint;

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
	
	public class WaypointInfo {
		public Waypoint waypoint;
		WaypointInfo(Waypoint w) {
			waypoint = w;
		}
		
		public List<Question> questions() {
			return this.waypoint.questions(this);
		}
	}
	
	
	// A waypoint is a point along the travel path as entered by the user
	// for example "closest pet store", "Ullevi" or "200m a head"
	// A waypoint is ambiguous and needs extra information to resolve to a
	// location
	public interface Waypoint {
		public AddressPlace findAddress(SearchApi api) throws IOException;
		public List<Question> questions(WaypointInfo w);
	}
	
	// A question direction towards the user with the intent to resolve some
	// ambiguous data
	public interface Question {
		public String concreteQuestion();
		public void answerQuestion(String answer);
	}
	
	private class WaypointAddress implements Waypoint {
		private String address;
		WaypointAddress(String address) {
			this.address = address;
		}
		public AddressPlace findAddress(SearchApi api) throws IOException {
			Address a =  api.geocoder.getFromLocationName(address, 1, 57.44, 11.60, 57.82, 12.10).get(0);
			GeoPoint g = new GeoPoint( (int)(a.getLatitude()*1000000), (int)(a.getLongitude()*1000000));
			AddressPlace l = new AddressPlace(g, a.getFeatureName());
			return l;
		}
		public List<Question> questions(WaypointInfo w) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private class CurrentLocation implements Waypoint {
		public AddressPlace findAddress(SearchApi api) throws IOException {
			return api.startLocation;
		}

		public List<Question> questions(WaypointInfo w) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private class AmbiguousWaypointQuestion implements Question {

		private WaypointInfo waypointinfo;

		public AmbiguousWaypointQuestion(List<AddressPlace> waypoints,
				WaypointInfo w) {
			this.waypointinfo = w;
		}

		public void answerQuestion(String answer) {
			// TODO Auto-generated method stub
			
		}

		public String concreteQuestion() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private class AmbiguousWaypoint implements Waypoint {

		private List<AddressPlace> waypoints;

		AmbiguousWaypoint(List<AddressPlace> waypoints) {
			if (waypoints.size() == 0)
				throw new IllegalArgumentException();
			this.waypoints = waypoints;
		}

		public AddressPlace findAddress(SearchApi api) throws IOException {
			return this.waypoints.get(0);
		}

		public List<Question> questions(WaypointInfo w) {
			List<Question> questions = new ArrayList<Question>();
			questions.add(new AmbiguousWaypointQuestion(waypoints, w));
			return questions;
		}
	}
	
	private class UnambiguousWaypoint implements Waypoint {
		private AddressPlace address;

		UnambiguousWaypoint(AddressPlace address) {
			this.address = address;
		}
		
		public AddressPlace findAddress(SearchApi api) throws IOException {
			return this.address;
		}

		public List<Question> questions(WaypointInfo w) {
			return new ArrayList<Question>();
		}
	}
	
	public class TravelMethodQuestion implements Question {
		private Leg leg;
		TravelMethodQuestion(Leg l) {
			leg = l;
		}
		public void answerQuestion(String answer) {
			if (answer == "walk") {
				leg.method(TravelMethod.WALK);
			} else if (answer == "car") {
				leg.method(TravelMethod.DRIVE);
			}
		}

		public String concreteQuestion() {
			return "Do you want to walk or go by car?";
		}
	}
	
	public class Leg {
		Leg(Waypoint destination) {
			this.destination.waypoint = destination;
		}
		private WaypointInfo destination = new WaypointInfo(null);
		private TravelMethod method = TravelMethod.UNKNOWN;
		
		public Waypoint destination() {
			return destination.waypoint;
		}
		
		public TravelMethod method() {
			return method;
		}
		
		public void method(TravelMethod method) {
			this.method = method;
		}
		
		public List<Question> questions() {
			List<Question> questions = new ArrayList<Question>();
			questions.addAll(this.destination.questions());
			if (this.destination.equals(TravelMethod.UNKNOWN)) {
				questions.add(new TravelMethodQuestion(this));
			}
			return questions;
		}
	}

	private WaypointInfo startAt = new WaypointInfo(null);
	private List<Leg> travelPath;
	
	public DirectionsForm() {
		this.reset();
	}
	
	public void reset() {
		this.startAt.waypoint = new CurrentLocation();
		this.travelPath = new ArrayList<Leg>();
	}
	
	public Waypoint startingLocation() {
		return startAt.waypoint;
	}

	public void startAt(Waypoint startAt) {
		this.startAt.waypoint = startAt;
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
	
	public List<Question> questions() {
		List<Question> questions = new ArrayList<Question>();
		questions.addAll(this.startAt.questions());
		for (Leg l : this.travelPath) {
			questions.addAll(l.questions());
		}
		return questions;
	}
}
