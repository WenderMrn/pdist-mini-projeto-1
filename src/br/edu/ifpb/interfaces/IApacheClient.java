package br.edu.ifpb.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IApacheClient extends Remote{
	public String searchFile(String name) throws RemoteException;
}
