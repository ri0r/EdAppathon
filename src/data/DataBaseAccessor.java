package data;

import java.util.ArrayList;
import java.util.List;

import utils.Location;


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
	
	

}
