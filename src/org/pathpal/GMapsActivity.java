package org.pathpal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.grammaticalframework.UnknownLanguageException;
import org.pathpal.DirectionsForm.Leg;
import org.pathpal.DirectionsForm.Question;
import org.pathpal.DirectionsForm.Waypoint;
import org.pathpal.translator.FunApp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.amelie.driving.DrivingDirections;
import com.amelie.driving.Route;
import com.amelie.driving.DrivingDirections.IDirectionsListener;
import com.amelie.driving.DrivingDirections.Mode;
import com.amelie.driving.impl.DrivingDirectionsGoogleKML;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

/**
 * This class take care of all user input and deligate the work to the libraries. 
 * Also paints overlays on the map
 */
public class GMapsActivity extends MapActivity implements IDirectionsListener, LocationListener {

	private MapView mapView;
	private Projection projection;
	private MapController mapController; 
	private MyLocationOverlay myLocationOverlay;
	private List<Overlay> mapOverlays;
	private boolean follow_your_location;
	private DirectionsForm directionForm;
	private Question activeQuestion;
	private SearchApi api;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmapslayout);
		
		// when the gps updates, the map should update its position too if true
		follow_your_location = true;
		
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		projection = mapView.getProjection();
		
		mapOverlays = mapView.getOverlays();
		
		// setup for gps
		LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
		
		mapController = mapView.getController();
		mapController.setZoom(14);
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// callback when driving directions are available
	public void onDirectionsAvailable(Route route, Mode mode) {		
		mapOverlays = mapView.getOverlays();
		mapOverlays.add(new PathOverlay(route.getGeoPoints(), mode));
	}

	// callback if no driving direction where available
	public void onDirectionsNotAvailable() {
		showDialog(NO_PATH_ID);
	}
	
	/**
	 * Draw a path from a list of GeoPoints
	 */
	public class PathOverlay extends Overlay {
		
		private GeoPoint first;
		private List<GeoPoint> points;
		private Mode m;
		
	    public PathOverlay(List<GeoPoint> points, Mode m) {
	    	this.points = new ArrayList<GeoPoint>(points);
	    	this.first  = this.points.remove(0);
	    	this.m      = m;
	    }
	    
	    @Override
	    public boolean onTap(GeoPoint p, MapView mapView) {
	    	// TODO : Be able to edit the leg, change travelpath etc
	    	return super.onTap(p, mapView);
	    }

	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	        super.draw(canvas, mapv, shadow);
	        Paint mPaint = new Paint();
	        mPaint.setDither(true);
	        
	        // set the color of the path to 
	        // 		RED       = CAR
	        //		BLUE      = WALKING
	        //      Otherwise = GREY
	        if(m == Mode.DRIVING)
	        	mPaint.setColor(Color.RED);
	        else if(m == Mode.WALKING)
	        	mPaint.setColor(Color.BLUE);
	        else
	        	mPaint.setColor(Color.DKGRAY);
	        	
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(4);
	        
	        Path path = new Path();
	        
	        GeoPoint from = this.first;
	        for (GeoPoint to : this.points) {
	        	Point p1 = new Point();
		        Point p2 = new Point();
		        
		        projection.toPixels(from, p1);
		        projection.toPixels(to, p2);

		        path.moveTo(p2.x, p2.y);
		        path.lineTo(p1.x,p1.y);
		        from = to;
	        }
	        canvas.drawPath(path, mPaint);
	    }   
	}
	
	/**
	 * Adds all path overlays to the map
	 */
	private void updatePath(){
		try {
			// clear all previous overlays on the map
			mapOverlays.clear();	
			mapOverlays.add(myLocationOverlay);
			
			// get the starting address
			AddressPlace fromAddress = directionForm.startingLocation().findAddress(api);
			
			// Draw the starting address as a red dot
			Drawable start_draw = this.getResources().getDrawable(android.R.drawable.ic_notification_overlay);
			CustomItemizedOverlay  start = new CustomItemizedOverlay(start_draw, this);
			OverlayItem o1 = new OverlayItem(fromAddress.geopoint, "", fromAddress.description);
			start.addOverlay(o1);
			mapOverlays.add(start);
			
			// Animate to the starting Address on the map
			mapController.animateTo(fromAddress.geopoint);	
			
			// for every leg, 
			//		draw the path from starting to ending address, 
			// 		and draw a green icon for the legs ending address
			for (Leg curLeg : directionForm.getTravelPath()) {
				// get the ending address of this leg
				Waypoint toWaypoint = curLeg.destination();
				AddressPlace toAddress = toWaypoint.findAddress(api);

				// Draw the legs ending address as a green dot
				Drawable goal_draw = this.getResources().getDrawable(android.R.drawable.presence_online);
				CustomItemizedOverlay  goal = new CustomItemizedOverlay(goal_draw, this);
				OverlayItem o2 = new OverlayItem(toAddress.geopoint, "", toAddress.description);
				goal.addOverlay(o2);
				mapOverlays.add(goal);	
				
				// Use the driving API to get the path. onDirectionsAvailble is then called to 
				// draw the path
				DrivingDirections dd = new DrivingDirectionsGoogleKML();
				dd.driveTo(fromAddress.geopoint, toAddress.geopoint, curLeg.method().realMode(), this);
					
				// set the next legs starting Address to the this legs ending address 
				fromAddress = toAddress;
			}				
		
			// Check if there are still some questions which have not been answered
			questionHandler();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e){
			showDialog(NO_PATH_ID);					
		}
	}
	
	/**
	 * Checks if the directionForm till has some unanswered question.
	 * If so, show a dialog where the user can answer a question
	 */
	public void questionHandler(){
		// if there are questions then take care of them
		if(directionForm.questions().size()>0){
			System.out.println("QUESTIONS: " + directionForm.questions().size());
			activeQuestion = directionForm.questions().get(0); // select a question as active
			removeDialog(QUESTION_ID); // clean up the dialog first
			showDialog(QUESTION_ID);   // then show the dialog
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FIND_PATH_REQUEST_CODE){
			System.out.println(resultCode + " <-- resultCode");
			if(resultCode!=RESULT_CANCELED){
				// Get the input string in natural language
				// which should be passed to GF through the TranslatorApi
				Bundle d = data.getExtras();				
				String nlp = d.get("nlp").toString();
				
				// Get your current location and create a new SearchApi with your current location
				AddressPlace l = new AddressPlace(myLocationOverlay.getGeoPoint(), "You are here");				

				
				// create a new directionForm if needed
				if(directionForm == null){
					api = new SearchApi(new Geocoder(getApplicationContext()), l); 
					api.geocoder = new Geocoder(getApplicationContext());
					directionForm = new DirectionsForm(api);
				} else {
					api.startLocation = l;
				}
				
				 
				    // The TranslatorApi update the directionForm
				    // the inputstring nlp is passed to the TranslatorApi
					try {
						boolean work = TranslatorApi.translateString(nlp, directionForm, getResources().openRawResource(R.raw.locator));
						if(!work){
							System.out.println("ERROR: Could not parse");
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (UnknownLanguageException e1) {
						e1.printStackTrace();
					} 
					
				
				// Test data to use as long as the TranslatorApi is not working properly
			//	directionForm.startAt("ullevi");
			//	directionForm.travelTo("gamla ullevi");
			//	directionForm.travelTo("brunnsparken");
				
				// do all the overlay drawings on the map
				updatePath();
			} 
		}
			
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	// these id are used to select the right dialog
	final int NO_PATH_ID = 0;
	final int QUESTION_ID = 1;
	final int GPS_NOT_LOADED_ID = 2;
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog d = null;
		if(id == NO_PATH_ID){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No path found! Try a new path!")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			return(builder.create());
		}else if(id == GPS_NOT_LOADED_ID){
			AlertDialog.Builder gpsNotLoadedBuilder = new AlertDialog.Builder(this);
			gpsNotLoadedBuilder.setMessage("Please wait until GPS is loaded");
			gpsNotLoadedBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			return (gpsNotLoadedBuilder.create());
		}
		else if(id == QUESTION_ID){
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			// TODO : Be able to know which leg/waypoint you are updating!
			alert.setTitle("Need more information!");
			alert.setMessage(activeQuestion.concreteQuestion());

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = input.getText().toString();
					try{
						FunApp abstractAnswer = (FunApp) TranslatorApi.parseString(value, getResources().openRawResource(R.raw.locator));
						if(abstractAnswer != null){
							activeQuestion.answerQuestion(abstractAnswer);
							updatePath();
						}
					}catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (UnknownLanguageException e1) {
						e1.printStackTrace();
					} 
					// finish and disnmiss the dialog!
					dialog.dismiss();
				}
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});
			return(alert.create());
		}
		return d;
	}
	
	/**
	 *  Translates a latitude and longitude to a GeoPoint
	 * @param latitude
	 * @param longitude
	 * @return a GeoPoint
	 */
	protected GeoPoint geopointfromDouble(double latitude, double longitude){
		final int convert = 1000000;
		return new GeoPoint( ((int)(latitude * convert)) , ((int)(longitude * convert)) );
	}


	static final int FIND_PATH_REQUEST_CODE = 0;
	static final int YOUR_LOCATION_REQUEST_CODE = 1;
	 
	// method to handle a menu button action when you press the menu button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.find_path:
	    	if(myLocationOverlay != null){
		    	Intent myIntent = new Intent(this, GMapsFindPathActivity.class);
		    	startActivityForResult(myIntent, FIND_PATH_REQUEST_CODE);
		    	return true;
	    	}else{
	    		showDialog(GPS_NOT_LOADED_ID);
	    		return true;
	    	}
	    case R.id.your_location:
	    	if(myLocationOverlay != null){
		    	if (follow_your_location) {
		    		follow_your_location = false;
		    		mapController.animateTo(myLocationOverlay.getGeoPoint());
		    	}else{
		    		follow_your_location = true;
		    	}
	    	}else{
	    		showDialog(GPS_NOT_LOADED_ID);
	    		return true;
	    	}
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	
	// define the menu from the XML
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.gmapmenu, menu);
	    return true;
	}

	// when the gps update its position this code is run
	public void onLocationChanged(Location location) {
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			GeoPoint p = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000) );
		
			mapView.getOverlays().remove(myLocationOverlay);
			myLocationOverlay = new MyLocationOverlay(p);
			mapView.getOverlays().add(myLocationOverlay);
			
			if(follow_your_location)
				mapController.animateTo(p);
		}
	}

	public void onProviderDisabled(String provider) {}
	public void onProviderEnabled(String provider) {}
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	
}
