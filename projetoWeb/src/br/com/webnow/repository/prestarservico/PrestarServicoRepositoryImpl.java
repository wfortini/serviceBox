package br.com.webnow.repository.prestarservico;

import java.util.ArrayList;
import java.util.Date;
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
		cypher.append(" where Servico.tipoServico in [" + servico + "] return distinct(Usuario), PrestarServico");
		
		Result<Map<String,Object>> mapa = neo4jTemplate.query(cypher.toString(), null);
		List<ServicoLocalizado> lstServico = new ArrayList<ServicoLocalizado>();
		
		
		for (Map<String, Object> m : mapa) {
			
			ServicoLocalizado servicoLocalizado = new ServicoLocalizado();
			
            for (Map.Entry<String, Object> e : m.entrySet()) {           	           	
            	
            	if(e.getKey().equals("(Usuario)")){
            		
            		Node nodeUsuario = (Node) e.getValue(); 
            		
            		Usuario u = new Usuario();
            		u.setId(nodeUsuario.getId()); 
            		if(nodeUsuario.hasProperty("apelido") && nodeUsuario.getProperty("apelido") != null){
            			u.setApelido((String) nodeUsuario.getProperty("apelido"));	
            		}
            		u.setNome( (String) nodeUsuario.getProperty("nome"));
            		u.setLogin( (String) nodeUsuario.getProperty("login") );
            		u.setSobreNome( (String) nodeUsuario.getProperty("sobreNome") );
            		u.setSexo( (String) nodeUsuario.getProperty("sexo") );
            		if(nodeUsuario.hasProperty("fotoPerfil") && nodeUsuario.getProperty("fotoPerfil") != null)
            		   u.setFotoPerfil( (String) nodeUsuario.getProperty("fotoPerfil") );
            		if(nodeUsuario.hasProperty("dataCadastro") && nodeUsuario.getProperty("dataCadastro") != null){
            			String data = (String) nodeUsuario.getProperty("dataCadastro");
            			u.setDataCadastro(new Date(new Long(data)));
            		}
            		 
            		if(nodeUsuario.hasProperty("telefone") && nodeUsuario.getProperty("telefone") != null){
            			u.setTelefone((String) nodeUsuario.getProperty("telefone") );	
            		}            		
            		servicoLocalizado.setUsuario(u);
            		
            	}else if(e.getKey().equals("PrestarServico")){
            		
            		Node nodePrestar = (Node) e.getValue();             		
            		PrestarServico prestar = this.prestarServicoRepository.findById(nodePrestar.getId());
            		servicoLocalizado.setPrestarServico(prestar);
            		
            	}            

            }            
            lstServico.add(servicoLocalizado);            
           
        }
		
		return lstServico;
	}
	
	
}
