package br.edu.ifpb;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientAL {

	public static void main(String[] args) {
		try {
			ApacheLiteRemote servidor = (ApacheLiteRemote) Naming.lookup("rmi://localhost/servidor");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
