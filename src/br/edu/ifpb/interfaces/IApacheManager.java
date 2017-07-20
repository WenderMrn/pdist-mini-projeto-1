package br.edu.ifpb.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ifpb.utils.ApacheLiteException;

public interface IApacheManager extends Remote{
	public String searchFile(String name) throws RemoteException;
	public boolean createFile(String name, String content,IManagerRemote manager) throws RemoteException,ApacheLiteException;
	public boolean deleteFile(String name, IManagerRemote manager) throws RemoteException,ApacheLiteException;
	public boolean createManager(String login,String password, IManagerRemote manager) throws RemoteException,ApacheLiteException;
	public IManagerRemote login(String login,String senha) throws RemoteException;
	public boolean isLogged(String login) throws RemoteException;
	public boolean isLogged(IManagerRemote manager) throws RemoteException;
	public boolean logout(IManagerRemote manager) throws RemoteException, ApacheLiteException;
}
