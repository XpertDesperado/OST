package tom.ost;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class OSTApplication extends Application {
	private static OSTApplication singleton;
	private OSTDatabase db;

	public OSTApplication getInstance() {
		return singleton;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		db = new OSTDatabase(this);
	}

	public Conversation[] getConversations() {
System.out.println("GET CONVERSATIONS");
		Cursor cur = db.getConversations();
		ArrayList<Conversation> conversations = new ArrayList<Conversation>();		
		if(cur.moveToFirst()){
//System.out.println("move to first");
			int column = 0;
			while(!cur.isAfterLast()){
//System.out.print("conversation, ");
				//Conversations.C_ID, Messages.Body, Messages.Date, Users.Name, Users.Number, Users.Image
				column = cur.getColumnIndex("Name");
				String name = cur.getString(column);
//System.out.print(" name " + name);
				column = cur.getColumnIndex("Body");
				String lastMessage = cur.getString(column);
//System.out.print(" body " + lastMessage);
				column = cur.getColumnIndex("Date");
				String lastMessageDate = cur.getString(column);
//System.out.print(" date" + lastMessageDate);
				column = cur.getColumnIndex("C_ID");
				int cID = cur.getInt(column);
//System.out.print(" cid " + cID);
				column = cur.getColumnIndex("Image");
				byte[] image = cur.getBlob(column);
//System.out.println(" img "+ image);
				conversations.add(new Conversation(name, lastMessage, cID, lastMessageDate, image));
				cur.moveToNext();
			}			
		}
System.out.println("end get conversations");
		return conversations.toArray(new Conversation[conversations.size()]);
	}
//get cid before notification
	public ArrayList<Message> getMessages(int cid, int status) {
System.out.println("GET MESSAGES");
		Cursor cur = db.getMessages(cid, status);
		ArrayList<Message> messages = new ArrayList<Message>();
		if(cur.moveToFirst()){
			int column;
			boolean isMe;
			while(!cur.isAfterLast()){
				//U_ID, Date, Body
				column = cur.getColumnIndex("Body");
				String message = cur.getString(column);
				column = cur.getColumnIndex("Date");
				
				String date = cur.getString(column);
//				DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				try {
//				date = (Date) iso8601Format.parse(cur.getString(column));
//				} catch (ParseException e) {
//					date = new Date(0);
//				Log.e("POO", "Parsing ISO8601 datetime failed", e);
//				}
				column = cur.getColumnIndex("U_ID");
				int uID = cur.getInt(column);
				column = cur.getColumnIndex("Status");
				int m_status = cur.getInt(column);
				isMe = uID > 0;					
				//NOTE Message(boolean isMe, String message, String phone, String date, int senderUID, int status)
				messages.add(new Message(isMe, message, date, uID, m_status));
				
				cur.moveToNext();
			}
		}
		//return messages.toArray(new Message[messages.size()]);
		return messages;
	}

	// reveived new message
	//returns cid.... maybe should return the mid to be more intuitive
	public int insertNewMessage(Message message) {
		// find user id for it
		int uID = db.getUserID(message.getPhoneNumber());
		Toast.makeText(this, "insert, "+message.getBody(), Toast.LENGTH_LONG).show();
		// create user id if user doesnt exist
		if (uID < 0) {
			String name = getContact(this, message.getPhoneNumber());
			uID = db.insertNewUser(name, message.getPhoneNumber(), null);
			Toast.makeText(this, "uID < 0, "+uID, Toast.LENGTH_LONG).show();
		}
		int cID = db.getConversationID(uID);
		Toast.makeText(this, "uid= "+uID+", cid= "+cID, Toast.LENGTH_LONG).show();
		db.insertMessageInConversation(message, cID);
		return cID;

	}

	// TODO: HACK FOR NOW move to more parallel role and decouple from here
	public static String getContact(Context context, String number) {
		// / number is the phone number
		Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(number));
		String[] mPhoneNumberProjection = { PhoneLookup._ID,
				PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME };
		Cursor cur = context.getContentResolver().query(lookupUri,
				mPhoneNumberProjection, null, null, null);
		try {
			if (cur.moveToFirst()) {
				int column = cur.getColumnIndex(Phone.DISPLAY_NAME);
				return cur.getString(column);
			}
		} finally {
			if (cur != null)
				cur.close();
		}
		// default name is number
		return number;
	}
}
