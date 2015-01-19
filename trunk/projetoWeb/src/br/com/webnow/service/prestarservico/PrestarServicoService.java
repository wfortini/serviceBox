package br.com.webnow.service.prestarservico;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.servicebox.common.domain.Itinerario;
import br.com.servicebox.common.domain.Planejamento;
import br.com.webnow.domain.Partida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.prestarservico.PrestarServicoRepository;
import br.com.webnow.repository.servico.ServicoRepository;

@Service
public class PrestarServicoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private PrestarServicoRepository prestarServicoPartidaRepository;
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	@Autowired
    private Neo4jOperations template;
	
	@Transactional
	public PrestarServico prestarServico(Long usuarioId, Integer tipoServico, 
			Itinerario itinerario, Planejamento planejamento){
		
		Usuario usuario = usuarioRepository.findById(usuarioId);
		Servico servico = servicoRepository.findByTipoServico(tipoServico);
		PrestarServico prestar = null;
		if(usuario != null){
			
			prestar = usuario.iniciarPrestacao(template, servico, itinerario, planejamento);
			
		}
		
		
		return prestar;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Partida> prestarServico(Double latitude, Double longitude, Double distanciaKM){		
				
		 Circle circle = new Circle(new Point(longitude, latitude), new Distance(distanciaKM, Metrics.KILOMETERS));
		
		return IteratorUtils.toList(this.prestarServicoPartidaRepository.findWithinDistance("localPartida", circle).iterator());
	}
	
	@Transactional
	public void addNoAoIndex(){
		
		
		GraphDatabaseService graphDatabaseService = this.neo4jTemplate.getGraphDatabaseService();
	    
	    IndexManager indexManager = graphDatabaseService.index();

	    Map<String, String> config = Collections.unmodifiableMap(
	        MapUtil.stringMap(SpatialIndexProvider.GEOMETRY_TYPE, LayerNodeIndex.POINT_GEOMETRY_TYPE,
	                          IndexManager.PROVIDER, SpatialIndexProvider.SERVICE_NAME,
	                          LayerNodeIndex.WKT_PROPERTY_KEY, "wkt") );
	    
	    Index<Node> index = indexManager.forNodes(PrestarServicoRepository.PARTIDA_GEOSPATIAL_INDEX, config);
	    
	    Iterator<Node> museums = this.neo4jTemplate.query("MATCH (m:PARTIDA) RETURN m", null).to(Node.class).iterator();

	    while (museums.hasNext())
	    {
	      Node museum = museums.next();
	      
	      if (museum.hasProperty("wkt"))
	      {
	        System.out.println("Adding " + museum.getProperty("name") + " to " + PrestarServicoRepository.PARTIDA_GEOSPATIAL_INDEX + " index...");
	      
	        index.add(museum, "dummy", "value");
	      }
	      else
	      {
	        System.out.println(museum.getProperty("name") + " NOT ADDED to " + PrestarServicoRepository.PARTIDA_GEOSPATIAL_INDEX + " index...");
	      }
	    }
	}
}
