package rest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.awt.PageAttributes.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.sun.jersey.multipart.FormDataParam;

import java.util.logging.*;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import javax.ws.rs.Consumes;  
import javax.ws.rs.POST;  
import javax.ws.rs.Path;  

import javax.ws.rs.core.Response;  


@Path("/server")
public class FtpResssource {

	/* @consumes : declare les types MIME supportés par la requet 
	 *  @Produces : pour la réponse ils sont facultatif
	 * */
	FTPClient ftp = new FTPClient();
	String Adress="127.0.0.1";
	int port = 1069;
	private String login;
	private boolean connected = false;
	private String ClientDirectory = "C:"+File.separator+"Users"+File.separator+"abk"+File.separator+
			"Desktop"+File.separator+"client";
	private String ServerDirectory = "C:"+File.separator+"Users"+File.separator+"abk"+File.separator+
			"Desktop"+File.separator+"server";
	
	
	private static void MessageServer(FTPClient ftp) {
        String[] msg = ftp.getReplyStrings();
        if (msg != null && msg.length > 0) {
            for (String message : msg) {
                System.out.println("SERVER: " + message);
            }
        }
    }
	
	public String connexion(String login,String pass) throws SocketException, IOException{
		
		//ftp.connect("ftp.univ-lille1.fr");
		ftp.connect(Adress,port);
		MessageServer(ftp);
		int Code = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(Code)) {
            System.out.println("Operation failed. Server reply code: " + Code);
            this.connected = false;
            return"Failed";
        }
		boolean reussite = ftp.login(login, pass);
		ftp.enterLocalActiveMode();
		MessageServer(ftp);
		if (!reussite) {
            System.out.println("Could not login to the server");
            connected=false;
            return "Failed2";
        } else {
        	connected = true;
            System.out.println("LOGGED IN SERVER");
        }
		
