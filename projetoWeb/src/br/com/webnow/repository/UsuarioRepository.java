package br.com.webnow.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.Usuario;

public interface UsuarioRepository extends GraphRepository<Usuario>, RelationshipOperationsRepository<Usuario>,
                                           ServiceBoxUserDetailsService{
	
	Usuario findByLogin(String login);
	Usuario findById(Long id);
}
