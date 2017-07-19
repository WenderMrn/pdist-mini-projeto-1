package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientALRemote extends Remote {
	public void alert(String msg)  throws RemoteException ;
}