		return "Connected";
	}
	
	@GET
	@Produces
	public String WelcomeServer() throws IOException{
		if(this.connected && ftp.isConnected()){
			//ftp.cwd("/");
			return "<p><h1>Veuillez choisir Une Command </h1></p>"
					+"<a href=\"/rest/tp2/server/list\"> Lister </a></br>"
					+"<a href=\"/rest/tp2/server/setfile\"> mettre un fichier</a></br>"
					
					+"<a href=\"/rest/tp2/server/passive\"> passer en mode passive</a></br>"
					+"<a href=\"/rest/tp2/server/active\"> passer en mode Active</a></br>"
					+"<a href=\"/rest/tp2/server/quitter\"> Quitter</a></br>"
					+"----------------------------------------------------------------------"
					+"</br>Courant Directory : \""+ftp.printWorkingDirectory()+"\"</a></br>";
		}else{
		return "<p><h1>Bonjour dans notre Serveur Veuillez Connecter </h1></p>"
				+"<a href=\"/rest/tp2/server/connexion\"> Cliquer-ici pour Authentification</a></br>"
				+"<a href=\"/rest/tp2/server/conf\"> Cliquer-ici pour Configurer votre serveur</a>";
	
		}
	}
	
	@GET
	@Path("/conf")
	public String RessourceConfig(){
		
		return "<html><head></head><body>"
				+"<h1>  Configuration Serveur : </h1>"
				+"<form method=\"POST\" action=\"/rest/tp2/server/conf\">"
				+"<p> Adress IP:"
				+ "<input name=\"IP\" placeholder=\"Entrez Adress IP\" /><br /></p>"
				+"<p> Port :"
				+ "<input name=\"Port\" placeholder=\"Entrez Port\" /><br /></p>"
				+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
	}
	
	@POST
	@Path("/conf")
	public String RessourecConfiguration(@FormParam("IP")String NumIp,@FormParam("Port")String port){
			
		this.Adress = NumIp;
		this.port = Integer.parseInt(port);
		return "Configuration Port : "+port+" ,Adress IP : "+NumIp
				+"</br><a href=\"/rest/tp2/server/connexion\"> Clique-Here For -- Authentification -- </a></br>"
				+"<a href=\"/rest/tp2/server\"> Accueil</a>";
	}
	
	@GET
	@Path("/connexion")
	@Produces("text/html")
	public String getConnect(){
		return "<html><head></head><body>"
				+"<h1>  Bonjour Dans Notre Serveur Abakarim </h1>"
				+"<form method=\"POST\" action=\"/rest/tp2/server/connexion\">"
				+"<p> Login:"
				+ "<input name=\"name\" placeholder=\"name\" /><br /></p>"
				+"<p> Password :"
				+ "<input type=\"password\" name=\"pass\" placeholder=\"password\" /><br /></p>"
				+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
	
	}
	
	@POST
	@Path("/connexion")
	@Produces("text/html")
	public String setConnect(@FormParam("name") String login,@FormParam("pass") String pass) throws SocketException, IOException {
		
		String resultat = connexion(login,pass);
		String ret="";
		if(resultat =="Connected"){
			ret= "<html><head></head><body>"
					+"<p>Vous avez Reussie de connecter ;</p><a href=\".\"> aller sur la racine ftp</a>"
					+"</body></html>";
		}else{
			ret ="<html><head></head><body>"
					+"<h1>User or password error.. Erreur De Connexion </h1>"
					+"<form method=\"POST\" action=\"/rest/tp2/server/connexion\">"
					+"<p> Login:"
					+ "<input name=\"name\" placeholder=\"name\" /><br /></p>"
					+"<p> Password :"
					+ "<input type=\"password\" name=\"pass\" placeholder=\"password\" /><br /></p>"
					+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
		}
		return ret;
	}
	
	public String RessourceList() throws IOException{
		if(this.connected){
		//String res = ftp.printWorkingDirectory();
		FTPFile[] files = ftp.listFiles();
		System.out.println("taille : "+files.length);
		String filename ="";
		if(files.length == 0){
			filename =  "Directory vide ! Not Have  !! ";
					
		}else{
			for (int i=0; i<files.length;i++) {
				if(files[i].isDirectory())
					filename += "<a href=\"/rest/tp2/server/cd/"+files[i].getName()+"\">"+files[i].getName()+"</a></br>";
				else
					filename += "<a href=\"/rest/tp2/server/getfile/"+files[i].getName()+"\">"+files[i].getName()+"</a></br>";
			}
				filename +="----------------------------------------------------------------------------</br>"
					+"<a href=\"/rest/tp2/server/rename\"> renomer un fichier</a></br>"
					+"<a href=\"/rest/tp2/server/delete\"> Supprimer un fichier</a></br>"
					+"<a href=\"/rest/tp2/server/setfile\"> Déposer un fichier</a></br>"
					+"<a href=\"/rest/tp2/server/passive\"> passer au pasive mode</a></br>"
					+"<a href=\"/rest/tp2/server/active\"> passer au active mode</a></br>"
					+"<a href=\"/rest/tp2/server/createDir\"> Créer un Repértoir </a></br>";
			}
				filename += "<a href=\"/rest/tp2/server/quit\"> Quitter </a></br>"
						+"</br><a href=\"/rest/tp2/server/cdup\"> ====> Parent Directory <==== 	</a></br>"
						
				+"</br>----------------------------------------------------------------------------------------------------"
				+ "</br>   current Directory :  \""+ftp.printWorkingDirectory()+"\"";
		return filename.toString();
		}else{
			return "NOT CONNECTED";
		}
	}
	
	@GET
	@Path("/pwd")
	@Produces("text/html")
	public String RessourcesPWD() throws IOException{
		if(this.connected){
		String res = ftp.printWorkingDirectory();
		MessageServer(ftp);
		int Code = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(Code)) {
            System.out.println("Operation failed. Server reply code: " + Code);
            this.connected = false;
            return"Failed";
        }
		return "votre Directory Courant c'est    ==>   \""+res+"\"";
		}else{
			return "NOT CONNECTED";
		}
	}
	@GET
	@Path("/list")
	@Produces("text/html")
	public String List() throws IOException{
		if(connected){
			return RessourceList();
					
		}else{
			return "Is Not Connected";
		}
	}
	
	@GET
	@Path("/cdup")
	@Produces("text/html")
	public String cdup(){
		if(this.connected){
		try {
			ftp.cdup();
			
			MessageServer(ftp);
			int Code = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(Code)) {
	            System.out.println("Operation failed. Server reply code: " + Code);
	            return"NO CHANGE DIRECTORY  : Failed CWD";
	        }
			return RessourceList();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return "NO CDUP  : Failed CDUP";
		}
		}else
			return "NOT CONNECTED";
	}
	
	
	@GET
	@Path("/cd/{file :.*}")
	@Produces("text/html")
	public String CWD(@PathParam("file") String file){
		if(this.connected){
		try {
			
			ftp.cwd(file);
			
			
			MessageServer(ftp);
			int Code = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(Code)) {
	            System.out.println("Operation failed. Server reply code: " + Code);
	            return"NO CHANGE DIRECTORY : Failed CWD";
	        }
			return RessourceList();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed Exception CWD";
		}
		}else return "NOT CONNECTED";
	}
	
	
		@GET
	@Path("/getfile/{var:.*}")
	@Produces("application/octet-stream")
	public Response getFilePath(@PathParam("var") String filename) {
		
		if(this.connected){
			boolean isDir = new File(filename).isDirectory();
			
			if(isDir){
				return Response.status(Response.Status.FORBIDDEN)
						.entity("is Not File is Directory").build();
			}else{
			InputStream in;
			try {
				in = this.ftp.retrieveFileStream(filename);
				/* Response objet represente la réponse HTTP */
				/* return in avec la requet HTTP */
				Response response = Response.ok(in).build();
				/* la methode comp... pour accomplir la transaction */
				boolean succes = ftp.completePendingCommand();
				if(!succes){
					System.out.println("ere");
					return Response.status(Response.Status.NOT_FOUND)
							  .entity("<h1>Erreur</h1>")
						      .build();
				}
				return response;
			
			} catch (IOException e) {
				System.out.print("Erreur lors du téléchargement du fichier :" + filename);
			}
			}
		}
		return Response.status(Response.Status.NOT_FOUND)
				  .entity("<h1>NOT CONNECTED</h1>")
			      .build();
	}
	
	@GET
	@Path("/setfile")
	@Produces("text/html")
	public String setInfoFile(){
		if(this.connected){
		return "<html><head></head><body>"
				+"<h1>Bonjour , For upload Fichier </h1>"
				
				+"<form enctype = \"multipart/form-data\" method=\"POST\" action=\"/rest/tp2/server/postfile\">"
				+"<input type =\"text\" name=\"nom\" />"
				+"<p> fichier"
				+ "<input type = \"file\" name=\"monfichier\"  /><br /></p>"
				+"<p> Press for upload :"
				+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
		}else return "NOT CONNECTED";
	}
	
	 @POST
	 @Path ("/postfile")
	 public String postFile(@Multipart("nom") String nom, @Multipart("monfichier") InputStream is) throws java.io.IOException {
		 if(this.connected){	
		 ftp.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
		 	ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		 System.out.println("nom de fichier : "+nom);
		 ftp.storeFile(nom,is );
	   //BufferedReader br = new BufferedReader(new InputStreamReader(uploadedInputStream));
	   //System.out.println("Recu: " + br.readLine());
	   return"Envoi fichier OK (POST)\n"
			   +"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
				+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
		 }return "NOT CONNECTED";
	 }
	
	
	
	
	 
	
	
	@GET
	@Path("/rename")
	@Produces("text/html")
	public String ProcessRename(){
		if(this.connected){
		return "<html><head></head><body>"
				+"<h1>  Ranme File/Directory : </h1>"
				+"<form method=\"POST\" action=\"/rest/tp2/server/rename\">"
				+"<p> fichier à renomer : "
				+ "<input name=\"nameOr\" placeholder=\"name ficher\" /><br /></p>"
				+"<p> Password :"
				+ "<input name=\"nameTo\" placeholder=\"nom rename\" /><br /></p>"
				+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
		}else return "NOT CONNECTED";
	}
	
	
	
	@POST
	@Path("/rename")
	@Produces("text/html")
	public String ProcessRENAME(@FormParam("nameOr")String nameOr,@FormParam("nameTo")String nameTo) throws IOException{
		
		if(this.connected){
			
			if(ftp.rename(nameOr, nameTo)){
				return "File/Directory renamed"
						+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
						+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
				
			}else{
				return "Failed to renamed";
			}
					
		}else{
			return "Not Connected";
		}
	}
	
	@GET
	@Path("/createDir")
	@Produces("text/html")
	public String ProcesscreateDir(){
		if(this.connected){
		return "<html><head></head><body>"
				+"<h1>  Create Répértoire : </h1>"
				+"<form method=\"POST\" action=\"/rest/tp2/server/createDir\">"
				+"<p> Name Répertoire a Créer : "
				+ "<input name=\"name\" placeholder=\"name Repertoir\" /><br /></p>"
				+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
		}else return "NOT CONNECTED";
	}
	
	@POST
	@Path("/createDir")
	@Produces("text/html")
	public String CreateDirectory(@FormParam("name")String name) throws IOException{
		
		if(this.connected){

			if(ftp.makeDirectory(name)){
				return "Directory \""+name+"\" IS ccreated"
						+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
						+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
			}else{
				return "Directory Not Created";
			}
		}else{
			return "Is Not Connected";
		}
	}
	
	
	@GET
	@Path("/delete")
	public String ProcessDelete(){
		if(this.connected){
			return "<html><head></head><body>"
					+"<h1>  DELETE File : </h1>"
					+"<form method=\"POST\" action=\"/rest/tp2/server/delete\">"
					+"<p> Nom Directory/File à supprimer : "
					+ "<input name=\"nom\" placeholder=\"Entrez Name\" /><br /></p>"
					+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
		
		}else return "NOT CONNECTED";
	}
	
	@POST
	@Path("/delete")
	public String ProcessDelette(@FormParam("nom")String name) throws IOException{
		if(this.connected){
			
			boolean succees = ftp.deleteFile(name);
			if(succees == true){
				return "Directory/File bien supprimer"
						+"</br><a href=\"/rest/tp2/server/list\"> Lister</a></br>"
						+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
			}else 
				return "DIrectory/File Not Deleting "
					+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
					+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
			
		}else return "NOT CONNECTED";
	}
	
	@GET
	@Produces("text/html")
	@Path("/changeDirectory")
	public String changeDirectoryServer(){
		if(this.connected){
		return "<html><head></head><body>"
				+"<h1>  Change Directory Server : </h1>"
				+"<form method=\"POST\" action=\"/rest/tp2/server/changeDirectory\">"
				+"<p> new Directory : "
				+ "<input name=\"newDirectory\" placeholder=\"name directory\" /><br /></p>"
				+ "<input type=\"submit\" value=\"Submit\"><br /></form>"+"</body></html>";
		}return "NOT CONNECTED";
	}
	
	
	@GET
	@Path("/passive")
	@Produces("text/html")
	public String RessourcePassive(){
		if(this.connected){
			ftp.enterLocalPassiveMode();
				return "Mode Passive"
						+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
						+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
		}else
			return "Not Connected";
	}
	
	@GET
	@Path("/active")
	@Produces("text/html")
	public String RessourceZActiveMode(){
		if(this.connected){
			
			ftp.enterLocalActiveMode();
			return "Active Mode"
					+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
					+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
			
		}else return "Not Connected";
	}
	
	@GET
	@Path("/quit")
	@Produces("text/html")
	public String RessourceQuitter() throws IOException{
		String message="";
		if(this.connected){
			this.connected = false;
			int code = ftp.quit();
			if(code == 221)
				this.connected = false;
				message =  "quitter bye bye ";
		}else{
			message =  "NOT CONNECTED";
		}
		return message;
	}
	
	@GET
	@Path("/passive")
	public String ProcessActive(){
		if(this.connected){
			ftp.enterLocalPassiveMode();
			return "Passive Mode"
					+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
					+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
		}else
			return " NOT CONNECTED";
	}
	
	@GET
	@Path("/active")
	public String ProcessActif(){
		if(this.connected){
			ftp.enterLocalActiveMode();
			return "Active Mode"
					+"<a href=\"/rest/tp2/server/list\"> Lister</a></br>"
					+"<a href=\"/rest/tp2/server/\"> Acceuill</a></br>";
		}else
			return " NOT CONNECTED";
	}
	
}










