package br.com.webnow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.servicebox.common.net.Response;
import br.com.webnow.exception.UsuarioException;
import br.com.webnow.service.recomendacao.RecomendacaoService;


@Controller
public class RecomendacaoController {
	
	private final static Logger logger = LoggerFactory.getLogger(RecomendacaoController.class);
	
	 @Autowired
	 private RecomendacaoService recomendacaoService;
	 
	@RequestMapping(value = "/recomendar", method = RequestMethod.POST)
    public @ResponseBody Response recomendar(            
            @RequestParam(value = "idUsuarioRecomenda") String idUsuarioRecomenda, 
            @RequestParam(value = "idUsuarioRecomendado") String idUsuarioRecomendado, 
            @RequestParam(value = "tipoServicoRecomendado") String tipoServicoRecomendado,
            @RequestParam(value = "recomenda") String recomenda,
            @RequestParam(value = "comentario") String comentario) 
    
    {
    	
    	try {
    		
    		 this.recomendacaoService.recomendar(Long.valueOf(idUsuarioRecomenda), 
    				 Long.valueOf(idUsuarioRecomendado), Integer.valueOf(tipoServicoRecomendado),
    				 Boolean.valueOf(recomenda), comentario);
    				    	
	    	return new Response(true, "Recomendação realizada com sucesso", null, Response.SUCESSO);
	    	
    	}catch(UsuarioException ue){
    		return new Response(false, ue.getMessage(), null, Response.FALHA);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro ao recomendar: ", e);
			return new Response(false, "Fallha ao recomendar", null, Response.ERRO_DESCONHECIDO);
		}
    }

}
