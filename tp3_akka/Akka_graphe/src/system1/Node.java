package system1;


import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Node extends UntypedActor{

	
	private List<ActorRef> enfants;
	private int idMessageRecu =999; // l'identifiant de dernier message passé dans cette noeud
	private String nameNoeud;
	private ActorRef parent;
	
	
	public Node(String name,List<ActorRef> childs){
		this.enfants = childs;
		this.nameNoeud = name;
	}
	
	
	public void BroadCastoChild(Message msg){
		if(enfants.size() > 0)
		for(ActorRef n: enfants){
			if(n != null)
				n.forward(msg, getContext());
		}
	}
	/*
	public void BroadCastoRMI(Object msg){
		
		for(ActorSelection a: diffChildOne){
				a.forward(msg, getContext());
		}
	}
	*/
	public boolean dejaRecu(Message msg){
		if(msg.getId() == this.idMessageRecu){
			return true;
		}else{
			this.idMessageRecu = msg.getId();
			return false;
		}
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		if(msg instanceof Message){
			
			Message chaine = (Message)msg;
			if(this.dejaRecu(chaine)){
				return;
			}
			
			System.out.println("le node : "+nameNoeud+" ,recu le message : "+chaine.getMessage());
				
			//Message chaine = (Message)msg;
			//System.out.println("le node : "+nameNoeud+" ,recu le message : "+chaine.getMessage());
			
			//System.out.println("er");
			//this.BroadCastoChild(chaine);
			
			if(enfants.size()>0 && enfants != null)
				this.BroadCastoChild(chaine);
			//this.BroadCastoRMI(chaine);
		}else unhandled(msg);
		
	}

	
	
}
