package tom.ost;

import java.util.ArrayList;
import java.util.List;
//
//import com.twilio.sdk.TwilioRestClient;
//import com.twilio.sdk.TwilioRestException;
//import com.twilio.sdk.resource.factory.MessageFactory;
//import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//user sees current conversations with people
//Conversation Activity
public class MainActivity extends ListActivity {
	private static ConversationAdapter conversationAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Conversation[] objects = ((OSTApplication) this.getApplicationContext()).getInstance().getConversations();
		
		MainActivity.conversationAdapter = new ConversationAdapter(this, objects);
		this.setListAdapter(conversationAdapter);		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    // open message activity for that conversation
		Conversation item = (Conversation) getListAdapter().getItem(position);
		Intent messageIntent = new Intent(this, MessageActivity.class);
		messageIntent.putExtra(MessageActivity.MESSAGE_ID_EXTRA, item.getName());
		this.startActivity(messageIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ComposeNewMessageMenuItem:
			Intent newMessageIntent = new Intent(this, MessageActivity.class);
			newMessageIntent.putExtra("new", true);
			this.startActivity(newMessageIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

//	private void validateUser() {
//		// from (240) 839-6322 //twillio number
//		String ACCOUNT_SID = "AC45b00a5504e242b8a486ebf4cad405c9";
//		String AUTH_TOKEN = "14f81f0216edcb78de9f99371eaa62aa";
//		// var twilio = new TwilioRestClient(AccountSid, AuthToken);
//		try {
//			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID,
//					AUTH_TOKEN);
//
//			// Build a filter for the MessageList
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("Body", "message from OST"));
//			params.add(new BasicNameValuePair("To", "+13018018683"));
//			params.add(new BasicNameValuePair("From", "+12408396322"));
//
//			MessageFactory messageFactory = client.getAccount()
//					.getMessageFactory();
//			Message message;
//			message = messageFactory.create(params);
//			Button tv = (Button) this.findViewById(R.id.test_view);
//			tv.setText(message.getSid());
//			System.out.println(message.getSid());
//		} catch (TwilioRestException e ) {
//			// TODO Auto-generated catch block
//			TextView tv = (TextView) this.findViewById(R.id.test_view);
//			tv.setText(e.getErrorMessage());
//			System.out.print(e.getErrorMessage());
//		}
//
//	}

}
