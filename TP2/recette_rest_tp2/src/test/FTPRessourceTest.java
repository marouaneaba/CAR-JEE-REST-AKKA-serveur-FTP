package test;


import static org.junit.Assert.*;

import java.io.IOException;
import java.net.SocketException;

import static org.junit.Assert.*;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.FtpResssource;
public class FTPRessourceTest {

	public static FtpResssource ressource = new FtpResssource();
	public static FTPClient ftp = new FTPClient();
	
	@Test
	public  void  test() throws SocketException, IOException{
		
		String test = ressource.setConnect("a","a");
		ftp.connect("127.0.0.1",1069);
		ftp.login("a", "a");
		
		String ret= "<html><head></head><body>"
				+"<p>Vous avez Reussie de connecter ;</p><a href=\".\"> aller sur la racine ftp</a>"
				+"</body></html>";
		
		if(test != ret) fail("faild connexion user :a  password : a");
		else System.out.println("succes");
		//int output = obj.methode()
		//assertEquals(output,predire);
	}
	
	@Test
	public void listTest() throws SocketException, IOException{
		
		String Test = ressource.RessourceList();
		if(Test.equals("NOT CONNECTED")) fail("error to list");
		
	}
	
	@Test 
	public void pwd() throws IOException{
		String Test = ressource.RessourcesPWD();
		if(Test.equals("NOT CONNECTED")) fail("error to list");
	}
	
	@Test
	public void cdup() throws IOException{
		String Test = ressource.cdup();
		if(Test.equals("NO CHANGE DIRECTORY  : Failed CWD")) fail("Failed CWD");
		if(Test.equals("NO CDUP  : Failed CDUP")) fail("Failed CDUP");
		if(Test.equals("NOT CONNECTED")) fail("NOT CONNECTED");
	}
	
	@Test
	public void cwd() throws IOException{
		String Test = ressource.CWD(";;;;");
		if(!Test.equals("NO CHANGE DIRECTORY : Failed CWD")) fail("Failed CWD");
		if(Test.equals("Failed Exception CWD")) fail("Failed CDUP");
		if(Test.equals("NOT CONNECTED")) fail("NOT CONNECTED");
	}
	
	@Test
	public void rename() throws IOException{
		String Test = ressource.ProcessRENAME(";;", "test");
		if(!Test.equals("Failed to renamed")) fail("Failed CWD");
		if(Test.equals("Not Connected")) fail("NOT CONNECTED");
	}
	
	@Test
	public void CreatDirectory() throws IOException{
		ressource.setConnect("a","a");
		ressource.ProcessDelette("junitDirectory");
		String Test = ressource.CreateDirectory("junitDirectory");
		if(Test.equals("Directory Not Created")) fail("Directory Not Created");
		if(Test.equals("Is Not Connected")) fail("NOT CONNECTED");
		
	}
	
	@Test
	public void passive() throws IOException{
		String Test = ressource.CreateDirectory("junitDirectory");
		if(Test.equals("Not Connected")) fail("NOT CONNECTED");
	}
	
	@Test
	public void active() throws IOException{
		String Test = ressource.CreateDirectory("junitDirectory");
		if(Test.equals("Not Connected")) fail("NOT CONNECTED");
	}
	
	/*@Test
	public void quitter() throws IOException{
		String Test = ressource.CreateDirectory("junitDirectory");
		if(Test.equals("Not Connected")) fail("NOT CONNECTED");
	}*/
	
	
	
	
	
}
