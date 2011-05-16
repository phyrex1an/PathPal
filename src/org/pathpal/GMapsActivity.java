package org.pathpal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.grammaticalframework.UnknownLanguageException;
import org.pathpal.DirectionsForm.Leg;
import org.pathpal.DirectionsForm.Waypoint;

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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amelie.driving.DrivingDirections;
import com.amelie.driving.Placemark;
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

public class GMapsActivity extends MapActivity implements IDirectionsListener, LocationListener {

	private MapView mapView;
	private Projection projection;
	private CustomItemizedOverlay itemizedOverlay;
	private MapController mapController; 
	
	private MyLocationOverlay myLocationOverlay;
	
	private List<Overlay> mapOverlays;
	
	private boolean follow_your_location;
	
	private DirectionsForm directionForm;
	
	private int currentColor;
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmapslayout);
		
		follow_your_location = true;
		
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		projection = mapView.getProjection();
		
		mapOverlays = new ArrayList<Overlay>();
		
		LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
		
	//	currentColor = Color.RED;
		
		mapController = mapView.getController();
		mapController.setZoom(14);
		
	}
	
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// Auto-generated method stub
		return false;
	}

	public void onDirectionsAvailable(Route route, Mode mode) {
		List<GeoPoint> gps = route.getGeoPoints();
		mapOverlays = mapView.getOverlays();
		PathOverlay p = new PathOverlay(gps);
		System.out.println(currentColor);
		System.out.println(currentColor);
		mapOverlays.add(new PathOverlay(gps));
		
	}

	public void onDirectionsNotAvailable() {
		// Auto-generated method stub
		System.out.println("DIRECTIONS ARE NOT AVAILABLE!!!!!!!");
		System.out.println("DIRECTIONS ARE NOT AVAILABLE!!!!!!!");
		System.out.println("DIRECTIONS ARE NOT AVAILABLE!!!!!!!");
		System.out.println("DIRECTIONS ARE NOT AVAILABLE!!!!!!!");
		System.out.println("DIRECTIONS ARE NOT AVAILABLE!!!!!!!");
	}
	
	public class PathOverlay extends Overlay {
		
		private GeoPoint first;
		private List<GeoPoint> points;
		private int c;
		
	    public PathOverlay(List<GeoPoint> points) {
	    	this.points = new ArrayList<GeoPoint>(points);
	    	this.first = this.points.remove(0);
	    	this.c = Color.RED;
	    }
	    
	    public void setColor(int c){
	    	this.c = c;
	    }

	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	    	
	        super.draw(canvas, mapv, shadow);
	        Paint mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(nextColor());
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
	

	
	static final int FIND_PATH_REQUEST_CODE = 0;
	 static final int YOUR_LOCATION_REQUEST_CODE = 1;
	 
	// method to handle a menu button action when you press the menu button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	//    case R.id.exit:
	 //   	showDialog(DIALOG_EXIT_ID);
	 //       return true;
	    case R.id.find_path:
	    	Intent myIntent = new Intent(this, GMapsFindPathActivity.class);
	    	startActivityForResult(myIntent, FIND_PATH_REQUEST_CODE);
	    	return true;
	    case R.id.your_location:
	    	if (follow_your_location) {
	    		follow_your_location = false;
	    		mapController.animateTo(myLocationOverlay.getGeoPoint());
	    	}else{
	    		follow_your_location = true;
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



	// Called when an activity you launched exits, 
	//giving you the requestCode you started it with, 
	//the resultCode it returned, 
	//and any additional data from it
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FIND_PATH_REQUEST_CODE){
			System.out.println(resultCode + " <-- resultCode");
			if(resultCode!=RESULT_CANCELED){
				Bundle d = data.getExtras();				
				String nlp = d.get("nlp").toString();
				
				AddressPlace l =new AddressPlace(myLocationOverlay.getGeoPoint(), "You are here");				
				SearchApi api = new SearchApi(new Geocoder(getApplicationContext()), l); // TODO: null == current location
				api.geocoder = new Geocoder(getApplicationContext());
				
				if(directionForm == null){
					directionForm = new DirectionsForm();
				}
				
				
				/*
					try {
						boolean work = TranslatorApi.translateString(nlp, directionForm, getResources().openRawResource(R.raw.locator));
						if(!work){
							System.out.println("ERROR: Could not parse");
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnknownLanguageException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} */
				directionForm.startAt("ullevi");
				directionForm.travelTo("gamla ullevi");
				directionForm.travelTo("brunnsparken");
				

				
				try {

					AddressPlace fromAddress = null;
					fromAddress = directionForm.startingLocation().findAddress(api);
					
					Drawable start_draw = this.getResources().getDrawable(android.R.drawable.ic_notification_overlay);
					CustomItemizedOverlay  start = new CustomItemizedOverlay(start_draw, this);
					OverlayItem o1 = new OverlayItem(fromAddress.geopoint, "", fromAddress.description);
					start.addOverlay(o1);
					mapOverlays = mapView.getOverlays();
					mapOverlays.add(start);
					
					
					for (Leg curLeg : directionForm.getTravelPath()) {
						Waypoint toWaypoint = curLeg.destination();
						AddressPlace toAddress = toWaypoint.findAddress(api);
						DirectionsForm.TravelMethod method = curLeg.method();
						GeoPoint startPos = fromAddress.geopoint;  // geopointfromDouble(fromAddress.getLatitude(), fromAddress.getLongitude());
						GeoPoint endPos   = toAddress.geopoint; // geopointfromDouble(toAddress.getLatitude(), toAddress.getLongitude());
							
						Drawable goal_draw = this.getResources().getDrawable(android.R.drawable.presence_online);
						CustomItemizedOverlay  goal = new CustomItemizedOverlay(goal_draw, this);
						OverlayItem o2 = new OverlayItem(endPos, "", toAddress.description);
						goal.addOverlay(o2);
						mapOverlays.add(goal);
						
						
						DrivingDirections dd = new DrivingDirectionsGoogleKML();
						dd.driveTo(startPos, endPos, curLeg.method().realMode(), this);
						mapController.animateTo(startPos);	
						fromAddress = toAddress;
					}				
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e){
					showDialog(NO_PATH_ID);					
				}
				

			}
		}
			
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	final int NO_PATH_ID = 0;
	
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
			d = builder.create();
		}
		return d;
	}
	
	protected GeoPoint geopointfromDouble(double latitude, double longitude){
		final int convert = 1000000;
		return new GeoPoint( ((int)(latitude * convert)) , ((int)(longitude * convert)) );
	}



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

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public int nextColor(){
		switch (currentColor) {
		case Color.RED:
			currentColor = Color.BLUE;
			break;
		case Color.BLUE:
			currentColor = Color.GREEN;
			break;
		case Color.GREEN:
			currentColor = Color.GRAY;
			break;
		default:
			currentColor = Color.RED;
		}
		return currentColor;
	}

	
}
