package br.com.webnow.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.transaction.annotation.Transactional;

import br.com.webnow.domain.Usuario;

public class UsuarioRepositoryImpl implements ViuUserDetailsService{
	
	@Autowired
    private Neo4jOperations template;
	
	@Override
	public Usuario getUserFromSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Usuario findByLogin(String login) {
        return template.lookup(Usuario.class,"login",login).to(Usuario.class).singleOrNull();
    }
	
	@Override
	@Transactional
	public Usuario registrar(Usuario usuario) {
		
		Usuario usuarioExiste = findByLogin(usuario.getLogin());
        if (usuarioExiste!=null) throw new RuntimeException("Login já existe: "+usuario.getLogin());
        if (usuario.getNome() ==null || usuario.getNome().isEmpty()) throw new RuntimeException("No name provided.");
        if (usuario.getPassword() ==null || usuario.getPassword().isEmpty()) throw new RuntimeException("No password provided.");
        Usuario usuario = new Usuario(login, password, nome, sobreNome, sexo, apelido);        
        usuario =template.save(usuario);
        //setUserInSession(user);
        return usuario;
	}

	@Override
	@Transactional
	public void adicionarAmigo(String login, Usuario userFromSession) {
		// TODO Auto-generated method stub
		
	}
}
