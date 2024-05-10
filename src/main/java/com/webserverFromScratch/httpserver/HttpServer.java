package com.webserverFromScratch.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.webserverFromScratch.httpserver.config.Configuration;
import com.webserverFromScratch.httpserver.config.ConfigurationManager;

/**
 * 
 * Driver Class for Http server
 */

public class HttpServer {
	public static void main(String[] args) {
		
		System.out.println("server starting ...");
		
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
		
		System.out.println("Using Port" +conf.getPort());
		
		System.out.println("Using webRoot" +conf.getWebroot());
		
	
		try {
			ServerSocket serverSocket = new ServerSocket(conf.getPort());
			Socket socket = serverSocket.accept();
			
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			String html = "<html><head><title>Simple Java Html</title></head><body><h1>This page was served using HTTP Server.</h1></body></html>" ;
			
			final String CRLF = "\n\r"; // 13, 10
			
			String response = 
					"HTTP/1.1 200 OK" + CRLF +  // Status Line : HTTP_VESION  RESPONCE_CODE RESPONCE_MESSAGE
					"CONTENT LENGTH" + html.getBytes().length + CRLF +  // HEADER
						 CRLF +
						 html +
						 CRLF + CRLF ;
			
			outputStream.write(response.getBytes());
			
			
			
			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
