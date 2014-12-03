package br.com.webnow.service.prestarservico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.servicebox.common.domain.Itinerario;
import br.com.servicebox.common.domain.Planejamento;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;

@Service
public class PrestarServicoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
    private Neo4jOperations template;
	
	@Transactional
	public PrestarServico prestarServico(Long usuarioId, Integer tipoServico, 
			Itinerario itinerario, Planejamento planejamento){
		
		Usuario usuario = usuarioRepository.findByNodeId(usuarioId);
		Servico servico = servicoRepository.findByPropertyValue("tipoServico", tipoServico);
		PrestarServico prestar = null;
		if(usuario != null){
			
			prestar = usuario.iniciarPrestacao(template, servico, itinerario, planejamento);
			
		}
		
		
		return prestar;
	}
}
