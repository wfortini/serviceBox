package br.com.webnow.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.repository.query.Param;

import br.com.webnow.domain.Usuario;

public interface AutorizarRepository extends GraphRepository<Usuario>,                                          
                                          RelationshipOperationsRepository<Usuario>{
	
    @Query("match (a) where a:Usuario and " +
           " a.login = {login} and a.password = {pwd}" +
           " return a")
	public Usuario autenticar(@Param("login")  String login, @Param("pwd") String pwd);

}
