package br.com.webnow.net;

import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.net.Response;
import br.com.webnow.domain.Usuario;


public class LoginResponse extends Response{
	
	private static final long serialVersionUID = -2114605692059685414L;
	
	 private Credenciais[] mCredenciais;
	 private Usuario usuario;

	public Credenciais[] getmCredenciais() {
		return mCredenciais;
	}

	public void setmCredenciais(Credenciais[] mCredenciais) {
		this.mCredenciais = mCredenciais;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	 
	

}
