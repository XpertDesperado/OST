package tom.ost;

import java.util.ArrayList;
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

public class OSTApplication extends Application{
	private static OSTApplication singleton;
	private OSTDatabase db;
	
	public OSTApplication getInstance(){
		return singleton;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		singleton = this;
	//	db = new OSTDatabase(this);
	}
	
	public Conversation[] getConversations(){
		Conversation[] conversations = new Conversation[100];
		int length = conversations.length;
		for(int i = 0 ; i < length; i++){
			Conversation o = new Conversation("NAME " + i, "last message for " + i + " hope that the ellipses works");
			conversations[i] = o;
		}	
		return conversations;
	}
	
	public Message[] getMessages(String name){
		Random rand = new Random();
		String small = "small";
		String medium = "this is more of a medium size message";
		String large = "this is a large message repeated to fill 160 characters,   this is a large message repeated to fill 160 characters,   123";
		
		Message[] messages = new Message[100];
		int length = messages.length;
		for(int i = 0; i < length; i++){
			int r = rand.nextInt(3);
			String m; boolean b;
			if (i % 2 == 0) {
				m = "i = " + i + " --ME--";
				b = true;
			}else {
				m = "i = " + i + " --THEM--";
				b = false;
			}		
			if(r == 0)
				m += small;
			else if(r == 1)
				m += medium;
			else
				m += large;
			messages[i] = new Message(b, m);
		}
		return messages;
	}


	public void insertNewMessage(Message message, int cID) {
		//find user or create user if doesn't exist
		db.insertMessageInConversation(message, cID);
	}
	
	//reveived new message
	public void insertNewMessage(SmsMessage sms) {
		Message message = new Message(sms.getMessageBody(), sms.getOriginatingAddress());
		//find user id for it
		int uID = db.getUserID(message.getPhoneNumber());
		//create user id if user doesnt exist
		if( uID < 0){
			String name = getContact(this, message.getPhoneNumber());
			uID = db.insertNewUser(name, message.getPhoneNumber(), null);
		}
		int cID = db.getConversationID(uID);
		insertNewMessage(message, cID);
	
	}
	
	 //TODO: HACK FOR NOW move to more parallel role and decouple from here
		public String getContact(Context context, String number) {
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
			//default name is number
			return number;
		}
}
