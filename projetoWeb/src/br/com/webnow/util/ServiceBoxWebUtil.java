package br.com.webnow.util;

import java.util.ArrayList;
import java.util.Collection;

import br.com.servicebox.common.domain.Servico;

public class ServiceBoxWebUtil {
	
	/**
	 * 
	 * @param usuarioWeb classe Usuario presente no projeto Web
	 * @return usuario instancia da classe do projeto Common usado no projeto Android
	 */
	public static br.com.servicebox.common.domain.Usuario copiarUsuarioWebParaUsuarioAndroid(
			      br.com.webnow.domain.Usuario usuarioWeb){
		
		br.com.servicebox.common.domain.Usuario usuarioResponse = new br.com.servicebox.common.domain.Usuario(
				usuarioWeb.getLogin(),usuarioWeb.getPassword(),usuarioWeb.getNome(), usuarioWeb.getSobreNome(),
				usuarioWeb.getSexo(), usuarioWeb.getApelido());
		usuarioResponse.setNodeId(usuarioWeb.getNodeId());		
		
		
		return usuarioResponse;
	}
	
	public static Collection<? extends Servico> copiarServicoWebParaServicoAndroid(
			Collection<? extends br.com.webnow.domain.Servico> servicosWeb){
		
		Collection<? extends Servico> servicosAndroid = new ArrayList<Servico>();
		
		for(br.com.webnow.domain.Servico s : servicosWeb){
			
		}
		
		
		
		
		
		return null;
	}

}
