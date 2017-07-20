package br.edu.ifpb.entity;

import java.io.Serializable;

public class Message implements Serializable{
	private String msg;
	
	public Message(){}
	
	public Message(String msg){
		this.msg = msg;
	}

	public String get() {
		return msg;
	}

	public void set(String msg) {
		this.msg = msg;
	}
	
}
