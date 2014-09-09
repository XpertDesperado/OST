package tom.ost;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsMessage;

public class OSTNotificationManager {
	public static NotificationManager mNotificationManager;
	public static NotificationCompat.Builder mNotifyBuilder;
	private static int notifyID = 1776;//because America
	
	private OSTNotificationManager(){		
	}
		
	public static void notifyIncomingMessage(Context context, SmsMessage message) {
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// Sets an ID for the notification, so it can be updated
		Intent notificationIntent = new Intent(context, MessageActivity.class);
				notificationIntent.putExtra(MessageActivity.MESSAGE_ID_EXTRA, message.getOriginatingAddress());
				notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);				

				//TODO THIS SHIT DOESNT WORK AND ITS PISSING ME OFF
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addNextIntentWithParentStack(notificationIntent);
//		stackBuilder.addParentStack(MessageActivity.class);
//		stackBuilder.addNextIntent(notificationIntent);
		
		//PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pIntent = PendingIntent.getActivity(context ,0,notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		mNotifyBuilder = new NotificationCompat.Builder(context)
		    .setContentTitle("New Message")
		    .setContentText(message.getMessageBody())
		    .setSmallIcon(R.drawable.ic_launcher)
		    .setContentIntent(pIntent)
		    .setAutoCancel(true);
		//TODO update if there are multiple messages//currently shows the most recent
		
		// Because the ID remains unchanged, the existing notification is updated.
		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
		
	}
}
