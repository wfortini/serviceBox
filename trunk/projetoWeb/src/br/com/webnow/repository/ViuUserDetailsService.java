package br.com.webnow.repository;

import org.springframework.transaction.annotation.Transactional;

import br.com.webnow.domain.Usuario;

public interface ViuUserDetailsService {
	
	Usuario getUserFromSession();

    @Transactional
    Usuario registrar(String login, String nome, String password);

    @Transactional
    void adicionarAmigo(String login, final Usuario userFromSession);


}
