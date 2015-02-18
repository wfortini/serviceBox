package br.com.servicebox.common.net;

import br.com.servicebox.common.json.PrestarServicoJSON;

public class ListaServicoResponse extends Response{

	
	private static final long serialVersionUID = -4284076708339877243L;
	
	private PrestarServicoJSON[] prestarServicoJSON;

	
	public PrestarServicoJSON[] getPrestarServicoJSON() {
		return prestarServicoJSON;
	}

	public void setPrestarServicoJSON(PrestarServicoJSON[] prestarServicoJSON) {
		this.prestarServicoJSON = prestarServicoJSON;
	}
	
	public ListaServicoResponse(boolean sucesso, String message, Long nodeId, int code){
		this.setSucesso(sucesso);
		this.setMessage(message);
		this.setNodeId(nodeId);
		this.setCode(code);
	}

	public ListaServicoResponse() {
		// TODO Auto-generated constructor stub
	}
}
