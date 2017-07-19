package br.edu.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
