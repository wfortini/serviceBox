package br.com.webnow.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import br.com.webnow.domain.Usuario;

public interface UsuarioRepository extends GraphRepository<Usuario>,
                                           ViuUserDetailsService{

	Usuario findByLogin(String login);
}
