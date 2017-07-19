package br.edu.ifpb;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientAL extends UnicastRemoteObject implements ClientALRemote {

	protected ClientAL() throws RemoteException {
	}

	public void alert(String msg)  throws RemoteException  {
		System.out.println(msg);
	}

	public static void main(String[] args) {
		try {
			ApacheLiteRemote servidor = (ApacheLiteRemote) Naming.lookup("rmi://localhost/servidor");
			Scanner scanner = new Scanner(System.in);
			
			while(servidor != null) {
				System.out.println("--- Menu ---\n1) Realizar login\n");
				System.out.println("Digite a opção: ");
				String option = scanner.nextLine();
				
				if (option.equals("1")) {
					System.out.println("Digite o login: ");
					String user = scanner.nextLine();
					System.out.println("Digite a senha: ");
					String password = scanner.nextLine();
					
					if (servidor.doLogin(user, password)) {
						servidor.doCallback(new ClientAL());
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
