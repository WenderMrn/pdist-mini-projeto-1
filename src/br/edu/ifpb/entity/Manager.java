package br.edu.ifpb.entity;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import br.edu.ifpb.interfaces.IApacheManager;
import br.edu.ifpb.interfaces.IManagerRemote;
import br.edu.ifpb.utils.ApacheLiteException;

public class Manager extends UnicastRemoteObject implements IManagerRemote{

	private static final long serialVersionUID = 1L;
	private static IManagerRemote studManager;
	private String login = "";
	private String password = "";
	
	public Manager()throws RemoteException{};
	
	public Manager(String login,String password)throws RemoteException{
		this.login = login;
		this.password = password;
	};
	
	@Override
	public void notify(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}

	@Override
	public String getLogin() throws RemoteException {
		// TODO Auto-generated method stub
		return this.login;
	}
	
	@Override
	public String getPassword() throws RemoteException {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		String option = "", name = "",login = "", password = "";
		
		try {
			
			IApacheManager servidor = (IApacheManager) Naming.lookup("rmi://localhost:1078/ApacheLiteRMI");
			System.out.println("Bem vindo ao cliente do Apache Lite RMI\n");
			
			do{
				
				System.out.println("---------------------------------------------");
				System.out.println("| ApacheLite 2.0 RMI                        |");
				System.out.println("|-------------------------------------------|");
				System.out.println("| 1 - Login                                 |");
				System.out.println("| 2 - Logout                                |");
				System.out.println("| 3 - Buscar Arquivo                        |");
				System.out.println("| 4 - Criar Arquivo                         |");
				System.out.println("| 5 - Remover Arquivo                       |");
				System.out.println("| 6 - Criar Manager                         |");
				System.out.println("| 0 - Sair                                  |");
				System.out.println("---------------------------------------------");
				
				option = teclado.nextLine();
				
				switch (option) {
				case "1":
					if(servidor.isLogged(studManager)){
						System.out.println("Você já esta logado com: "+studManager.getLogin());
						break;
					}else{
						
						System.out.println("\nInforma o login: ");
						login = teclado.nextLine();
						
						System.out.println("\nInforma a senha: ");
						password = teclado.nextLine();
						try {
							//if(servidor.authentication(login,password)){
								studManager = servidor.login(new Manager(login,password));
								if(studManager != null){
									System.out.println("Logado com sucesso");
									break;
								}else{
									System.out.println("Usuário e/ou senhas inválido(s)!");
								}
							//}
							
						}catch (ApacheLiteException e) {
							// TODO Auto-generated catch block
							System.out.println();
						}
					}
				break;
				case "2":
					try {
						servidor.logout(studManager);
						studManager = null;
						System.out.println("Fazendo logout.");
					} catch (ApacheLiteException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				break;
				case "3":
					System.out.println("Informe o nome do arquivo: ");
					name = teclado.nextLine();
					System.out.println(servidor.searchFile(name));
				break;
				case "4":
					String content;
					System.out.println("Informe o nome do arquivo: ");
					name = teclado.nextLine();
					
					System.out.println("Informe o conteúdo do arquivo: ");
					content = teclado.nextLine();
					
					try {
						if(servidor.createFile(name,content, studManager)){
							System.out.println("O arquivo "+name+" foi criado com sucesso!");
						}else{
							System.out.println("Não foi possível criar o arquivo. Verifique a extensão ou se o arquivo já existe.");
						}
						
					} catch (ApacheLiteException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
					
				break;
				case "5":
					System.out.println("Informe o nome do arquivo: ");
					name = teclado.nextLine();
					try {
						if(servidor.deleteFile(name, studManager)){
							System.out.println("Arquivo "+name+" deletado com sucesso!");
						}else{
							System.out.println("Não foi possível deletar o arquivo. Arquivo não encontrado");
						}
					} catch (ApacheLiteException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				break;
				case "6":
					
						System.out.println("Novo Manager\n");
						System.out.println("\nInforma o login: ");
						login = teclado.nextLine();
						
						System.out.println("\nInforma a senha: ");
						password = teclado.nextLine();
						
						
					try {
						if(servidor.createManager(login, password,studManager)){
							System.out.println("Novo Manager criado com sucesso!");
						}else{
							System.out.println("Erro: login já existente.");
						}
					} catch (ApacheLiteException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
					}
					
				break;
				case "0":
					try {
						servidor.logout(studManager);
					} catch (ApacheLiteException e) {
						// TODO Auto-generated catch block
					};
					System.out.println("finalizando Manager!");
					return;
				default:
					System.out.println("\nOpção inválida!");
					break;
				}
				
			}while(true);	
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
