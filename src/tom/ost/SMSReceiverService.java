package tom.ost;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;

public class SMSReceiverService extends Service{
	private final String TAG = this.getClass().getSimpleName();
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	
	
	@Override
	public void onCreate() {
		// Start up the thread running the service. Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block.
		HandlerThread thread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
		//mServiceHandler.handleMessage(msg);
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private final class ServiceHandler extends Handler{
		
		public ServiceHandler (Looper looper){
			super(looper);
		}
		
//		public void handleMessage(Message msg) {
//            int serviceId = msg.arg1;
//            Intent intent = (Intent)msg.obj;
//            if (intent != null) {
//                String action = intent.getAction();
//
//                int error = intent.getIntExtra("errorCode", 0);
//
//                if (MESSAGE_SENT_ACTION.equals(intent.getAction())) {
//                    handleSmsSent(intent, error);
//                } else if (SMS_RECEIVED_ACTION.equals(action)) {
//                    handleSmsReceived(intent, error);
//                } else if (ACTION_BOOT_COMPLETED.equals(action)) {
//                    handleBootCompleted();
//                } else if (TelephonyIntents.ACTION_SERVICE_STATE_CHANGED.equals(action)) {
//                    handleServiceStateChanged(intent);
//                } else if (ACTION_SEND_MESSAGE.endsWith(action)) {
//                    handleSendMessage();
//                }
//            }
//            SmsReceiver.finishStartingService(SmsReceiverService.this, serviceId);
//        }
		
	}

//	public void handleSmsSent(Intent intent, int error) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	public void handleSendMessage() {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	public void handleServiceStateChanged(Intent intent) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	public void handleBootCompleted() {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	public void handleSmsReceived(Intent intent, int error) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	
}
