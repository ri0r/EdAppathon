package activities;

import java.util.List;

import utils.MapItems;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import batmanmustdie.src.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class WarningOnMap extends MapActivity {
	
	private final String KEY = "00SUO8awrOPRlS6ZpZ6mbx1g9SkWY4gdopdrPiQ";
	private final String TAG = "WarningOnMap";
	LinearLayout linearLayout;
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MapItems itemizedOverlay;
	OverlayItem overlayitem;
	double[] lat;
	double[] lon;
	String[] msg;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.warning);
        itemizedOverlay = new MapItems(drawable);        
        
        Bundle bundle = this.getIntent().getExtras();
    	if (bundle != null) {
    		lat = bundle.getDoubleArray("lat");
    		lon = bundle.getDoubleArray("lon");
    		msg = bundle.getStringArray("msg");
    		
    		GeoPoint point;
    		Log.d(TAG, "recieved "+ lat.length + "elements");
    		for(int i=0;i<lat.length;i++) {
    			point = new GeoPoint((int) (lat[i] * 1E6), (int) (lon[i] * 1E6));
    			overlayitem = new OverlayItem(point, "", msg[i]);
    			itemizedOverlay.addOverlay(overlayitem);
    			Log.d(TAG, "added item: " + i);
    		}   		
    	}                
        mapOverlays.add(itemizedOverlay);
    }
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

}
