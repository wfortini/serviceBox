package br.com.webnow.service.prestarservico;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
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
import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.prestarservico.PrestarServicoRepositoryImpl;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.returno.cypher.ServicoLocalizado;
import br.com.webnow.util.ServiceBoxWebUtil;

@Service
public class PrestarServicoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private PrestarServicoRepositoryImpl prestarServicoRepository;
	
	@Autowired
	private Neo4jTemplate neo4jTemplate;
	
	@Autowired
    private Neo4jOperations template;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Transactional
	public PrestarServico prestarServico(Long usuarioId, Integer tipoServico, 
			Itinerario itinerario, Planejamento planejamento){
		
		PrestarServico prestar = new PrestarServico();
		Usuario usuario = usuarioRepository.findById(usuarioId);
		Servico servico = Servico.getInstance(tipoServico);
		if(usuario != null && usuario.getServicosDisponiveis().contains(servico)){
			
	        prestar.setAtiva(true);
	        prestar.setData(new Date());
	        prestar = ServiceBoxWebUtil.preencherPrestacao(prestar, itinerario, planejamento); 
	        usuario.addPrestarServico(prestar);
	        template.save(usuario);
			
		}		
		
		return prestar;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Usuario> prestarServico(Double latitude, Double longitude, Double distanciaKM){		
				
		 Circle circle = new Circle(new Point(longitude, latitude), new Distance(distanciaKM, Metrics.KILOMETERS));
		
		return null; //IteratorUtils.toList(this.repository.findWithinDistance("localPartida", circle).iterator());
	}
	
	@Transactional
	public List<ServicoLocalizado> buscarServicoPorCoordenadasDistancia(GeoPartida partida, 
			GeoDestino destino, Integer servico, double distancia){
		
		
		return this.prestarServicoRepository.buscarServicoPorCoordenadasDistancia(partida, 
				          destino, servico, distancia);
		
		
	}
	
	
	public Iterable<ServicoLocalizado> buscarServicoPorCoordenadasDistancia(){
		GraphDatabaseService graphDatabaseService = this.neo4jTemplate.getGraphDatabaseService();
		Transaction tx = graphDatabaseService.beginTx();
		Iterable<ServicoLocalizado> lista = null;
		try {
		//	lista = this.prestarServicoRepository.buscarServicoPorCoordenadasDistancia(partida, destino, servico, distancia)();	
			tx.success();
			return lista;
		} catch (Exception e) {
			tx.close();
	   }
	
		return lista;
		
	}
	
	
	
	@Transactional
	public void addNoAoIndex(){
		
		
		GraphDatabaseService graphDatabaseService = this.neo4jTemplate.getGraphDatabaseService();
	    
	    IndexManager indexManager = graphDatabaseService.index();

	    Map<String, String> config = Collections.unmodifiableMap(
	        MapUtil.stringMap(SpatialIndexProvider.GEOMETRY_TYPE, LayerNodeIndex.POINT_GEOMETRY_TYPE,
	                          IndexManager.PROVIDER, SpatialIndexProvider.SERVICE_NAME,
	                          LayerNodeIndex.WKT_PROPERTY_KEY, "wkt") );
	    
	    Index<Node> index = indexManager.forNodes(GeoPartida.PARTIDA_GEOSPATIAL_INDEX, config);
	    
	    Iterator<Node> museums = this.neo4jTemplate.query("MATCH (m:GEOPARTIDA) RETURN m", null).to(Node.class).iterator();

	    while (museums.hasNext())
	    {
	      Node museum = museums.next();
	      
	      if (museum.hasProperty("wkt"))
	      {
	        System.out.println("Adding  to " + GeoPartida.PARTIDA_GEOSPATIAL_INDEX + " index............");
	      
	        index.add(museum, "dummy", "value");
	      }
	      else
	      {
	        System.out.println(" NOT ADDED to " + GeoPartida.PARTIDA_GEOSPATIAL_INDEX + " index...");
	      }
	    }
	}
}
