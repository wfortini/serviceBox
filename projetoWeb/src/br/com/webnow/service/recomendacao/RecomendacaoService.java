package br.com.webnow.service.recomendacao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webnow.domain.Recomendacao;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.exception.UsuarioException;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;

@Service
public class RecomendacaoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
    private Neo4jOperations template;
	
	@Transactional
	public void recomendar(Long idUsuarioRecomendador, 
			Long idUsuarioRecomendado, Integer servicoRecomendado, boolean recomenda, String comentario){
		
		Recomendacao recomendacao = new Recomendacao();
		Usuario usuarioRecomendador = this.usuarioRepository.findById(idUsuarioRecomendador);
		Usuario usuarioRecomendado = this.usuarioRepository.findById(idUsuarioRecomendado);
		Servico servico = servicoRepository.findByPropertyValue("tipoServico", servicoRecomendado);
		
		if(usuarioRecomendado.equals(usuarioRecomendador)){
			throw new UsuarioException("Você não pode recomendar à você mesmo.");
		}
		
		for(Recomendacao rec : usuarioRecomendado.getRecomendacoes()){
			
			if(rec.getUsuarioQueRecomenda().getId().equals(usuarioRecomendador.getId()) &
					rec.getServicoRecomendado().getTipoServico().equals(servico.getTipoServico())){
				throw new UsuarioException("Você já recomendou este serviço para esse mesmo usuário.");
			}
		}
		
		recomendacao.setComentario(comentario);
		recomendacao.setRecomenda(recomenda);
		recomendacao.setServicoRecomendado(servico);
		recomendacao.setUsuarioQueRecomenda(usuarioRecomendador);
		recomendacao.setDataRecomendacao(new Date());
		
		if(usuarioRecomendado.addRecomendacao(recomendacao)){
			this.template.save(usuarioRecomendado);	
		}else{
			throw new UsuarioException("Você já recomendou este serviço para esse mesmo usuário.");
		}
		
		
		
	}
	

}
