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
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;

@Controller
public class ServicoController {
	
	private final static Logger logger = LoggerFactory.getLogger(ServicoController.class);
	
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;
	
	
	@RequestMapping(value = "/prestarServico", method = RequestMethod.POST)
    public @ResponseBody Response prestarServico(            
            @RequestParam(value = "login") String login, 
            @RequestParam(value = "nodeId") String nodeId, 
            @RequestParam(value = "tipoServico") String tipoServico
            ) 
    
    {
    	
    	try {
    				    	
	    	return null;
    	}catch(UsuarioException ux){
    		return new Response(false, ux.getMessage(), null, ux.getCode());
		} catch (Exception e) {
			logger.error("Erro ao prestar servico: ", e.getMessage());
			return new Response(false, "Fallha em habilitar a prestação do serviço", null, Response.ERRO_DESCONHECIDO);
		}
    }

}
