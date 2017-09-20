package system1;


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

	
	private ActorSystem system1;
	public Map<String,ActorRef> noeuds;
	
	
	public initiateur() throws Exception{
		system1 = ActorSystem.create("MySystem1",ConfigFactory.load().getConfig("MySystem1"));

		noeuds = new HashMap<String,ActorRef>();
		
		/*  Noeud 6 */
		noeuds.put("6",system1.actorOf(Props.create(Node.class,"6", new ArrayList<ActorRef>())));
		
		/*  Noeud 4 */
		noeuds.put("4",system1.actorOf(Props.create(Node.class,"4", new ArrayList<ActorRef>())));
		
		/*  Noeud 3 */
		noeuds.put("3",system1.actorOf(Props.create(Node.class,"3", new ArrayList<ActorRef>())));
		
		/*  Noeud 2 */
		List<ActorRef> NodeTwo = new ArrayList<ActorRef>();
		NodeTwo.add(noeuds.get("4"));
		NodeTwo.add(noeuds.get("3"));
		noeuds.put("2",system1.actorOf(Props.create(Node.class,"2", NodeTwo)));
		
		/*  Noeud 5 */
		List<ActorRef> NodeFive = new ArrayList<ActorRef>();
		NodeFive.add(noeuds.get("6"));
		noeuds.put("5",system1.actorOf(Props.create(Node.class,"5", NodeFive)));
		
		/*  Noeud 1 */
		List<ActorRef> NodeOne = new ArrayList<ActorRef>();
		NodeOne.add(noeuds.get("2"));
		//NodeOne.add(noeuds.get("5"));
		
		
		
		
		ActorSelection as = system1.actorSelection("akka.tcp://MySystem2@127.0.0.1:2553/user/5");
		ActorRef ar = Await.result(as.resolveOne(new Timeout(30,TimeUnit.SECONDS)),Duration.Inf());
		NodeOne.add(ar)	;
		
		noeuds.put("1",system1.actorOf(Props.create(Node.class,"1", NodeOne)));
		

		
		System.out.println("constructeur initaiteur est fini");
	}
	
	
	
	
	
	@Override
	public void onReceive(Object msg) throws Exception {
		/*if(msg instanceof Message){
			Message chaine = (Message) msg;
			System.out.println("init recu message : "+chaine.getMessage());
			noeuds.get("1").forward(chaine, getContext());
		
		}else unhandled(msg);*/
		if(msg instanceof Message){
			//Message chaine = (Message) msg;
			//System.out.println("init recu message : "+chaine.getMessage());
			Message chaine = (Message) msg;
			if(msg instanceof initMessage){
				initMessage chaine1 = (initMessage) msg;
				System.out.println("init message");
				
				noeuds.get(chaine1.getInitNoeud()).forward(chaine, getContext());
			}else{
				
				noeuds.get("1").forward(chaine, getContext());
				System.out.println("non init message");
			}
			
		
		}else unhandled(msg);
		
	}

}
