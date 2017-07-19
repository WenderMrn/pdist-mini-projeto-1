package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IApacheManager extends Remote{
	public String searchFile(String file) throws RemoteException;
	public boolean createFile(String file) throws RemoteException;
	public boolean deleteFile(String file) throws RemoteException;
	public boolean createManager(String login,String senha) throws RemoteException;
	public void login(String login,String senha) throws RemoteException;
	public void notifyAll(String message) throws RemoteException;
}
