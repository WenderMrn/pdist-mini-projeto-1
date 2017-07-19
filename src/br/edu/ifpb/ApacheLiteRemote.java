package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApacheLiteRemote extends Remote {
	public boolean createFile(String name, String content) throws RemoteException;

	public boolean removeFile(String name) throws RemoteException;

	public String readFile(String name) throws RemoteException;
}
