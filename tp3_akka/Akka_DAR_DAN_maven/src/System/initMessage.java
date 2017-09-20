package System;

public class initMessage extends Message{

	private String intiNoeud;
	
	
	public initMessage(int id,String msg,String initNoeud){
		super(id,msg);
		this.intiNoeud = initNoeud;
	}
	
	
	public String getInitNoeud(){
		return this.intiNoeud;
	}
	
}
