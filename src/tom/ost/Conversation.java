package tom.ost;

import java.util.Date;

public class Conversation {
	private String name = "";
	private byte[] image = null;
	private String lastMessage = "";
	private String lastMessageDate = null;
	private int numberOfPendingMessages = 0;
	private int cID;
	
	
	public Conversation(String name, String lastMessage){
		this.name = name;
		this.lastMessage = lastMessage;	
	}
	
	public Conversation(String name, String lastMessage, int cID){
		this(name, lastMessage);
		this.setCID(cID);
	}
	
	public Conversation(String name, String lastMessage, int cID, String lastMessageDate, byte[] image){
		this(name, lastMessage, cID);
		this.lastMessageDate = lastMessageDate;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getLastMessageDate() {
		return lastMessageDate;
	}

	public void setLastMessageDate(String lastMessageDate) {
		this.lastMessageDate = lastMessageDate;
	}

	public int getNumberPendingMessages() {
		return numberOfPendingMessages;
	}

//	public void setNumberOfPendingMessages(int numberOfPendingMessages) {
//		this.numberOfPendingMessages = numberOfPendingMessages;
//	}

	public void incrementPendingMessages(){
		this.numberOfPendingMessages++;
	}
	public void resetPendingMessages(){
		this.numberOfPendingMessages = 0;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCID() {
		return cID;
	}

	public void setCID(int cID) {
		this.cID = cID;
	}
	
	
}
