package tom.ost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsMessage;
import android.util.Log;

public class OSTDatabase extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "tom.ost.database";
	private final String[] TABLES = {"Users", "Conversations", "UsersInConversation", "Messages", "MessagesInConversation"};
	
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
		Log.e("poop", "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		for(String table : TABLES){
			db.execSQL("DROP TABLE IF EXISTS " + table);
		}
		onCreate(db);		
	}
	
//	public boolean handleNewMessage(Message m){		
//		int uID = m.getSenderUID();
//		ContentValues cv = new ContentValues();
//		cv.put("Body", m.getBody());
//		cv.put("Date", m.getDate().toString());
//		cv.put("U_ID", uID);
//		long mID = insert("Messages", cv);		
//		//message received
//		if(uID > 0){
////			long uID
//		}		
//		return true;
//	}
	
	
	
	public long insert(String table, ContentValues values){
		//dont let insert with empty values
		if(values.size() > 0)
			return this.getWritableDatabase().insert(table, null, values);
		else return -1;
	}
	
	//generally for conversations
	public long update(String table, ContentValues values, String whereClause){
		return this.getWritableDatabase().update(table, values, whereClause, null);
	}
	
	public Cursor getConversations(){
		return this.getReadableDatabase().rawQuery(this.GetConversationsSQL, null);
	}
	public Cursor getMessages(int conversationID){
		String query = String.format(GetMessagesSQL, conversationID);
		return this.getReadableDatabase().rawQuery(query, null);		
	}
	
	private String GetMessagesSQL = "SELECT Messages.* FROM MessagesInConversation, Messages " +
			"WHERE MessagesInConversation.C_ID = %d AND Messages.M_ID = MessagesInConversation.M_ID " +
			"ORDER BY Messages.M_ID ASC";
	
	private String GetConversationsSQL = "SELECT Conversations.C_ID, Messages.Body, Messages.Date, Users.Name, Users.Number, Users.Image" +
			"			FROM Users, Conversations, UsersInConversation, Messages " +
			"			WHERE UsersInConversation.U_ID = Users.U_ID" +
			"			AND Messages.M_ID = Conversations.M_ID" +
			"			AND Conversations.C_ID = UsersInConversation.C_ID" +
			"			ORDER BY Messages.M_ID DESC";
	
	
	private static String CREATE_CONVERSATIONS_TABLE_SQL = 
				"CREATE TABLE `Conversations` (" +
				"	`C_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"						`M_ID`	INTEGER NOT NULL" +
				"					);";
	private static String CREATE_MESSAGES_TABLE_SQL =
				"					CREATE TABLE `Messages` (" +
				"						`M_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"						`U_ID`	INTEGER NOT NULL," +
				"						`Date`	TEXT NOT NULL," +
				"						`Body`	TEXT NOT NULL" +
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

	//returns new conversation id for the user if doesnt exist
//3
	public int getConversationID(int uID) {
		
		return 0;
	}
//2
	public int insertNewUser(String name, String string, Object image) {
		// TODO Auto-generated method stub
		return 0;
	}
//1
	public int getUserID(String phoneNumber) {
		// TODO Auto-generated method stub
		int uID = 0;//check if user exists
		
		return 0;
	}

	public void insertMessageInConversation(Message message, int cID) {
		// TODO Auto-generated method stub
		
	}

}
