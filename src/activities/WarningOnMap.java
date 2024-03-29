package activities;

import java.util.ArrayList;
import java.util.List;

import utils.Controller;
import utils.MapItems;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

	Controller controller = Controller.getInstance();
	List<GeoPoint> locations = new ArrayList<GeoPoint>();
	
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
        
        locations = controller.getLocations();
        
        for (GeoPoint location : locations) {
        	overlayitem = new OverlayItem(location, "", "");
    		itemizedOverlay.addOverlay(overlayitem);
        }
        
        mapOverlays.add(itemizedOverlay);
    }
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

}
