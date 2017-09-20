package system1;
import java.io.Serializable;

public class initMessage extends Message implements Serializable{

	private String intiNoeud;
	
	
	public initMessage(int id,String msg,String initNoeud){
		super(id,msg);
		this.intiNoeud = initNoeud;
	}
	
	
	public String getInitNoeud(){
		return this.intiNoeud;
	}
	
}
