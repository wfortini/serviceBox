package br.com.webnow.repository.prestarservico;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import br.com.webnow.domain.PrestarServico;

public interface PrestarServicoRepository extends GraphRepository<PrestarServico>, 
                                                  RelationshipOperationsRepository<PrestarServico>, 
                                                  PrestarServicoRepositoryExtension  {
	
		
	PrestarServico findById(Long id);	
	
	
}