package br.com.webnow.repository.prestarservico;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.repository.query.Param;

import br.com.webnow.domain.PrestarServico;

public interface PrestarServicoRepository extends GraphRepository<PrestarServico>, 
                                                  RelationshipOperationsRepository<PrestarServico>, 
                                                  PrestarServicoRepositoryExtension  {
	
		
	PrestarServico findById(Long id);
	
	@Query("START n=node({id}) MATCH (n)-[:PRESTA_SERVICO]-(prestarServico) RETURN prestarServico")
	List<PrestarServico> listarPrestarServicoOferecidos(@Param("id")  Long id);
	
	
}