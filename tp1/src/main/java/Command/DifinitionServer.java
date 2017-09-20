/*
   Marouane Abakaim
   
 */
package Command;

/**
 *
 * @author abk
 */
public class DifinitionServer {
    
    public static int port;
    public static String serverDirectory;
    public static String currentDirectory;
    
    public DifinitionServer(){
        this.serverDirectory = "C:/Users/abk/server";
        this.currentDirectory = "C:/Users/abk/server";
        this.port = 1069;
    }
    
    public String getDirectoryServer()
    {
        return serverDirectory;
    }
    
    public int getPort(){
        return port;
    }
    /*
    public String getAdressIpServer()
    {
        return severAdressIP;
    }
    */
    public  String getCurrentDirectory()
    {
        return currentDirectory;
    }
    public void AddCurrentDirectory(String chemin){
        this.currentDirectory = chemin;
    }
    
    
    
}
