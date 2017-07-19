package br.edu.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AlreadyBoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApacheLite extends UnicastRemoteObject implements IApacheManager, IApacheClient{
	
	private static final long serialVersionUID = 1L;
	
	public ApacheLite()throws RemoteException{};
	
	public static void main(String[] args){
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			ApacheLite servidor = new ApacheLite();
			Naming.bind("rmi://localhost/ApacheLiteRMI", servidor);
			System.out.println("ApacheLite RMI Inicializado..");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (java.rmi.AlreadyBoundException e) {
			// TODO Auto-generated catch block
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
	
	private static String getFileExtension(String fileName){
		String extension = "";
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
			extension = fileName.substring(fileName.lastIndexOf(".")+1);
		} 
		return extension;
	}
	
	private File readFile(String name){
		File file = new File(String.format("./public/%s", name));
		return file.exists()?file:null;
	}
	
	private boolean fileExists(String name){
		return readFile(name)!=null?true:false;
	}
	
	@Override
	public String searchFile(String name) throws RemoteException {
		
		// TODO Auto-generated method stub
		String response = "";
	
		try {
			String path = "./public/"+name;
			String extension = getFileExtension(path).equals("txt")?"text/plain":"text/html";
			
			byte[] bytes = Files.readAllBytes(Paths.get(path));
			response += this.toHttpFormat(new String(bytes),extension,200,"Ok");
		} catch (IOException e) {
			response = this.toHttpFormat("O recurso não pode ser encontrado :(","text/plain",404,"Not Found");
		}
		
		return response;
	}

	@Override
	public boolean createFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFile(String file) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createManager(String login, String senha) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void login(String login, String senha) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyAll(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
