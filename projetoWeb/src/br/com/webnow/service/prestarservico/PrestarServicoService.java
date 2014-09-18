package br.com.webnow.service.prestarservico;

import org.springframework.beans.factory.annotation.Autowired;
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

	
	
	@Transactional
	public boolean addServico(Usuario usuario, Servico servico){
		
		return usuario.addServico(servico);
		
	}
	
	@Transactional
	public boolean addServico(Usuario usuario, Integer idServico){
		
		Servico servico = servicoRepository.findByPropertyValue("tipoServico", "idServico", idServico);
		return usuario.addServico(servico);
		
	}
}
