package org.pathpal.test;

import java.io.IOException;
import java.util.List;

import org.pathpal.DirectionsForm;
import org.pathpal.SearchApi;

import android.location.Address;

import junit.framework.TestCase;

public class DirectionsFormTest extends TestCase {
	private DirectionsForm form;
	private DirectionsForm.Waypoint start;
	private DirectionsForm.Waypoint destination;
	private DirectionsForm.Waypoint waypoint;
	
	public void setUp() {
		start = new DummyWaypoint();
		form = new DirectionsForm(start);
		destination = new DummyWaypoint();
	}
	
	public void testStartLocation() {
		assertEquals(start, form.startingLocation());
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
		assertEquals(form.getTravelPath().get(0).method(), DirectionsForm.TravelMethod.WALK);
	}
	
	public void testTravelMethod() {
		form.travelTo(destination).byBus();
		assertEquals(form.getTravelPath().get(0).method(), DirectionsForm.TravelMethod.BUS);
	}
	
	public void testThreeStepTravel() {
		form.travelTo(waypoint).travelTo(destination);
		List<DirectionsForm.Leg> path = form.getTravelPath();
		assertEquals(path.get(0).destination(), waypoint);
		assertEquals(path.get(1).destination(), destination);
	}
	
	private class DummyWaypoint implements DirectionsForm.Waypoint {

		public Address findAddress(SearchApi api) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	}
}