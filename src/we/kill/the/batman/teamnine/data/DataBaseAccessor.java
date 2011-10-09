package we.kill.the.batman.teamnine.data;

import java.util.ArrayList;
import java.util.List;

import we.kill.the.batman.teamnine.EdLocation;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;




public class DataBaseAccessor {
	
	private static DataBaseAccessor dba;
	private final Context context;
	private final DataBaseAccessorHelper dbah;
	private SQLiteDatabase db;
	
	private DataBaseAccessor(Context c) {
		this.context = c;
		dbah = new DataBaseAccessorHelper(context);
		open();
	}
	
	public static DataBaseAccessor getInstance(Context c) {
		if( null == dba )
			dba = new DataBaseAccessor( c );
		return dba;
	}
	
	public void open() {
		db = dbah.getWritableDatabase();	
	}
	
	public void close() {
		db.close();
	}
	
	public void insertStreet(String street) {
		db.execSQL("insert into " + dbah.ROUTE_TABLE_NAME + "values(" + street + ");");
	}
	
	public void insertLocations(List<EdLocation> edLocs) {
		for (EdLocation edLoc : edLocs) {
			insertStreet(edLoc.toString());
		}
	}
	
	public List<EdLocation> getAllLocations() {
		 List<EdLocation> edlocs = new ArrayList<EdLocation>();
		 Cursor c = db.query(dbah.MAKE_ROUTE_TABLE, new String[] {"street_name" }, null, null, null, null, null);
		 for (int i = 0; i < c.getCount(); i++) {
			 edlocs.add(new EdLocation(c.getString(0)));
		 }
		 return edlocs;
	}
	
	public void removeStreet(Location street) {
		db.execSQL("delete from " + dbah.ROUTE_TABLE_NAME + " where street_name=" + street.toString());
	}

}
