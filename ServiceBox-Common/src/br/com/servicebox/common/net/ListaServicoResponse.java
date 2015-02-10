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
	
	

}
