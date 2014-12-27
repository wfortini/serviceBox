package br.com.webnow.repository.prestarservico;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import br.com.webnow.domain.PrestarServico;

public interface PrestarServicoRepository extends GraphRepository<PrestarServico>, 
                           RelationshipOperationsRepository<PrestarServico>, SpatialRepository<PrestarServico>{
	
	PrestarServico findByNodeId(Long id);

}