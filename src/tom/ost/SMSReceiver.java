package tom.ost;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver{
    private static SMSReceiver sInstance;
 
	public static SMSReceiver getInstance() {
		if (sInstance == null) {
			sInstance = new SMSReceiver();
		}
		return sInstance;
	}    
    
    @Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras == null)
			return;		
		Object[] smsextras = (Object[]) extras.get("pdus");
		for (int i = 0; i < smsextras.length; i++) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) smsextras[i]);
			//add to db
			Message newMessage = new Message(message.getMessageBody(), message.getOriginatingAddress());
			newMessage.setStatus(Message.STATUS_NONE);
System.out.println("RECEIVED MESSAGE" + newMessage.getBody());
			int cID = ((OSTApplication) context.getApplicationContext()).getInstance().insertNewMessage(newMessage);
			//notify user
			OSTNotificationManager.notifyIncomingMessage(context, message, cID);
		}
    }

   

	

}
