package br.com.webnow.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import br.com.webnow.domain.Usuario;

public interface AutorizarRepository extends GraphRepository<Usuario>,
                                          NamedIndexRepository<Usuario>,
                                          RelationshipOperationsRepository<Usuario>{
	
    @Query("START a=node:Usuario(login={0})" +
           " where a.password = {1}" +
           " return a")
	public Usuario autenticar(String login, String pwd);

}
