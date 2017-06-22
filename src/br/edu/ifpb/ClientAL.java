package br.edu.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientAL {
	private static Pattern pattern = Pattern.compile("(\\d+.\\d+.\\d+.\\d+|\\w+):(\\d+)(.*)");

	public static void main(String[] args) {

		System.out.println("Bem vindo ao cliente do Apache Lite");

		if (args.length == 0) {
			options();
		} else {
			requisitar(args[0]);
		}
	}
	
	private static void requisitar(String url){
		Matcher matcher =  pattern.matcher(url);
		
		if (!matcher.find()) {
			System.out.println("Entrada incorreta, experimente algo como: 127.0.0.1:6500/index.html");
			return;
		}
		// Conecta ao servidor
		try {
			Socket socket = new Socket(matcher.group(1), Integer.parseInt(matcher.group(2)));

			if (!socket.isConnected()) {
				System.out.println("Não foi possível conectar ao servidor");
				return;
			}

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			DataInputStream input = new DataInputStream(socket.getInputStream());

			// Requisita arquivo
			output.writeUTF(matcher.group(3));

			// Escreve resposta
			System.out.println(input.readUTF());
			socket.close();
		} catch (IOException e) {
			System.out.println("Não foi possível realizar operação ao servidor especificado");
		}
	}
	
	private static void options(){
		Scanner teclado = new Scanner(System.in);
		String option = "";
		do {
			System.out.println("\n\n Para sair digite '0' (zero) .................................\n");
			System.out.println("Informe um endereço para conectar: \n");
			option = teclado.nextLine();
			if(option.equals("0")) break;
			requisitar(option);
		} while (true);
	}
	
}
