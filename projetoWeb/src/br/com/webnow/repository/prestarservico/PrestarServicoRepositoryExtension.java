package br.com.webnow.repository.prestarservico;

import java.util.List;

import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.returno.cypher.ServicoLocalizado;

public interface PrestarServicoRepositoryExtension {
	
	List<ServicoLocalizado> buscarServicoPorCoordenadasDistancia(GeoPartida partida, 
			GeoDestino destino, Integer servico, double distancia);

}
