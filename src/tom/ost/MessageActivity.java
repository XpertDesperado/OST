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

import tom.ost.MessageAdapter.ViewHolder;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//individual message activity
public class MessageActivity extends Activity{
	public static String CONVERSATION_NAME_EXTRA = "tom.ost.MessageActivity.CONVERSATION_NAME_EXTRA_";
	public static String CONVERSATION_ID_EXTRA = "tom.ost.MessageActivity.CONVERSATION_ID_EXTRA";
	private final static int PICK_CONTACT_REQUEST = 1;
	
	private String name = "New Message";
	private boolean isNewMessage;
	private static MessageAdapter messageAdapter;
	private ArrayList<Message> messages;
	private EditText MessageAddressEditText;
	private EditText MessageBodyEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.message_view);
		
		messages = new ArrayList<Message>();
		
		this.isNewMessage = this.getIntent().getBooleanExtra("new", false);
		if(!this.isNewMessage){
			name = this.getIntent().getStringExtra(CONVERSATION_NAME_EXTRA);
			int cid = this.getIntent().getIntExtra(CONVERSATION_ID_EXTRA, -1);
			//remove new message functionality
			Button contacts = (Button) this.findViewById(R.id.MessageSearchContactsButton);			
			EditText sendTo = (EditText) this.findViewById(R.id.MessageAddressEditText);
			contacts.setVisibility(View.GONE);
			sendTo.setVisibility(View.GONE);
			//load messages and show
			if(cid < 0){
				Toast.makeText(this, "couldn't load messages", Toast.LENGTH_LONG).show();
				//finish();
			}
			else{
				//get registered messages 
				messages = ((OSTApplication) this.getApplicationContext()).getInstance().getMessages(cid, Message.STATUS_NONE);
				//get messages not sent yet so user can still undo
				messages.addAll(((OSTApplication) this.getApplicationContext()).getInstance().getMessages(cid, Message.STATUS_OST));
				ListView messagesListView = (ListView) this.findViewById(R.id.MessageListView);
				messageAdapter = new MessageAdapter(this, messages.toArray(new Message[messages.size()]));
				messagesListView.setAdapter(messageAdapter);
			}
		}
		this.setTitle(name);
		MessageAddressEditText = ((EditText)this.findViewById(R.id.MessageAddressEditText));
		MessageBodyEditText = ((EditText)this.findViewById(R.id.MessageBodyEditText));
	}
	
//	@Override
//	protected void onResume(){
//		
//	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_CONTACT_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	            // The user picked a contact.
	        	Uri contactUri = data.getData();
	            String[] projection = {Phone.NORMALIZED_NUMBER, Phone.NUMBER, Phone.PHOTO_THUMBNAIL_URI, Phone.DISPLAY_NAME_PRIMARY};
	            // TODO: CAUTION: The query() method should be called from a separate thread to avoid blocking
	            // the UI thread. Consider using CursorLoader to perform the query.
	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();
	            // Retrieve the phone number from the NUMBER column
	            int column = cursor.getColumnIndex(Phone.NORMALIZED_NUMBER);//+11234567890
	            String nnumber = cursor.getString(column);
	            column = cursor.getColumnIndex(Phone.NUMBER);//(123) 456 7890 or other ways
	            String number = cursor.getString(column);
	            column = cursor.getColumnIndex(Phone.DISPLAY_NAME_PRIMARY);
	            String label = cursor.getString(column);
	            column = cursor.getColumnIndex(Phone.PHOTO_THUMBNAIL_URI);
	            String tnURI = cursor.getString(column);
	            Log.e("POOP", "label: "+label+"number: "+number+"tnURI: "+tnURI);
	            
	            MessageAddressEditText.setText(nnumber);
	            
	            cursor.close();
	        }
	    }
	}

	//HACK because stackbuilder is being a bitch
	//TODO: fix the hack
	@Override
	public void onBackPressed(){
		Intent thisIsBullshit = new Intent(this, MainActivity.class);
		thisIsBullshit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(thisIsBullshit);
		finish();
	}
	
	
	public void onSendButtonClick(View v){
		String body = MessageBodyEditText.getText().toString();
		String address = MessageAddressEditText.getText().toString();
		Toast.makeText(this, "SEND", Toast.LENGTH_LONG).show();
		
		//add message to list
		//refresh list
		//start OST timer
		//send message if timer not cancelled
		//insert in DB
		//refresh list
		
	//	SmsManager smsManager = SmsManager.getDefault();
	//	smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	}
	
	public void onContactsButtonClick(View v){
		Intent getContactsIntent = new Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		getContactsIntent.setType(Phone.CONTENT_TYPE);
		startActivityForResult(getContactsIntent, PICK_CONTACT_REQUEST);
	}
	
	public void OST(View v){
		Toast.makeText(this, "OST", Toast.LENGTH_LONG).show();
		//cancel send
		//remove from object list
	}
	
	
	

}
