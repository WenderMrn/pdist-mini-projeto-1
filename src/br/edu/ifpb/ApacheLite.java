package br.edu.ifpb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ApacheLite extends UnicastRemoteObject implements ApacheLiteRemote {
	private HashMap<String, String> managers;
	private ArrayList<ClientALRemote> clients;
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			ApacheLite servidor = new ApacheLite();
			Naming.bind("servidor", servidor);
			System.out.println("ApacheLite RMI Inicializado..");

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	protected ApacheLite() throws RemoteException {
		this.managers = new HashMap<String, String>();
		this.clients = new ArrayList<ClientALRemote>();
		this.managers.put("mari", "123");
	}

	@Override
	public boolean createFile(String name, String content) throws RemoteException {
		File file = new File(String.format("./public/%s", name));

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		try {
			FileOutputStream cursor = new FileOutputStream(String.format("./public/%s", name), false);
			cursor.write(content.getBytes());
			cursor.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean removeFile(String name) throws RemoteException {
		File file = new File(String.format("./public/%s", name));

		if (!file.exists()) {
			return false;
		}

		file.delete();

		return true;
	}

	@Override
	public String readFile(String name) throws RemoteException {
		File file = new File(String.format("./public/%s", name));
		String saida = "200 - File found\nServer: ApacheLite/2.0\nContent:\n";

		if (!file.exists()) {
			return "400 - File not found";
		}

		try {
			saida += new String(Files.readAllBytes(Paths.get(String.format("./public/%s", name))));
		} catch (IOException e) {
			e.printStackTrace();
			return "400 - Something is going wrong";
		}

		return saida;
	}

	@Override
	public boolean doLogin(String user, String password)  throws RemoteException {
		if (this.managers.get(user) == null &&
				(this.managers.get(user) != password)) {
			return false;
		}

		return true;
	}

	@Override
	public void doCallback(ClientALRemote client)  throws RemoteException {
		this.clients.add(client);
		
		for (ClientALRemote c : this.clients) {
			c.alert("Mudou algo em..");
		}
	}
}
