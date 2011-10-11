package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAccessorHelper extends SQLiteOpenHelper {
	
	// not sure why this is here, unused
//	private Context mContext;
	
	public static final String ROUTE_TABLE_NAME = "routes";
	public static final String DATABASE_NAME = "edappathon.sqlite";	
	public static final String MAKE_ROUTE_TABLE= 
			"create table" + ROUTE_TABLE_NAME + " ("  + 
			"street_name text not null)";
	
	public DataBaseAccessorHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MAKE_ROUTE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("drop table if exists " + ROUTE_TABLE_NAME);
		onCreate(db);
	}
			
				
	
	
	

}
