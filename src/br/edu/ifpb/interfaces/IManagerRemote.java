package br.edu.ifpb.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ifpb.entity.Message;

public interface IManagerRemote extends Remote{
	public Object notify(Message message) throws RemoteException;
	public String getLogin()throws RemoteException;
	public String getPassword()throws RemoteException;
}
