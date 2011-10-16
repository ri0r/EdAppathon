package data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import utils.Location;
import utils.Property;
import utils.Routes;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAccessorHelper extends SQLiteOpenHelper {
	
	// not sure why this is here, unused
//	private Context mContext;
	public static final String DB_NAME = "tbdb.sqlite";	
	public static final String ROUTE_TABLE_NAME = "routes";
	public static final String ROADS_TABLE_NAME = "roads";
	public static final String USER_DATA_TABLE_NAME = "user_data";
	
	
//	public static final String MAKE_ROUTE_TABLE= 
//			"create table" + ROUTE_TABLE_NAME + " ("  + 
//			"street_name text not null)";
	
	private static String DB_PATH = "/data/data/batmanmustdie.src/databases/";
	private SQLiteDatabase db;
	private final Context myContext;
	
	public DataBaseAccessorHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(MAKE_ROUTE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("drop table if exists " + ROUTE_TABLE_NAME);
		onCreate(db);
	}
			
				
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
	
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}
	
	 private void copyDataBase() throws IOException{
		 
	    	//Open your local db as the input stream
	    	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	    	// Path to the just created empty db
	    	String outFileName = DB_PATH + DB_NAME;
	 
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	 
	    public void openDataBase() throws SQLException{
	 
	    	//Open the database
	        String myPath = DB_PATH + DB_NAME;
	    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    }
	 
	    @Override
		public synchronized void close() {
	 
	    	    if(db != null)
	    		    db.close();
	 
	    	    super.close();
	 
		}
	    
	    
	 	 
	        // Add your public helper methods to access and get content from the database.
	       // You could return cursors by doing "return db.query(....)" so it'd be easy
	       // to you to create adapters for your views.
	    
	    //TODO refactor using DatabaseUtils
	    
	    public void insertRoute(Routes r, Location l) {
	    	//TODO add logic to make sure the same route does not exist
	    	db.execSQL("insert into " + ROUTE_TABLE_NAME + "values("+ Integer.toString(r.getRouteID()) +","+  r.getStart() +","+ r.getEnd() +","+ l.getLocationName()
	    			+","+ l.getLatitude() +","+ l.getLongitude() +");");
	    }
	    
	    public void insertRoad(int routeID, String roadName, String lat, String lon) {
	    	db.execSQL("insert into " + ROADS_TABLE_NAME + "values(" 
	    			+ routeID +  "," 
	    			+ roadName  + "," 
	    			+ lat +  "," 
	    			+ lon + " );");
	    }
	    
	    public void insertRoad(Location loc) {
	    	int temp = 0;
	    	if (loc.getRouteID() != 0 ) {
	    		temp = loc.getRouteID();
	    	}
	    	
	    	db.execSQL("insert into " + ROADS_TABLE_NAME + "values("
	    			+ temp +  "," 
	    			+ loc.getLocationName()  + "," 
	    			+ Double.toString(loc.getLatitude()) +  "," 
	    			+ Double.toString(loc.getLongitude()) +	");");
	    }
	    
	    public List<Location> getAllLocations() {
	    	List<Location> locs = new ArrayList<Location>();
	    	
	    	String query = "select * from " + ROADS_TABLE_NAME + ";";
	    	Cursor c = db.query(true, ROADS_TABLE_NAME, null, query, null, null, null, null, null);
	    	
	    	if (c.isBeforeFirst()) {
	    		while (c.moveToNext()) {
	    			
	    		}
	    	}
	    	
	    	return locs;
	    }
	    
	    public List<Location> getLocationsByRouteID(int routeID) {
	    	return null;
	    }
	    
	    public void insertUserInformation(String property, String value) {

	    }
	    public void insertUserInformation(Property prop) {

	    }
	    
	    public String getUserInfoByProperty(String property) {
	    	return null;
	    }
	    
	    public List<Property> getAllUserProperty() {
	    	return null;
	    }
	    
//	    public void insertRoad(String street) {
//			db.execSQL("insert into " + ROUTE_TABLE_NAME + "values(" + street + ");");
//		}
//		
//		public void insertLocations(List<Location> edLocs) {
//			for (Location edLoc : edLocs) {
//				insertRoad(edLoc.toString());
//			}
//		}
//		
//		public List<Location> getAllLocations() {
//			 List<Location> edlocs = new ArrayList<Location>();
//			 Cursor c = db.query(ROUTE_TABLE_NAME, new String[] {"street_name" }, null, null, null, null, null);
//			 for (int i = 0; i < c.getCount(); i++) {
//				 edlocs.add(new Location(c.getString(0)));
//			 }
//			 return edlocs;
//		}
//		
//		public void removeStreet(Location street) {
//			db.execSQL("delete from " + ROUTE_TABLE_NAME + " where street_name=" + street.toString());
//		}
	    
	 
}
