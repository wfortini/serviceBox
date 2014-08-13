package br.com.webnow.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.transaction.annotation.Transactional;

import br.com.servicebox.net.Response;
import br.com.webnow.domain.Usuario;
import br.com.webnow.exception.UsuarioException;

public class UsuarioRepositoryImpl implements ServiceBoxUserDetailsService{
	
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
        if (usuarioExiste!=null) throw new UsuarioException("Login já existe: "+usuario.getLogin(), Response.LOGIN_DUPLICADO);
        if (usuario.getNome() ==null || usuario.getNome().isEmpty()) throw new UsuarioException("Nome não preenchido.", Response.ERRO_NOME_INVALIDO);
        if (usuario.getPassword() ==null || usuario.getPassword().isEmpty()) throw new UsuarioException("Password não preenchido.", Response.ERRO_PASSWORD_INVALIDO);
        usuario =template.save(usuario);
        
        return usuario;
	}

	@Override
	@Transactional
	public void adicionarAmigo(String login, Usuario userFromSession) {
		// TODO Auto-generated method stub
		
	}
}
