package tom.ost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsMessage;
import android.util.Log;

public class OSTDatabase extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "tom.ost.database";
	private final String[] TABLES = {"Users", "Conversations", "UsersInConversation", "Messages", "MessagesInConversation"};
	
	private String GetMessagesSQL = "SELECT Messages.* FROM MessagesInConversation, Messages " +
			"WHERE MessagesInConversation.C_ID = %d AND Messages.M_ID = MessagesInConversation.M_ID " +
			"AND Messages.Status = %d ORDER BY Messages.Date ASC";
	private String GetConversationsSQL = "SELECT Conversations.C_ID, Messages.Body, Messages.Date, Users.Name, Users.Number, Users.Image" +
			"			FROM Users, Conversations, UsersInConversation, Messages " +
			"			WHERE UsersInConversation.U_ID = Users.U_ID" +
			"			AND Messages.M_ID = Conversations.M_ID" +
			"			AND Conversations.C_ID = UsersInConversation.C_ID" +
			"			ORDER BY Messages.M_ID DESC";
	private static String CREATE_CONVERSATIONS_TABLE_SQL = 
				"CREATE TABLE `Conversations` (" +
				"	`C_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"	`M_ID`	INTEGER" +
				"	);";
	private static String CREATE_MESSAGES_TABLE_SQL =
				"					CREATE TABLE `Messages` (" +
				"						`M_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"						`U_ID`	INTEGER NOT NULL," +
				"						`Date`	DATETIME default CURRENT_DATE," +
				"						`Body`	TEXT NOT NULL," +
				"						`Status` TEXT DEFAULT '0'" +
				"					);";
	private static String CREATE_MESSAGESINCONVERSATION_TABLE_SQL =
				"					CREATE TABLE `MessagesInConversation` (" +
				"						`M_ID`	INTEGER NOT NULL," +
				"						`C_ID`	INTEGER NOT NULL" +
				"					);";
	private static String CREATE_USERS_TABLE_SQL =
				"					CREATE TABLE `Users` (" +
				"						`U_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"						`Name`	TEXT NOT NULL," +
				"						`Number`	TEXT NOT NULL UNIQUE," +
				"						`Image`	BLOB" +
				"					);";
	private static String CREATE_USERSINCONVERSATION_TABLE_SQL =
				"					CREATE TABLE `UsersInConversation` (" +
				"						`U_ID`	INTEGER NOT NULL," +
				"						`C_ID`	INTEGER NOT NULL" +
				"					);";
	
    public OSTDatabase(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		//TODO: use transactions to speed up
		db.execSQL(CREATE_USERS_TABLE_SQL);
		db.execSQL(CREATE_CONVERSATIONS_TABLE_SQL);
		db.execSQL(CREATE_MESSAGES_TABLE_SQL);
		db.execSQL(CREATE_USERSINCONVERSATION_TABLE_SQL);
		db.execSQL(CREATE_MESSAGESINCONVERSATION_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("POO", "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		for(String table : TABLES){
			db.execSQL("DROP TABLE IF EXISTS " + table);
		}
		onCreate(db);		
	}	
	
	public long insert(String table, ContentValues values){
		//dont let insert with empty values
		if(values.size() > 0)
			return this.getWritableDatabase().insert(table, null, values);
		else return -1;
	}
	
	public long update(String table, ContentValues values, String whereClause){
		return this.getWritableDatabase().update(table, values, whereClause, null);
	}
	
	public Cursor getConversations(){
		return this.getReadableDatabase().rawQuery(this.GetConversationsSQL, null);
	}
	public Cursor getMessages(int cID, int status){
		String query = String.format(GetMessagesSQL, cID, status);
		return this.getReadableDatabase().rawQuery(query, null);		
	}
	


	//returns new conversation id for the user if doesnt exist
//3
	public int getConversationID(int uID) {
		Cursor c = this.getReadableDatabase().rawQuery("SELECT UsersInConversation.C_ID FROM UsersInConversation WHERE UsersInConversation.U_ID="+uID, null);
		if(c.moveToFirst()){
			Log.e("POO", "returning" + c.getInt(0));
			if (c.getInt(0) >= 0)
				return c.getInt(0);
		}
		
		//insert new conversation and user in conversation
		ContentValues cv = new ContentValues();
		cv.putNull("M_ID");//last message right now is null, TODO: may swtich flow to make message first then insert it
		int cID = (int)insert("Conversations", cv);
		Log.e("POO", "stuck with this shit" + cID);		
		//shouldn't have an empty conversation
		cv = new ContentValues();
		cv.put("U_ID", uID);
		cv.put("C_ID", cID);
		//assume works
		insert("UsersInConversation", cv);		
		return cID;
	}
//2
	public int insertNewUser(String name, String phone, byte[] image) {
		ContentValues cv = new ContentValues();
		cv.put("Name", name);
		cv.put("Number", phone);
		cv.put("Image", image);
		return (int)insert("Users", cv);
	}

// 1
	public int getUserID(String phoneNumber) {
		Cursor c = this.getReadableDatabase().rawQuery("SELECT Users.U_ID FROM Users WHERE Users.Number="+phoneNumber, null);
		if(c.moveToFirst()){
			return c.getInt(0);
		}
		return -1;
	}
//4
	public boolean insertMessageInConversation(Message message, int cID) {
		//insert message
		ContentValues cv = new ContentValues();
		cv.put("Body", message.getBody());
		cv.put("U_ID", message.getSenderUID());
//		cv.put("Date", "datetime()");TODO
		int mID = (int)insert("Messages", cv);
		//put message in conversation
		//assume works
		cv = new ContentValues();
		cv.put("M_ID", mID);
		cv.put("C_ID", cID);
		insert("MessagesInConversation", cv);
		//update conversation
		cv = new ContentValues();
		cv.put("M_ID", mID);
		this.update("Conversations", cv, "Conversations.C_ID="+cID);
		return false;
	}

}
