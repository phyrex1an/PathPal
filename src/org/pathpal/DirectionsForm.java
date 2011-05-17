package org.pathpal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.grammaticalframework.UnknownLanguageException;
import org.grammaticalframework.Linearizer.LinearizerException;
import org.pathpal.translator.Fun;
import org.pathpal.translator.FunApp;
import org.pathpal.translator.FunString;

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
	
	public class FunStrings {
		public Fun f;
		public List<String> l;
		
		public FunStrings(Fun f, List<String> l) {
			this.f = f;
			this.l = l;
		}	
	}
	
	
	// A waypoint is a point along the travel path as entered by the user
	// for example "closest pet store", "Ullevi" or "200m a head"
	// A waypoint is ambiguous and needs extra information to resolve to a
	// location
	public interface Waypoint {
		public AddressPlace findAddress(SearchApi api) throws IOException;
		public List<Question> questions(WaypointInfo w);
		public String name();
	}
	
	// A question direction towards the user with the intent to resolve some
	// ambiguous data
	public interface Question {
		public FunStrings concreteQuestion();
		public boolean answerQuestion(FunApp answer);
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
			return new ArrayList<Question>();
		}
		public String name() {
			return address;
		}
	}
	
	private class CurrentLocation implements Waypoint {
		public AddressPlace findAddress(SearchApi api) throws IOException {
			return api.startLocation;
		}

		public List<Question> questions(WaypointInfo w) {
			return new ArrayList<Question>();
		}

		public String name() {
			return "yourself";
		}
	}
	
	private class AmbiguousWaypointQuestion implements Question {

		private WaypointInfo waypointinfo;
		private List<AddressPlace> waypoints;

		public AmbiguousWaypointQuestion(List<AddressPlace> waypoints,
				WaypointInfo w) {
			this.waypoints = waypoints;
			this.waypointinfo = w;
		}

		public boolean answerQuestion(FunApp answer) {
			if (answer.getIdent().equals("ProbablyAnAddress")) {
				String a = ((FunString) answer.getArgs().get(0)).getString();
				for (AddressPlace address : this.waypoints) {
					if (address.description.equals(a)) {
						this.waypointinfo.waypoint = new UnambiguousWaypoint(address);
						return true;
					}
				}
			}
			return false;
		}

		public FunStrings concreteQuestion() {
			FunApp fa;
			LinkedList<Fun> args = new LinkedList<Fun>();
			switch (this.waypoints.size()) {
			case 1:	
				fa = new FunApp("DString1", args);
				break;
			case 2:
				fa = new FunApp("DString2", args);
				break;
			case 3:
				fa = new FunApp("DString3", args);
				break;
			default:
				throw new RuntimeException("Not enough DStringS");
			}
			List<String> sargs = new ArrayList<String>();
			for (AddressPlace arg : this.waypoints) {
				sargs.add(arg.description);
			}
			return new FunStrings(fa, sargs);
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

		public String name() {
			return waypoints.get(0).description;
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

		public String name() {
			return address.description;
		}
	}
	
	public class TravelMethodQuestion implements Question {
		private Leg leg;
		TravelMethodQuestion(Leg l) {
			leg = l;
		}
		public boolean answerQuestion(FunApp answer) {
			if (!answer.getIdent().equals("WalkOrTrans")) {
				return false;
			}
			String id = ((FunApp)answer.getArgs().get(0)).getIdent();
			if (id.equals("Walking")) {
				leg.method(TravelMethod.WALK);
			} else if (id.equals("Transportation")) {
				leg.method(TravelMethod.DRIVE);
			}
			return true;
		}

		public FunStrings concreteQuestion() {
			LinkedList<Fun> arg1 = new LinkedList<Fun>();
			LinkedList<Fun> arg2 = new LinkedList<Fun>();
			arg1.add(new FunApp("DString1", arg2));
			List<String> args = new ArrayList<String>();
			args.add(leg.destination.waypoint.name());
			return new FunStrings(new FunApp("WalkOrCarTo", arg1), args);
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
			if (this.method.equals(TravelMethod.UNKNOWN)) {
				questions.add(new TravelMethodQuestion(this));
			}
			return questions;
		}
	}

	private WaypointInfo startAt = new WaypointInfo(null);
	private List<Leg> travelPath;
	private SearchApi api;
	
	public DirectionsForm(SearchApi api) {
		this.api = api;
		this.reset();
	}
	
	public void reset() {
		this.startAt.waypoint = new CurrentLocation();
		this.travelPath = new ArrayList<Leg>();
	}
	
	public Waypoint startingLocation() {
		return startAt.waypoint;
	}

	public DirectionsForm startAt(Waypoint startAt) {
		this.startAt.waypoint = startAt;
		return this;
	}
	
	private Waypoint fromAddress(String address) {
		List<AddressPlace> places = new ArrayList<AddressPlace>();
		try
		{
			List<Address> addresses = this.api.geocoder.getFromLocationName(address, 3, 57.44, 11.60, 57.82, 12.10);
			for (Address a : addresses) {
				GeoPoint g = new GeoPoint( (int)(a.getLatitude()*1000000), (int)(a.getLongitude()*1000000));
				places.add(new AddressPlace(g, a.getFeatureName()));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Waypoint w;
		switch (places.size()) {
		case 0:
			throw new RuntimeException("no path");
			//w = new UnknownWaypoint();
			//break;
		case 1:
			w = new UnambiguousWaypoint(places.get(0));
			break;
			
		default:
			w = new AmbiguousWaypoint(places);
			break;
		}
		return w;
	}
	
	public DirectionsForm startAt(String address) {
		return startAt(fromAddress(address));
	}
	
	private void byMethod(TravelMethod method) {
		travelPath.get(travelPath.size()-1).method = method;
	}
	
	public void byCar() {
		byMethod(TravelMethod.DRIVE);
	}
	
	public void byFoot() {
		byMethod(TravelMethod.WALK);
	}

	public DirectionsForm travelTo(Waypoint destination) {
		this.travelPath.add(new Leg(destination));
		return this;
	}
	
	public DirectionsForm travelTo(String address) {
		return this.travelTo(fromAddress(address));
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
