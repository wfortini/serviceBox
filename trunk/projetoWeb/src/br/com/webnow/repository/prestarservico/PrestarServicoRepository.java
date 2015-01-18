package br.com.webnow.repository.prestarservico;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import br.com.webnow.domain.Partida;

public interface PrestarServicoRepository extends GraphRepository<Partida>, 
                            SpatialRepository<Partida>{
	
	Partida findById(Long id);

}