package br.edu.ifpb.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IManagerRemote extends Remote{
	public void notify(String message) throws RemoteException;
	public String getLogin()throws RemoteException;
	public String getPassword()throws RemoteException;
}
