package br.com.servicebox.common.net;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 1875831764587251525L;

	private boolean sucesso;
	private String message;
	private Long nodeId;
	private int code;
	
	public static final int SUCESSO = 0;
	public static final int LOGIN_DUPLICADO = 1;
	public static final int ERRO_DESCONHECIDO = 2;
	public static final int ERRO_NOME_INVALIDO = 3;
	public static final int ERRO_PASSWORD_INVALIDO = 4;
	public static final int USUARIO_NAO_AUTORIZADO = 5;
	public static final int USUARIO_AUTORIZADO = 6;
	
	public Response(boolean sucesso, String message, Long nodeId, int code){
		this.setSucesso(sucesso);
		this.setMessage(message);
		this.setNodeId(nodeId);
		this.setCode(code);
	}
	public Response() {
		// TODO Auto-generated constructor stub
	}
	public boolean isSucesso() {
		return sucesso;
	}
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
