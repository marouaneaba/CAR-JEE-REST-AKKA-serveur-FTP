package system2;


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
	
	public void BroadcastToChild(Message msg){
		if(enfants.size() > 0)
			for(ActorRef n: enfants){
				if(n != null)
					n.forward(msg, getContext());
			}
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		system2.Message m = ((system2.Message)msg);
		if(m != null){
			Message chaine1 = new Message(m.getId(),m.getMessage());
			
			System.out.println("le node : "+nameNoeud+" ,recu le message : "+chaine1.getMessage());
			
			if(enfants.size() > 0 && enfants != null)
				this.BroadcastToChild(chaine1);
		
		
	
		}else unhandled(msg);
			System.out.println("bien recu class message");
		
		
		
	}

	
	
}
