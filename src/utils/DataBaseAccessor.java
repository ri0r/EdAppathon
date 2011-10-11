package utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
		db.execSQL("insert into " + DataBaseAccessorHelper.ROUTE_TABLE_NAME + "values(" + street + ");");
	}
	
	public void insertLocations(List<Location> edLocs) {
		for (Location edLoc : edLocs) {
			insertStreet(edLoc.toString());
		}
	}
	
	public List<Location> getAllLocations() {
		 List<Location> edlocs = new ArrayList<Location>();
		 Cursor c = db.query(DataBaseAccessorHelper.MAKE_ROUTE_TABLE, new String[] {"street_name" }, null, null, null, null, null);
		 for (int i = 0; i < c.getCount(); i++) {
			 edlocs.add(new Location(c.getString(0)));
		 }
		 return edlocs;
	}
	
	public void removeStreet(Location street) {
		db.execSQL("delete from " + DataBaseAccessorHelper.ROUTE_TABLE_NAME + " where street_name=" + street.toString());
	}

}
