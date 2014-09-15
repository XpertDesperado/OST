package tom.ost;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<Message> {
	private final Context context;
	private final Message[] messages;
	private final static int itemLayout_me = R.layout.message_view_item_me;
	private final static int itemLayout_them = R.layout.message_view_item_them;

	// allows fast loading of listview
	static class ViewHolder {
		public boolean isMe;
		public TextView message;
		public TextView date;
		public ImageButton image;
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
				rowView = inflater.inflate(MessageAdapter.itemLayout_me, null);
				viewHolder.isMe = true;
				viewHolder.image = (ImageButton)rowView.findViewById(R.id.OSTButton);
				if(currentMessage.getStatus() == Message.STATUS_OST)
					viewHolder.image.setVisibility(ImageButton.VISIBLE);
				else//TODO put image for post OST time
					viewHolder.image.setVisibility(ImageButton.GONE);
			}
			else{
				rowView = inflater.inflate(MessageAdapter.itemLayout_them, null);
				viewHolder.isMe = false;
				viewHolder.image = null;
			}
			// configure view holder
			viewHolder.message = (TextView) rowView.findViewById(R.id.MessageItemBody);
			viewHolder.date = (TextView) rowView.findViewById(R.id.MessageItemDate);
			// viewHolder.image = (ImageView) rowView.findViewById(R.id.);

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
