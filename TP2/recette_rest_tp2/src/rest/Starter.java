package rest;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * La classe principale de l'application.
 * Demarre un serveur sur le port 1069.
 * Utilise le prefixe /rest/ pour les URLs.
 * 
 * @author Lionel Seinturier <Lionel.Seinturier@univ-lille1.fr>
 */
public class Starter {
	
	public static void main( final String[] args ) throws Exception {
	
		int port = 1063;
		
		Server server = new Server( port );
		        
 		final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
 		final ServletContextHandler context = new ServletContextHandler(); 		
 		context.setContextPath( "/" );
 		context.addServlet( servletHolder, "/rest/*" ); 	
 		context.addEventListener( new ContextLoaderListener() );
 		
 		context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
 		context.setInitParameter( "contextConfigLocation", Config.class.getName() );
 		 		
        server.setHandler( context );
        server.start();
        server.join();	
	}
}
