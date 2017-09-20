/*
   Marouane Abakaim
 */

package com.codebind.serverftp;

import Command.DifinitionServer;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.util.Map;


public class server {
	
	//public static final Map<String, String> users = new HashMap<>();
        //protected static String directoryServer = "C:/Users/abk/server";
        
    /*
    public void init_map()
    {
        users.put("MAROUANE","MAROUANE");
        users.put("ABA","ABA");
        users.put("a","a");
    }
*/
    

    /*
    public static void ajouterUser(String us,String pass)
    {
        users.put(us,pass);
    }
    */
    /*
    public static Map<String,String> getUsers()
    {
        return users;
    }
    
    public String getDirectoryServer()
    {
        return directoryServer;
    }
    */
    public void executer()
    {
        DifinitionServer toolServer=new DifinitionServer();
        try {
            ServerSocket serverSocket = new ServerSocket(1069);
            System.out.println("le serveur à l'ecoute sur le port : ");
            
            while(true)
            {
                Socket socket = serverSocket.accept();
                Thread T = new Thread(new FtpRequest(socket));
                T.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
	
	public static void main(String[] args) {
            
            server sr =new server();
            //sr.init_map();
            sr.executer();
	}
	
	
}
