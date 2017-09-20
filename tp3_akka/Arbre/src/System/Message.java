package System;

public class Message {

	public String message; // la chiane vas contient le message
	private int id; // pour identifiet le message
	
	
	public Message(int id,String msg){
			this.message = msg;
			this.id = id;
		}
		
	public String getMessage(){
		return this.message;
	}
	
	public int getId(){
		return this.id;
	}
}
