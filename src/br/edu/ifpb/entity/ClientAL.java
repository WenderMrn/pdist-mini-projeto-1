package br.edu.ifpb.entity;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import br.edu.ifpb.interfaces.IApacheClient;
import br.edu.ifpb.utils.ApacheLiteException;

public class ClientAL {
	
	public static void main(String[] args) {

		System.out.println("Bem vindo ao cliente do Apache Lite RMI");

		Scanner teclado = new Scanner(System.in);
		String arquivo = "";
		
		try {
			
			IApacheClient servidor = (IApacheClient) Naming.lookup("rmi://localhost/ApacheLiteRMI");
			
			do{
				System.out.println("\n\n---------------------------------------------");
				System.out.println("| ApacheLite 2.0 RMI (para sair digite 0 )  |");
				System.out.println("|-------------------------------------------|\n\n");
				System.out.println(" Informe o nome do arquivo: ");
				
				arquivo = teclado.nextLine();
		
				System.out.println(servidor.searchFile(arquivo));
				
			}while(!arquivo.equals("0"));
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
}
