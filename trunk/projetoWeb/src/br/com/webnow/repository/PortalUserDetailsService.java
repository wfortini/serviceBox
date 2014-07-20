package br.com.webnow.repository;

import org.springframework.transaction.annotation.Transactional;

import br.com.webnow.domain.Usuario;

public interface PortalUserDetailsService {
	
	//TODO: deve extender de extends UserDetailsService
	
	
	Usuario getUserFromSession();

    @Transactional
    Usuario registrar(Usuario usuario);

    @Transactional
    void adicionarAmigo(String login, final Usuario userFromSession);


}
