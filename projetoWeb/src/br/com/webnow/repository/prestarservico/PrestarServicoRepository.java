package br.com.webnow.repository.prestarservico;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;

import br.com.webnow.domain.Partida;

public interface PrestarServicoRepository extends GraphRepository<Partida>, 
                            SpatialRepository<Partida>{
	
	public static final String PARTIDA_GEOSPATIAL_INDEX = "localPartida";	
	Partida findById(Long id);

}