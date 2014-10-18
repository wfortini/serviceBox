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
import br.com.servicebox.common.net.ServicoResponse;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.service.prestarservico.PrestarServicoService;

@Controller
public class ServicoController {
	
	private final static Logger logger = LoggerFactory.getLogger(ServicoController.class);
	
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;
	 
	 @Autowired
	 private PrestarServicoService prestarServicoService;
	
	
	@RequestMapping(value = "/adicionarServico", method = RequestMethod.POST)
    public @ResponseBody ServicoResponse adicionarServico(            
            @RequestParam(value = "login") String login, 
            @RequestParam(value = "disponibilizarServico") String disponibilizarServico, 
            @RequestParam(value = "tipoServico") String tipoServico
            ) 
    
    {
    	ServicoResponse response = null;
    	try {
    		
    		Usuario usuario = usuarioRepository.findByLogin(login);
    		for(Servico s : usuario.getServicosDisponiveis()){
				
				System.out.println("=================" + s);
				
			}
    		if(Boolean.valueOf(disponibilizarServico)){
    			if (prestarServicoService.addServico(usuario, Integer.valueOf(tipoServico))){
        			Servico servico = servicoRepository.findByPropertyValue("tipoServico", Integer.valueOf(tipoServico));
                    response = new ServicoResponse(true,"SUCESSO",servico.getNodeId(),Response.SUCESSO);
                    response.setDataInicialPrestacao(servico.getDataInicialPrestacao());
                    response.setServicoDisponivel(servico.getServicoDisponivel());
                    response.setTipoServico(servico.getTipoServico());
                    return response;
        		}
    		}else{
    			if (prestarServicoService.removeServico(usuario, Integer.valueOf(tipoServico))){
        			Servico servico = servicoRepository.findByPropertyValue("tipoServico", Integer.valueOf(tipoServico));
                    response = new ServicoResponse(true,"SUCESSO",servico.getNodeId(),Response.SUCESSO);
                    response.setDataInicialPrestacao(servico.getDataInicialPrestacao());
                    response.setServicoDisponivel(servico.getServicoDisponivel());
                    response.setTipoServico(servico.getTipoServico());
                    return response;
        		}
    		}
    		
    		
    				    	
	    	return new ServicoResponse(false, "Fallha em habilitar a prestação do serviço", null, Response.SERVICO_NAO_INCLUSO);
    	
		} catch (Exception e) {
			logger.error("Erro ao prestar servico: ", e.getMessage());
			return new ServicoResponse(false, "Fallha em habilitar a prestação do serviço", null, Response.ERRO_DESCONHECIDO);
		}
    }

}
