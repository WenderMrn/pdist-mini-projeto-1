package br.edu.ifpb.entity;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import br.edu.ifpb.interfaces.IApacheClient;

public class ClientAL {
	
	public static void main(String[] args) {

		System.out.println("Bem vindo ao cliente do Apache Lite RMI");

		try {
			
			IApacheClient servidor = (IApacheClient) Naming.lookup("rmi://localhost/ApacheLiteRMI");
			System.out.println(servidor.searchFile("doc.txt"));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
}
