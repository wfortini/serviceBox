package br.com.servicebox.common.net;

import java.io.Serializable;

public class Request implements Serializable{

	
	private static final long serialVersionUID = 1018315777078309117L;
	
	private Long nodeId;
	private String login;
	private Integer servicoPrestado;
	
	

	public Integer getServicoPrestado() {
		return servicoPrestado;
	}

	public void setServicoPrestado(Integer servicoPrestado) {
		this.servicoPrestado = servicoPrestado;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	

}
