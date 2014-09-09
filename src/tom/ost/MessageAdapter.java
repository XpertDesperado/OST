package tom.ost;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<Message> {
	private final Context context;
	private final Message[] messages;
	private final static int itemLayout_me = R.layout.message_view_item_me;
	private final static int itemLayout_them = R.layout.message_view_item_them;

	// allows fast loading of listview	
	// TODO check that it actually is faster
	static class ViewHolder {
		public boolean isMe;
		public TextView message;
		public TextView date;
		// public ImageView image;
	}

	public MessageAdapter(Context context,	Message[] messages) {
		super(context, itemLayout_me, messages);
		this.context = context;
		this.messages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		Message currentMessage = messages[position];

		// reuse views
		if (rowView == null || ((ViewHolder)rowView.getTag()).isMe != currentMessage.isMe()) {
			ViewHolder viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			if(currentMessage.isMe()){
				rowView = inflater.inflate(this.itemLayout_me, null);
				viewHolder.isMe = true;
			}
			else{
				rowView = inflater.inflate(this.itemLayout_them, null);
				viewHolder.isMe = false;
			}
			// configure view holder
			viewHolder.message = (TextView) rowView.findViewById(R.id.MessageItemBody);
			viewHolder.date = (TextView) rowView.findViewById(R.id.MessageItemDate);
			// viewHolder.image = (ImageView) rowView.findViewById(R.id.);

			// TODO find out about this part
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.message.setText(currentMessage.getBody());
		holder.date.setText(currentMessage.getDate().toString());
		// holder.image.setImageResource(R.drawable.ok);

		return rowView;
	}

}
