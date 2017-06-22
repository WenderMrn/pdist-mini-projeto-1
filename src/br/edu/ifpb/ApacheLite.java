package br.edu.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApacheLite extends Thread{
	
	private static ServerSocket serverSocket = null;
	private static int port = 5600;
	private static int poolSize = 100; 
	private int number = 0;  
	
	public ApacheLite(ServerSocket ss,int n){
		serverSocket = ss;
		this.number = n;
	}
	
	public static void main(String[] args) {
		System.out.println("ApacheLite - Executing\n\n");
		ServerSocket ss = null;
		
		try {
			
			ss = new ServerSocket(port);
			ExecutorService pool = Executors.newFixedThreadPool(poolSize);
			
			for (int i = 1; i <= poolSize; i++) {
				pool.execute(new ApacheLite(ss,i));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Thread starting number - "+this.number);
		try {
			while(true){
				Socket socket = serverSocket.accept();
				DataInputStream dataInput = new DataInputStream(socket.getInputStream());
				DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
				
				String data = dataInput.readUTF();
				dataOutput.writeUTF(this.finder(data));
				socket.close();
				System.out.println("client adresss "+socket.getInetAddress()+" - request intercepted by number thread - "+this.number);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private String toHttpFormat(String content,String contenttype,
			int statusCode, String statusMessage){
		String formatedMessage 
					= "HTTP/1.1 "+statusCode+" "+statusMessage+"\n"
					+"Server: ApacheLite/1.0\n"
					+"Date: "+GregorianCalendar.getInstance(new Locale("pt","BR")).getTime()+"\n"
					+"Content-Type: "+contenttype+"\n"
					+"Last-Modified: Mon, 11 Jan 1998 13:23:42 GMT\n"
					+"Content-Length: "+content.length()+"\n"
					+"Connection: Closed\n\n"
					+ content;
		return formatedMessage;
	}
	
	private String finder(String name){
		
		String response = "";
	
		try {
			String path = "./public/"+name;
			String extension = getFileExtension(path).equals("txt")?"text/plain":"text/html";
			
			byte[] bytes = Files.readAllBytes(Paths.get(path));
			response += this.toHttpFormat(new String(bytes),extension,200,"Ok");
		} catch (IOException e) {
			response = this.toHttpFormat("","text/plain",404,"Not Found");
		}
		
		return response;
	}
	
	private static String getFileExtension(String fileName){
		String extension = "";
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
			extension = fileName.substring(fileName.lastIndexOf(".")+1);
		} 
		return extension;
	}
}
