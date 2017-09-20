/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codebind.serverftp;

import Command.Commandes;
import com.sun.corba.se.spi.activation.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author abk
 */
@RunWith(MockitoJUnitRunner.class)
public class FtpRequestTest {

   
    
    
  
    
    
    
   FtpRequest ftp;
   @Mock private Socket socket;
   @Mock private Socket dataSocket;
   /*
    @Test
	public void testProcessRequestUser() throws IOException{	
		ftp.processRequest("USER ABA"); 
		Mockito.verify(ftp).processUSER("ABA");
	}
	
     */
    @Test
    public void initialise() throws IOException
    {   
        // socket control 
        socket = Mockito.mock(Socket.class);
        OutputStream out = this.socket.getOutputStream();
        InputStream in = this.socket.getInputStream();
        Mockito.when(socket.getInputStream()).thenReturn(in);
        Mockito.when(socket.getOutputStream()).thenReturn(out);
        
        
    }

    
}
