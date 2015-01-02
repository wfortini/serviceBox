package br.com.webnow.service.prestarservico;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;

/**
 * Classe responsavel pelo gerenciamento da manutenção dos serviços prestados por usuario
 * @author wpn0510
 *
 */

@Service
public class ServicoService {
	
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
		
		Servico servico = servicoRepository.findByTipoServico(idServico);
		boolean result = usuario.addServico(servico); 
		template.save(usuario);
		return result;
		
	}
	
	@Transactional
	public boolean removeServico(Usuario usuario, Integer idServico){
		
		Servico servico = servicoRepository.findByTipoServico(idServico);
		boolean result = usuario.removeServico(servico); 
		template.save(usuario);
		return result;		
		
	}
	
	@Transactional
	public boolean removeServico(Usuario usuario, Servico servico){
				
		boolean result = usuario.removeServico(servico); 
		template.save(usuario);
		return result;		
		
	}
}
