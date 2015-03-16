package br.com.servicebox.common.net;

import java.io.Serializable;

import br.com.servicebox.common.json.PrestarServicoJSON;

public class PrestacaoLocalizada implements Serializable{
	
	private static final long serialVersionUID = 6622436051778890553L;
	
	private PrestarServicoJSON prestarServicoJSON;
	private UsuarioResponse usuario;
	
	public PrestacaoLocalizada() {
		
	}
	
	public PrestarServicoJSON getPrestarServicoJSON() {
		return prestarServicoJSON;
	}
	public void setPrestarServicoJSON(PrestarServicoJSON prestarServicoJSON) {
		this.prestarServicoJSON = prestarServicoJSON;
	}
	public UsuarioResponse getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioResponse usuario) {
		this.usuario = usuario;
	}

	
	
}
