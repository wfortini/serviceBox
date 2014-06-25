package br.com.webnow.repository;

import org.springframework.transaction.annotation.Transactional;

import br.com.webnow.domain.Usuario;

public class UsuarioRepositoryImpl implements ViuUserDetailsService{
	
	
	@Override
	public Usuario getUserFromSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public Usuario registrar(String login, String nome, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void adicionarAmigo(String login, Usuario userFromSession) {
		// TODO Auto-generated method stub
		
	}
}
