package system2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

	

public class initiateur extends UntypedActor  {

	
	private ActorSystem system2;
	public Map<String,ActorRef> noeuds;
	private String init;
	
	public initiateur(String name) throws Exception{
		
		//System.out.println("class initiateur : name : "+name);
		this.init = name;
		system2 = ActorSystem.create("MySystem2");
		
		noeuds = new HashMap<String,ActorRef>();

		/*  Noeud 6 */
		noeuds.put("6",system2.actorOf(Props.create(Node.class,"6", new ArrayList<ActorRef>())));

		/*  Noeud 5 */
		List<ActorRef> NodeFive = new ArrayList<ActorRef>();
		NodeFive.add(noeuds.get("6"));
		noeuds.put("5",system2.actorOf(Props.create(Node.class,"5", NodeFive)));
		
	}
	
	
	
	
	
	@Override
	public void onReceive(Object msg) throws Exception {
		system1.initMessage m = ((system1.initMessage)msg);
		
		//System.out.println("message : "+m.getMessage());
			if(m != null){
			
			
				Message chaine1 = new Message(m.getId(),m.getMessage());
				//System.out.println("testing : "+chaine1.getMessage());
				noeuds.get(this.init).forward(chaine1, getContext());
			
			
		
			}else unhandled(msg);
		
			//System.out.println("bien recu class initiateur  : "+msg.getClass());
			
		
		
		
	}

}
