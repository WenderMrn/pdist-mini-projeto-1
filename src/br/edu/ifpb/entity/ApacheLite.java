package br.edu.ifpb.entity;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import br.edu.ifpb.interfaces.IApacheClient;
import br.edu.ifpb.interfaces.IApacheManager;
import br.edu.ifpb.interfaces.IManagerRemote;
import br.edu.ifpb.utils.ApacheLiteException;
import br.edu.ifpb.utils.FileHelper;

public class ApacheLite extends UnicastRemoteObject implements IApacheManager, IApacheClient{
	
	private static final long serialVersionUID = 1L;
	private static int port = 1078;
	private HashMap<String, String> managers;
	private ArrayList<IManagerRemote> loggedManagers;
	private static String acessoNegado = "\nAcesso negado! Faça login.\n";
	
	public ApacheLite()throws RemoteException{
		this.managers = new HashMap<String, String>();
		this.loggedManagers = new ArrayList<IManagerRemote>();
		this.managers.put("mari", "123");
	};
	
	public static void main(String[] args){
		try {
			
			Registry registry = LocateRegistry.createRegistry(port);
			ApacheLite servidor = new ApacheLite();
			registry.bind("ApacheLiteRMI", servidor);
			System.out.println("ApacheLite RMI Inicializado..");
			try {
				System.out.println("Host: "+Inet4Address.getLocalHost().getHostAddress()+":"+port);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		} catch (java.rmi.AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private IManagerRemote findLoggedManagerByLogin(String login){
		IManagerRemote manager = null;
		for (IManagerRemote iManagerRemote : loggedManagers) {
			try {
				if(iManagerRemote.getLogin().equals(login)){
					manager = iManagerRemote;
					break;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return manager;
	}
	
	@Override
	public String searchFile(String name) throws RemoteException {
		
		String response = "";
	
		if(FileHelper.fileExists(name)){
			// ok 200
			response += "\n200 ok\n\n"+FileHelper.readFileContent(name);
		}else{
			// not found 400
			response += "\n400 not faund - Arquivo não encontrado.";	
		}
		
		return response;
	}

	@Override
	public boolean createFile(String name, String content,IManagerRemote manager) throws RemoteException, ApacheLiteException {
		// TODO Auto-generated method 
		if(manager == null || !isLogged(manager.getLogin())) throw new ApacheLiteException(acessoNegado); 
		
		if(FileHelper.createFile(name, content)){
			//notify
			for (IManagerRemote iManagerRemote : loggedManagers) {
				if(!iManagerRemote.getLogin().equals(manager.getLogin()))
					iManagerRemote.notify("O usuário "+manager.getLogin()+" criou o arquivo "+name+"!");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFile(String name,IManagerRemote manager) throws RemoteException, ApacheLiteException {
		// TODO Auto-generated method stub
		if(manager == null || !isLogged(manager.getLogin())) throw new ApacheLiteException(acessoNegado); 
		
		if(FileHelper.deleteFile(name)){
			//notify
			for (IManagerRemote iManagerRemote : loggedManagers) {
				if(!iManagerRemote.getLogin().equals(manager.getLogin()))
					iManagerRemote.notify("O usuário "+manager.getLogin()+" deletou o arquivo "+name+"!");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean createManager(String login, String password,IManagerRemote manager) throws RemoteException,ApacheLiteException {
		// TODO Auto-generated method stub
		if(manager == null || !isLogged(manager.getLogin())) throw new ApacheLiteException(acessoNegado); 
		
		if(this.managers.get(login)==null){
			this.managers.put(login, password);
			return true;
		}
		return false;
	}
	
	@Override
	public IManagerRemote login(IManagerRemote manager) throws RemoteException{
		// TODO Auto-generated method stub
		IManagerRemote mr = null;
		
		if(manager == null ) return mr;
		
		if (this.managers.get(manager.getLogin()) != null &&
				(this.managers.get(manager.getLogin()).equals(manager.getPassword()))) {

			if(!isLogged(manager.getLogin())){
				this.loggedManagers.add(manager);
				System.out.println(this.loggedManagers.size());
				mr = manager;
			}else{
				mr = findLoggedManagerByLogin(manager.getLogin());
			}
		}
		return mr;
	}

	
	@Override
	public boolean isLogged(IManagerRemote manager) throws RemoteException {
		// TODO Auto-generated method stub
		return manager != null && isLogged(manager.getLogin());
	}
	
	@Override
	public boolean isLogged(String login) throws RemoteException {
		// TODO Auto-generated method stub
		IManagerRemote manager = null;
		manager = findLoggedManagerByLogin(login);
		return manager!=null;
	}

	@Override
	public boolean logout(IManagerRemote manager) throws RemoteException, ApacheLiteException {
		// TODO Auto-generated method stub
		
		if(manager == null || !isLogged(manager.getLogin())) throw new ApacheLiteException("Você não está logado!");
		
		IManagerRemote mr = findLoggedManagerByLogin(manager.getLogin());
		
		if(mr!=null) return false;
		
		this.loggedManagers.remove(manager);
		
		return true;
		
	}

}
