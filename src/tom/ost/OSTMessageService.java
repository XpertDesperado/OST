package tom.ost;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class OSTMessageService extends Service {

	// private final static String TAG = "BroadcastService";

	public static final String MESSAGE_SERVICE = "your_package_name.countdown_br";
	Intent messageIntent = new Intent(MESSAGE_SERVICE);

	CountDownTimer countDownTimer = null;

	@Override
	public void onCreate() {
		super.onCreate();
		// Log.i(TAG, "Starting timer...");
		countDownTimer = new CountDownTimer(30000, 1000) {// (30 sec countdown,
															// 1 sec tics)
			@Override
			public void onTick(long millisUntilFinished) {
				// Log.i(TAG, "Countdown seconds remaining: " +
				// millisUntilFinished / 1000);
				messageIntent.putExtra("countdown", millisUntilFinished);
				sendBroadcast(messageIntent);
			}

			@Override
			public void onFinish() {
				// Log.i(TAG, "Timer finished");
			}
		};
		countDownTimer.start();
	}

	@Override
	public void onDestroy() {
		// TODO when does a service get destroyed
		countDownTimer.cancel();
		// Log.i(TAG, "Timer cancelled");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
