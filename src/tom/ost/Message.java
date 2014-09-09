package tom.ost;

import java.util.Calendar;
import java.util.Date;

public class Message {
	private boolean isMe;
	private String body;
	private Date date;
	private Object image = null;
	private int senderUID;
	private String phoneNumber;
	
	public Message(boolean isMe, String message){
		this.isMe = isMe;
		this.body = message;
		date = Calendar.getInstance().getTime();
	}

	public Message(String message, String phone){
		this(false, message);
		this.phoneNumber = phone;
	}
	
	public boolean isMe() {
		return isMe;
	}

	public void setMe(boolean isMe) {
		this.isMe = isMe;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String message) {
		this.body = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = image;
	}

	public int getSenderUID() {
		return senderUID;
	}

	public void setSenderUID(int senderUID) {
		this.senderUID = senderUID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phone) {
		this.phoneNumber = phone;
	}
}
