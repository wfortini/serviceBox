package br.com.webnow.service.prestarservico;

import java.util.Date;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.servicebox.common.domain.StatusSolicitacao;
import br.com.servicebox.common.domain.TipoSolicitacao;
import br.com.webnow.controller.AutorizarController;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Solicitacao;
import br.com.webnow.domain.Usuario;
import br.com.webnow.exception.SolicitacaoException;
import br.com.webnow.repository.SolicitacaoRepository;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.prestarservico.PrestarServicoRepository;

@Service
public class SolicitacaoService {
	
	private final static Logger logger = LoggerFactory.getLogger(SolicitacaoService.class);
	
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
			solicitacao.setStatusSolicitacao(StatusSolicitacao.SOLICITACAO_ENVIADA_PARA_SOLICITADO.getCodigo());
			solicitacao.setTipoSolicitacao(tipoSolicitacao);
			solicitacao.setMensagem(this.getMensagem(solicitacao));			
			this.solicitacaoRepository.save(solicitacao);
			solicitacao.setMensagem(this.getMensagem(solicitacao));
			
			return solicitacao;
		} catch (Exception e) {
			logger.error("Erro ao Solicitar serviço: ", e.getMessage());
			e.printStackTrace();
			throw new SolicitacaoException(e.getMessage());
		}
			
	}
	
	
	public Solicitacao responderSolicitacao(Long id, Integer status){
		Solicitacao solicitacao = null;
		
		try {
		     solicitacao = this.solicitacaoRepository.findById(id);
			if(solicitacao == null)
				throw new SolicitacaoException("Solicitação não encontrado");
			
			solicitacao.setStatusSolicitacao(status); // uso status vindo do android para gerar mensagem
			solicitacao.setMensagem(this.getMensagem(solicitacao));
			solicitacao.setStatusSolicitacao(StatusSolicitacao.SOLICITACAO_ENVIADA_PARA_SOLICITANTE.getCodigo());
			this.solicitacaoRepository.save(solicitacao);		
			
			return solicitacao;
		}catch(SolicitacaoException se){
			throw new SolicitacaoException(se.getMessage());
		} catch (Exception e) {
			logger.error("Erro ao atualizar status da solicitacao: ", e.getMessage());
			e.printStackTrace();
			throw new SolicitacaoException(e.getMessage());
		}		
		
	}
	
	public Solicitacao atualizar(Solicitacao solicitacao){
		return this.solicitacaoRepository.save(solicitacao);
	}
	
	@SuppressWarnings("unchecked")
	private String getMensagem(Solicitacao solicitacao){
		
		JSONObject json = new JSONObject();
		
		json.put("mensagem", this.getMsgPorTipoEStatusSolicitacao(solicitacao));
		json.put("idSolicitante", solicitacao.getSolicitante().getId());
		json.put("idSolicitado", solicitacao.getSolicitado().getId());
		json.put("imagemPefil", solicitacao.getSolicitante().getFotoPerfil() + "+" 
				.concat(solicitacao.getSolicitante().getLogin()));
		json.put("idPrestacao", solicitacao.getPrestarServico().getId());
		json.put("tipoSolicitacao", solicitacao.getTipoSolicitacao());
		json.put("idSolicitacao", solicitacao.getId());
		json.put("status", solicitacao.getStatusSolicitacao());
		
		return json.toJSONString();
	}
	
	private String getMsgPorTipoEStatusSolicitacao(Solicitacao solicitacao){
		
		String msg = "";
		
		if(solicitacao.getTipoSolicitacao().equals(TipoSolicitacao.CARONA.getCodigo())){
			
			if(solicitacao.getStatusSolicitacao().equals(
					StatusSolicitacao.SOLICITACAO_ENVIADA_PARA_SOLICITADO.getCodigo())){
				msg = "O usuário " + solicitacao.getSolicitante().getNome() + ", está pedindo uma carona, você aceita " +
						" este pedido?";				
			}else if(solicitacao.getStatusSolicitacao().equals(
					StatusSolicitacao.SOLICITACAO_ACEITA_PELO_SOLICITADO.getCodigo())){
				msg = "O usuário " + solicitacao.getSolicitado().getNome() + ", aceitou seu pedido.";
			}if(solicitacao.getStatusSolicitacao().equals(
					StatusSolicitacao.SOLICITACAO_NEGADA_PELO_SOLICITADO.getCodigo())){
				msg = "O usuário " + solicitacao.getSolicitado().getNome() + ", recusou seu pedido.";
			}
			
			
		}
		
		return msg;
		
		
	}

}
