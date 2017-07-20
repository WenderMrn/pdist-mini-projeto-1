package br.edu.ifpb.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {
	
	private static String publicFolder = "./public/";
	
	public static boolean createFile(String name, String content){
		
		if(fileExists(name)) return false;
		
		try{
		    PrintWriter writer = new PrintWriter(publicFolder+name, "UTF-8");
		    writer.println(content);
		    writer.close();
		    return true;
		} catch (IOException e) {
		   // do something
			return false;
		}
	}
	
	public static boolean deleteFile(String name){
		if(!fileExists(name)) return false;
		File file = readFile(name);
		file.delete();
		return true;
	}
	
	public static File readFile(String name){
		File file = new File(String.format(publicFolder+"%s", name));
		return file.exists()?file:null;
	}
	
	public static String readFileContent(String name){
		String response = "";
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(publicFolder+name));
			response += new String(bytes);
		} catch (IOException e) {
			//arquivo não encontrado
		}
		return response;
	}
	
	public static boolean fileExists(String name){
		return readFile(name)!=null?true:false;
	}
	
	public static String getFileExtension(String fileName){
		String extension = "";
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
			extension = fileName.substring(fileName.lastIndexOf("."));
		} 
		return extension;
	}

}
