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
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
System.out.println("START");
		Conversation[] objects = (Conversation[])((OSTApplication) this.getApplicationContext()).getInstance().getConversations();
		
		MainActivity.conversationAdapter = new ConversationAdapter(this, objects);
		
		//footer stuff
		View footerView = this.getLayoutInflater().inflate(R.layout.conversation_footer, null);
        TextView footerTextView = (TextView) footerView.findViewById(R.id.ConversationFooterTextView);
        footerTextView.setText(String.format("You have %d conversations", objects.length));
		this.getListView().addFooterView(footerView);
		
		this.setListAdapter(conversationAdapter);
		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    // open message activity for that conversation
		Conversation item = (Conversation) getListAdapter().getItem(position);
		Intent messageIntent = new Intent(this, MessageActivity.class);
		messageIntent.putExtra(MessageActivity.CONVERSATION_NAME_EXTRA, item.getName());
		messageIntent.putExtra(MessageActivity.CONVERSATION_ID_EXTRA, item.getCID());
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


}
