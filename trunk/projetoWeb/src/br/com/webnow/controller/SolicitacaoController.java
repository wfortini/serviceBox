package br.com.webnow.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.servicebox.common.domain.StatusSolicitacao;
import br.com.servicebox.common.net.Response;
import br.com.webnow.domain.Solicitacao;
import br.com.webnow.exception.SolicitacaoException;
import br.com.webnow.service.prestarservico.SolicitacaoService;
import br.com.webnow.util.ServiceBoxWebUtil;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Controller
public class SolicitacaoController {
	
	private final static Logger logger = LoggerFactory.getLogger(SolicitacaoController.class);
	
	@Autowired
	private SolicitacaoService solicitacaoService;
	
	private String SENDER_ID = "AIzaSyB5i8wHc2EaRehgBy98hKV3DlTcJ5UEQYE";
	private String collapKey;
	private String message;
	private List<String> listaIdAndroids = new ArrayList<String>();
	private Sender sender;
	
	@RequestMapping(value = "/solicitarServico", method = RequestMethod.POST)
    public @ResponseBody Response solicitarServico(            
            @RequestParam(value = "idUsuarioSolicitante", required=false) String idUsuarioSolicitante, 
            @RequestParam(value = "idUsuarioSolicitado", required=false) String idUsuarioSolicitado, 
            @RequestParam(value = "tipoSolicitacao", required=false) String tipoSolicitacao, 
            @RequestParam(value = "idPrestacao", required=false) String idPrestacao) 
    
    {
		Solicitacao solicitacao = null;
    	
    	try {
    		
    		solicitacao = this.solicitacaoService.registrarSolicitacao(Long.valueOf(idUsuarioSolicitante),
    				Long.valueOf(idUsuarioSolicitado), Long.valueOf(idPrestacao), 
    				Integer.valueOf(tipoSolicitacao));
    	   
    		
    		//String regid = "APA91bHFvBbIB2PzeweRIwswSulUs_4jSxe6jhyLyKNhMsSf5-FpyQM0YekXEutycjHV1KoVAWELCEfe0ZSmJN5xeIhzsPf1xKDTQuXnkfN8lMLjQe-z2LAUejZXkLgmCVbV3EESlP_R";
    		
    		solicitacao.setStatusSolicitacao(this.sendGCM(solicitacao));
    		
    		this.solicitacaoService.atualizar(solicitacao);
    		
    		return new Response(true, "Solicitação enviada  com sucesso.", solicitacao.getId(), Response.SUCESSO);
	    	
    	}catch(SolicitacaoException ux){
    		return new Response(false, ux.getMessage(), null, Response.FALHA);
		} catch (Exception e) {
			logger.error("Erro ao Solicitar serviço: ", e.getMessage());			
			return new Response(false, "Erro ao solicitar serviço.", null, Response.ERRO_DESCONHECIDO);
		}
    }
	
	@RequestMapping(value = "/responderSolicitacao", method = RequestMethod.POST)
    public @ResponseBody Response responderSolicitacao(            
            @RequestParam(value = "idSolicitacao", required=true) String idSolicitacao, 
            @RequestParam(value = "respostaSolicitacao", required=true) String respostaSolicitacao)
            {
		
		try {
			Solicitacao solicitacao = this.solicitacaoService.responderSolicitacao(Long.valueOf(idSolicitacao),
					  Integer.valueOf(respostaSolicitacao));
			solicitacao.setStatusSolicitacao(this.sendGCM(solicitacao));
			this.solicitacaoService.atualizar(solicitacao);
			return new Response(true, "Solicita~]ao respondida  enviada  com sucesso.", solicitacao.getId(), Response.SUCESSO);
			
		} catch (Exception e) {
			
		}
		
		return null;
		
	}
    
	
	/**
	 * Envia solicitação para seridor Google GCM
	 * @param regId String com registro do dispositivo que ira receber a mensagem
	 * @param mensagem String mensagem que sera enviada
	 * @return código que especifica se mensagem foi enviada.
	 */
   private Integer sendGCM(Solicitacao solicitacao){
  		
		this.sender = new Sender(SENDER_ID);
		
		Result result = null;
		String canonicalId = null;
		String idMensagem = null;
		
		Message message = new Message.Builder()	
		.collapseKey(this.collapKey)
		.timeToLive(30)
		.delayWhileIdle(false)
		.addData("mensagem", solicitacao.getMensagem())
		.build();
		
		try {
			result = this.sender.send(message, 
					ServiceBoxWebUtil.getRegIDGCMConformeStatusSolicitacao(solicitacao), 1);
			if(result != null){
				//TODO: trata canonical id ocorre quando registro foi alterado, o canonical passara a ser o atual
				canonicalId = result.getCanonicalRegistrationId();
				idMensagem = result.getMessageId();
				// se a mensagem voi enviada eu simplesmente mantenho o status de enviado
				if(idMensagem != null && !idMensagem.equals("")){					
					return solicitacao.getStatusSolicitacao();
				}			
        } else {
        	
        	if(solicitacao.getStatusSolicitacao().equals(
        	  		StatusSolicitacao.SOLICITACAO_ENVIADA_PARA_SOLICITADO)){
        		return StatusSolicitacao.SOLICITACAO_NAO_ENVIADA_PARA_SOLICITADO.getCodigo();
        	}else{
        		return StatusSolicitacao.SOLICITACAO_NAO_ENVIADA_PARA_SOLICITANTE.getCodigo();
        	}
             
        }
		} catch (Exception e) {
			logger.error("Erro no google GCM serviço: ", e.getMessage());
			e.printStackTrace();
		}
		
		return solicitacao.getStatusSolicitacao();
		
	}
	
   private void sendGCMBroadcast(List<String> listaId, String mensagem){
		
		this.sender = new Sender(SENDER_ID);
		
		Message message = new Message.Builder()
		.collapseKey(this.collapKey)
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", mensagem)
		.build();
		
		try {
			MulticastResult result = this.sender.send(message, listaId, 1);
			if(result.getResults() != null){
				int regId = result.getCanonicalIds();
				if(regId != 0){
			}
        } else {
            int error = result.getFailure();
            System.out.println("Broadcast failure ======================: " + error);
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}



}
