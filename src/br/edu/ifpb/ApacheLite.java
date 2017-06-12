package br.edu.ifpb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ApacheLite {
	public static void main(String[] args) {
		ApacheLite al = new ApacheLite();
		al.find("doc.xt");
		al.find("index.html");
		al.find("doc.txt");
	}
	
	private String toHttpFormat(String content,String contenttype,
			int statusCode, String statusMessage){
		String formatedMessage 
					= "HTTP/1.1 "+statusCode+" "+statusMessage+"\n"
					+"Server: ApacheLite/1.0\n"
					+"Date: "+GregorianCalendar.getInstance(new Locale("pt","BR")).getTime()+"\n"
					+"Content-Type: "+contenttype+"\n"
					+"Last-Modified: Mon, 11 Jan 1998 13:23:42 GMT\n"
					+"Content-Length: "+content.length()+"\n"
					+"Connection: Closed\n\n"
					+ content;
		return formatedMessage;
	}
	
	public String find(String name){
		
		String response = "";
	
		try {
			byte[] file = Files.readAllBytes(Paths.get("./public/"+name));
			response += this.toHttpFormat(new String(file),"text/html",200,"Ok");
		} catch (IOException e) {
			response = this.toHttpFormat("","text/plain",404,"Not Found");
		}
		System.out.println(response);
		return response;
	} 
}
