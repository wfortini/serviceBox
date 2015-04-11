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
    public String solicitarServico(            
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
    		
    		solicitacao.setStatusSolicitacao(this.sendGCM(solicitacao.getSolicitado().getRegIdGCM(), 
    				solicitacao.getMensagem()));
    		
    		this.solicitacaoService.atualizar(solicitacao);
    		
    		return  "/home";
	    	//return new Response(true, "Notificação enviada  com sucesso.", solicitacao.getId(), Response.SUCESSO);
    	}catch(SolicitacaoException ux){
    		return "/home";// new Response(false, ux.getMessage(), null, ux.getCode());
		} catch (Exception e) {
			logger.error("Erro ao tentar registrar usuario Android: ", e.getMessage());
			return "/home"; //new Response(false, "Falha no cadastro do usuário", null, Response.ERRO_DESCONHECIDO);
		}
    }
	
   private Integer sendGCM(String regId, String mensagem){
  		
		this.sender = new Sender(SENDER_ID);
		
		Result result = null;
		String canonicalId = null;
		String idMensagem = null;
		
		Message message = new Message.Builder()	
		.collapseKey(this.collapKey)
		.timeToLive(30)
		.delayWhileIdle(false)
		.addData("mensagem", mensagem)
		.build();
		
		try {
			result = this.sender.send(message, regId, 1);
			if(result != null){
				//TODO: trata canonical id ocorre quando registro foi alterado, o canonical passara a ser o atual
				canonicalId = result.getCanonicalRegistrationId();
				idMensagem = result.getMessageId();
				if(idMensagem != null && !idMensagem.equals("")){
					return StatusSolicitacao.SOLICITACAO_ENVIADA.getCodigo();
				}
			
        } else {
             return StatusSolicitacao.SOLICITACAO_NAO_ENVIADA.getCodigo();
        }
		} catch (Exception e) {
			//TODO: logar aqui
			e.printStackTrace();
		}
		
		return StatusSolicitacao.SOLICITACAO_NAO_ENVIADA.getCodigo();
		
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
