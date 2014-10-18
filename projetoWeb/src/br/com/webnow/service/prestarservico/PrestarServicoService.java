package br.com.webnow.service.prestarservico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public boolean addServico(Usuario usuario, Servico servico){
		
		boolean result = usuario.addServico(servico); 
		template.save(usuario);
		return result;
		
	}
	
	@Transactional
	public boolean addServico(Usuario usuario, Integer idServico){
		
		Servico servico = servicoRepository.findByPropertyValue("tipoServico", "idServico", idServico);
		boolean result = usuario.addServico(servico); 
		template.save(usuario);
		return result;
		
	}
	
	@Transactional
	public boolean removeServico(Usuario usuario, Integer idServico){
		
		Servico servico = servicoRepository.findByPropertyValue("tipoServico", "idServico", idServico);
		boolean result = usuario.removeServico(servico); 
		template.save(usuario);
		return result;		
		
	}
}
