package tom.ost;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConversationAdapter extends ArrayAdapter<Conversation>{
	private final Context context;
	private final Conversation[] conversations;
	private final int itemLayout = R.layout.conversation_view_item;
	
	//allows fast loading of listview
	//TODO check that it actually is faster
	static class ViewHolder{
		public TextView name;
		public TextView lastMessage;
	//	public ImageView image;
	}
	
	public ConversationAdapter(Context context, Conversation[] conversations) {
		super(context, R.layout.conversation_view_item, conversations);
		this.context = context;
		this.conversations = conversations;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	    View rowView = convertView;
	    // reuse views
	    if (rowView == null) {
	      LayoutInflater inflater = LayoutInflater.from(context);
	      rowView = inflater.inflate(this.itemLayout, null);
	      // configure view holder
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.name = (TextView) rowView.findViewById(R.id.ConversationNameTextView);
	      viewHolder.lastMessage = (TextView) rowView.findViewById(R.id.ConversationLastMessageTextView);
	     // viewHolder.image = (ImageView) rowView.findViewById(R.id.);
	      
	    //TODO find out about this part
	      rowView.setTag(viewHolder);
	    }

	    // fill data
	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    Conversation s = conversations[position];
	    holder.name.setText(s.getName());
	    holder.lastMessage.setText(s.getLastMessage());
	    //holder.image.setImageResource(R.drawable.ok);

	    return rowView;
	  }

}
