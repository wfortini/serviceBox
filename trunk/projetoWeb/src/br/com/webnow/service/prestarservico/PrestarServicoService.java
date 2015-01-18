package br.com.webnow.service.prestarservico;

import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
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
}
