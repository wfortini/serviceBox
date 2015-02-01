package br.com.webnow.repository.prestarservico;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.returno.cypher.ServicoLocalizado;

@Repository
public class PrestarServicoRepositoryImpl implements PrestarServicoRepositoryExtension{	
	
	@Autowired
	private PrestarServicoRepository prestarServicoRepository;
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	@Override
	public List<ServicoLocalizado> buscarServicoPorCoordenadasDistancia(
			GeoPartida partida, GeoDestino destino, Integer servico,
			double distancia) {
		
		Locale enLocale = new Locale("en", "EN");
		String strPartida = String.format(enLocale, "'withinDistance:[%.2f, %.2f, %.2f]'",partida.getLatitude(),
				partida.getLongitude(),distancia);
				
		String strDestino = String.format(enLocale, "'withinDistance:[%.2f, %.2f, %.2f]'",destino.getLatitude(),
				destino.getLongitude(),distancia);
		
		// Cypher com metodos de GeoLOcalização não suporta chamadas parametrizadas
		// preciso montar query cypher assim
		StringBuffer cypher = new StringBuffer();
		cypher.append("start GeoPartida=node:localPartida(".concat(strPartida).concat("), "));
		cypher.append(" GeoDestino=node:localDestino(".concat(strDestino).concat(")"));
		cypher.append(" match (GeoPartida)-[:VEM_DE]-(PrestarServico)-[:PRESTA_SERVICO]->(Usuario), ");
		cypher.append(" (Servico)-[:SERVICO_DISPONIVEL]-(Usuario) ");
		cypher.append(" where Servico.tipoServico in [" + servico + "] return GeoPartida, Usuario, PrestarServico, GeoDestino");
		
		Result<Map<String,Object>> mapa = neo4jTemplate.query(cypher.toString(), null);
		List<ServicoLocalizado> lstServico = new ArrayList<ServicoLocalizado>();
		
		
		for (Map<String, Object> m : mapa) {
			
			ServicoLocalizado servicoLocalizado = new ServicoLocalizado();
			
            for (Map.Entry<String, Object> e : m.entrySet()) {
            	
            	Node nodeUsuario = (Node) e.getValue();
            	
            	if(e.getKey().equals("Usuario")){
            		
            		Usuario u = new Usuario();
            		u.setId((Long) nodeUsuario.getProperty("id"));            		
            		servicoLocalizado.setUsuario(u);
            		
            	}else if(e.getKey().equals("PrestarServico")){
            		
            		PrestarServico prestar = new PrestarServico();
            		prestar.setId((Long)nodeUsuario.getProperty("id"));
            		servicoLocalizado.setPrestarServico(prestar);
            		
            	}else if(e.getKey().equals("GeoPartida")){
            		GeoPartida geo = (GeoPartida) e.getValue();
            		servicoLocalizado.setGeoPartida(geo);
            	}else if(e.getKey().equals("GeoDestino")){
            		GeoDestino geo = (GeoDestino) e.getValue();
            		servicoLocalizado.setGeoDestino(geo);
            	}                

            }            
            lstServico.add(servicoLocalizado);            
           
        }
		
		return lstServico;
	}
}
