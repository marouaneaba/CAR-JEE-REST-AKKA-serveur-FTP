package system2;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {
  public static void main(String args[]) {
    ActorSystem system = ActorSystem.create("MySystem2", ConfigFactory.load().getConfig("MySystem2")); 
    System.out.println("J'ecoute...");
    ActorRef greeter1 = system.actorOf( Props.create(initiateur.class,"5"), "5" );
  }
}
