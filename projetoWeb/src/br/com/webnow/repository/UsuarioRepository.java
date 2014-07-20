package br.com.webnow.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import br.com.webnow.domain.Usuario;

public interface UsuarioRepository extends GraphRepository<Usuario>, RelationshipOperationsRepository<Usuario>,
                                           PortalUserDetailsService{

	Usuario findByLogin(String login);
}
