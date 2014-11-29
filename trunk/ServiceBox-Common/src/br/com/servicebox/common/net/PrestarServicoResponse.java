package br.com.servicebox.common.net;

public class PrestarServicoResponse extends Response{

	
	private static final long serialVersionUID = 7827655959679289111L;
	
	public PrestarServicoResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public PrestarServicoResponse(boolean sucesso, String message, Long nodeId, int code){
		this.setSucesso(sucesso);
		this.setMessage(message);
		this.setNodeId(nodeId);
		this.setCode(code);
	}
	

}
