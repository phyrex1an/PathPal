package org.pathpal;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class GMapsActivity extends MapActivity implements IDirectionsListener {

	private MapView mapView;
	private Projection projection;
	private CustomItemizedOverlay itemizedOverlay;
	
	private static final int latitudeE6 = 37985339;
	private static final int longitudeE6= 23716735;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmapslayout);
		
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		
		projection = mapView.getProjection();
		
		
		GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);

		MapController mapController = mapView.getController();
		
		mapController.animateTo(point);
		
		
		DrivingDirections dd = new DrivingDirectionsGoogleKML();
		GeoPoint gp          = new GeoPoint(37985339, 23716735);
		GeoPoint gp2         = new GeoPoint(38036160, 23787610);
		
		dd.driveTo(gp, gp2, Mode.DRIVING, this);
		
		mapController.animateTo(point);
		mapController.setZoom(10);
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onDirectionsAvailable(Route route, Mode mode) {
		List<GeoPoint> gps = route.getGeoPoints();

		List<Overlay> mapOverlays = mapView.getOverlays();

		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		itemizedOverlay = new CustomItemizedOverlay(drawable, this);
		
		GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);
		OverlayItem overlayitem = new OverlayItem(point, "hello", gps.size()+"");
		
		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);
		
		mapOverlays.add(new PathOverlay(gps));
		
	}

	public void onDirectionsNotAvailable() {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}
	
	public class PathOverlay extends Overlay {
		
		private GeoPoint first;
		private List<GeoPoint> points;
		
	    public PathOverlay(List<GeoPoint> points) {
	    	this.points = new ArrayList<GeoPoint>(points);
	    	this.first = this.points.remove(0);
	    }

	    public void draw(Canvas canvas, MapView mapv, boolean shadow){
	    	
	        super.draw(canvas, mapv, shadow);
	        Paint mPaint = new Paint();
	        mPaint.setDither(true);
	        mPaint.setColor(Color.RED);
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(2);
	        
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
	
	// define the menu from the XML
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.gmapmenu, menu);
	    return true;
	}
	
	
	 static final int FIND_PATH_REQUEST_CODE = 0;
	 
	// method to handle a menu button action
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.exit:
	    	showDialog(DIALOG_EXIT_ID);
	        return true;
	    case R.id.find_path:
	    	Intent myIntent = new Intent(this, GMapsFindPathActivity.class);
	    	System.out.println("================================ HELLOO _======== ");
	    	startActivityForResult(myIntent, FIND_PATH_REQUEST_CODE);
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	
	// Called when an activity you launched exits, 
	//giving you the requestCode you started it with, 
	//the resultCode it returned, 
	//and any additional data from it
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FIND_PATH_REQUEST_CODE){
			Bundle d = data.getExtras();
			String from = d.get("from").toString();
			String to   = d.get("to").toString();
			
			// DO SOMETHING WITH THE RESULT FROM FIND_PATH
			
			System.out.println("====================");
			System.out.println(from + " " + to);
			System.out.println("====================");
		}
			
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	static final int DIALOG_EXIT_ID      = 1;
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog;
		switch(id){
		case DIALOG_EXIT_ID:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                GMapsActivity.this.finish();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			dialog = alert;
			break;
			default:
				dialog= null;
		}
		return dialog;
	}

}
