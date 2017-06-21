package br.edu.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientAL {
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
		
			do {
				System.out.println("Informe o nome do arquivo:");
				Socket serverSocket = new Socket("localhost",5600);
				DataOutputStream dataOutput = new DataOutputStream(serverSocket.getOutputStream());
				DataInputStream dataInput = new DataInputStream(serverSocket.getInputStream());
				
				dataOutput.writeUTF(sc.nextLine());
				String response = dataInput.readUTF();
				System.out.println("Response: "+response);
				System.out.println(".........................................................");
				serverSocket.close();
			} while (true);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
