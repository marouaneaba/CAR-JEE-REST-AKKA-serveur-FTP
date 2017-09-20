package System;

import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.Scanner;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {
  public static void main(String args[]) {
	  
	  String message ="";
	  int identifiantMessage =0;
	  
	  //ActorSystem system = ActorSystem.create("MySystem1",ConfigFactory.load().getConfig("sys1")); 
	  ActorSystem system = ActorSystem.create("MySystem1");
	 ActorRef init = system.actorOf(Props.create(initiateur.class));
	  
	  Scanner sc = new Scanner(System.in);
	  
	  
	  //while((message=sc.nextLine()) != null){
	  while(!message.equals("quit")){
		  System.out.println("Veuillez saisie votre message");
		  message = sc.nextLine();
		  
		  System.out.println("entrez le noeud initiateur , sinon non (pour racine comme initiateur)");
		  String Ninit = sc.nextLine();
		  try{
		  if((Integer.parseInt(Ninit)<=6 && Integer.parseInt(Ninit)>=0)){
		  if(Ninit.equals("0")){
			  init.tell(new Message(identifiantMessage,message),ActorRef.noSender());
			  //System.out.println("nonzeùgmkzemj");
		  }else if(!Ninit.equals("non")){
			  init.tell(new initMessage(identifiantMessage,message,Ninit),ActorRef.noSender());
		  }
		  //initiateur.noeuds.clear();
		  	identifiantMessage++;
		  }else{
			  System.out.println("Noeud non Exist or charactére not acceptable ,  on recommence");
		  }
		  }catch(NumberFormatException ee){
			  System.out.println("Noeud non Exist or charactére not acceptable ,  on recommence");
		  }
	  }
	  
	/*System.out.println("J'ecoute...");
    ActorRef greeter1 = system.actorOf( Props.create(Greeter.class), "greeter1" );*/
  }
}
