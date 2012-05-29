package umf.research.angler;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class MapDraw extends MapActivity {
	
	LinearLayout linearLayout;
	MapView mapView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//GET AND CREATE GEOPOINT
		Bundle extras = getIntent().getExtras();
		float latitude = extras.getFloat("lat")*1000000;
		float longitude = extras.getFloat("lon")*1000000;
		GeoPoint geoPoint = new GeoPoint((int)latitude, (int)longitude);

		Toast.makeText(this, "lat:" + geoPoint.getLatitudeE6(), 2000).show();

		//MAP STUFF
		setContentView(R.layout.map_page);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		MapController mapController = mapView.getController();
		mapController.setZoom(17);
		mapController.animateTo(geoPoint);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
