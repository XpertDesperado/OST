package tom.ost;

import java.util.Calendar;
import java.util.Date;



public class Message {
	private boolean isMe;
	private String body;
	private String date;
	private byte[] image = null;
	private int senderUID;
	private String phoneNumber;
	private int status;
	public static int STATUS_NONE = 0;
	public static int STATUS_OST = 1;
	public static int STATUS_ERR = 2;
	
	public Message(boolean isMe, String message){
		this.isMe = isMe;
		this.body = message;
		date = Calendar.getInstance().getTime().toString();
		setStatus(STATUS_NONE);
	}

	public Message(String message, String phone){
		this(false, message);
		this.phoneNumber = phone;
	}
	
	public Message(boolean isMe, String message, String date, int senderUID, int status){
		this.isMe = isMe;
		this.body = message;
		this.date = date;
		this.phoneNumber = "";
		this.senderUID = senderUID;
		this.status = status;
		this.image = null;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
