package org.pathpal.test;

import java.io.IOException;
import java.util.List;

import org.pathpal.AddressPlace;
import org.pathpal.DirectionsForm;
import org.pathpal.SearchApi;

import android.location.Address;

import junit.framework.TestCase;

public class DirectionsFormTest extends TestCase {
	private DirectionsForm form;
	private DirectionsForm.Waypoint start;
	private DirectionsForm.Waypoint destination;
	private DirectionsForm.Waypoint waypoint;
	private SearchApi api;
	
	public void setUp() {
		form = new DirectionsForm();
		destination = new DummyWaypoint();
	}
	
	public void testChangeStartLocation() {
		waypoint = new DummyWaypoint();
		form.startAt(waypoint);
		assertEquals(waypoint, form.startingLocation());
	}
	
	public void testSimpleTravelPath() {
		form.travelTo(destination);
		assertEquals(form.getTravelPath().get(0).destination(), destination);
	}
	
	public void testDefaultTravelMethod() {
		form.travelTo(destination);
		assertEquals(form.getTravelPath().get(0).method(), DirectionsForm.TravelMethod.UNKNOWN);
	}
	
	public void testTravelMethod() {
		form.travelTo(destination).byCar();
		assertEquals(form.getTravelPath().get(0).method(), DirectionsForm.TravelMethod.DRIVE);
	}
	
	public void testThreeStepTravel() {
		form.travelTo(waypoint).travelTo(destination);
		List<DirectionsForm.Leg> path = form.getTravelPath();
		assertEquals(path.get(0).destination(), waypoint);
		assertEquals(path.get(1).destination(), destination);
	}
	
	private class DummyWaypoint implements DirectionsForm.Waypoint {

		public AddressPlace findAddress(SearchApi api) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
