package br.com.webnow.domain;

import java.io.Serializable;

public class Credenciais implements Serializable{

	
	private static final long serialVersionUID = 614426912268970508L;
	
	private String login;
	private String pwd;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	
	

}
