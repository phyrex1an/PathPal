package org.pathpal;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

class MyLocationOverlay extends com.google.android.maps.Overlay {
	
	private GeoPoint p;
	public MyLocationOverlay(GeoPoint p) {
		this.p = p;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

	super.draw(canvas, mapView, shadow);
	Paint paint = new Paint();
	// Converts lat/lng-Point to OUR coordinates on the screen.
	Point myScreenCoords = new Point();
	mapView.getProjection().toPixels(p, myScreenCoords);
	paint.setStrokeWidth(1);
	paint.setARGB(255, 255, 255, 255);
	paint.setStyle(Paint.Style.STROKE);
	Bitmap bmp = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.icon);
	canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
	canvas.drawText("YOU", myScreenCoords.x, myScreenCoords.y, paint);


	}

	public GeoPoint getGeoPoint() {
		// TODO Auto-generated method stub
		return p;
	} 
} 