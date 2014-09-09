package tom.ost;

import java.util.Date;

public class Conversation {
	private String name = "";
	private Object image = null;
	private String lastMessage = "";
	private Date lastMessageDate = null;
	private int numberOfPendingMessages = 0;
	private int cID;
	
	
	public Conversation(String name, String lastMessage){
		this.name = name;
		this.lastMessage = lastMessage;	
	}
	
	public Conversation(String name, String lastMessage, int cID){
		this(name, lastMessage);
		this.cID = cID;
	}

	public String getName() {
		return name;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = image;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public Date getLastMessageDate() {
		return lastMessageDate;
	}

	public void setLastMessageDate(Date lastMessageDate) {
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
	
	
}
