package br.com.webnow.service.prestarservico;

import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.servicebox.common.domain.StatusSolicitacao;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Solicitacao;
import br.com.webnow.domain.Usuario;
import br.com.webnow.exception.SolicitacaoException;
import br.com.webnow.repository.SolicitacaoRepository;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.prestarservico.PrestarServicoRepository;

@Service
public class SolicitacaoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PrestarServicoRepository prestarServicoRepository;
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	
	public Solicitacao registrarSolicitacao(Long usuarioSolicitante, Long usuarioSolicitado, Long idPrestacao, 
			Integer tipoSolicitacao) {
		
		Usuario solicitante = null;
		Usuario solicitado = null;
		PrestarServico prestarServico = null;
		Solicitacao solicitacao = new Solicitacao();
		try {
			solicitante = usuarioRepository.findById(usuarioSolicitante);
			if(solicitante == null)
				throw new SolicitacaoException("Solicitante não encontrado");
			
			solicitado = usuarioRepository.findById(usuarioSolicitado);
			if(solicitado == null)
				throw new SolicitacaoException("Usuário solicitado não encontrado");
					
			prestarServico = this.prestarServicoRepository.findById(idPrestacao);
			if(prestarServico == null)
				throw new SolicitacaoException("O Serviço não foi localizado");
			
			solicitacao.setSolicitante(solicitante);
			solicitacao.setSolicitado(solicitado);
			solicitacao.setPrestarServico(prestarServico);
			solicitacao.setDataSolicitacao(new Date());
			solicitacao.setAtiva(true);
			solicitacao.setStatusSolicitacao(StatusSolicitacao.SOLICITACAO_ENVIADA.getCodigo());
			solicitacao.setTipoSolicitacao(tipoSolicitacao);
			solicitacao.setMensagem(this.getMensagem(tipoSolicitacao, solicitante, solicitado));
			
			this.solicitacaoRepository.save(solicitacao);		
			
			return solicitacao;
		} catch (Exception e) {
			//TODO: log aqui
			e.printStackTrace();
			throw new SolicitacaoException(e.getMessage());
		}
			
	}
	
	@SuppressWarnings("unchecked")
	private String getMensagem(Integer tipoSolicitacao, Usuario solicitante, Usuario solicitado){
		
		JSONObject json = new JSONObject();
		
		String msg = "O usuário " + solicitante.getNome() + ", está pedindo uma carona, você aceita " +
		" este pedido?";
		json.put("mensagem", msg);
		json.put("idSolicitante", solicitante.getId());
		json.put("idSolicitado", solicitado.getId());
		json.put("imagemPefil", solicitante.getFotoPerfil());
		
		return json.toJSONString();
	}

}
