package br.com.webnow.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.PrestarServicoResponse;
import br.com.servicebox.common.net.Response;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.service.prestarservico.PrestarServicoService;
import br.com.webnow.util.ServiceBoxWebUtil;

@Controller
public class PrestarServicoController {

	private final static Logger logger = LoggerFactory.getLogger(PrestarServicoController.class);
	
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;
	 
	 @Autowired
	 private PrestarServicoService prestarServicoService;
	 
	 
	 @RequestMapping(value = "/prestarServico", method = RequestMethod.POST)
	 public @ResponseBody PrestarServicoResponse prestarServico(@RequestBody PrestarServicoRequest request){
		 
		 PrestarServico prestar = null; 
		 PrestarServicoResponse response = null;
		 try {
			  prestar = prestarServicoService.prestarServico(request.getNodeId(), 
					  request.getServicoPrestado(), request.getDescricao(), ServiceBoxWebUtil.preencherObjetoItinerario(request), 
					  ServiceBoxWebUtil.preencherObjetoPlanejamento(request));
			  if(prestar != null && prestar.getId() != null){
				  
				  response = new PrestarServicoResponse(true,"Presta��o de servi�o adicionado com sucesso.",
						  prestar.getId(),Response.SUCESSO);                 
                  return response;
				  
			  }else{
				  
				  response = new PrestarServicoResponse(true,"Falha ao adicionar Presta��o de Servi�o.",
						  prestar.getId(),Response.FALHA);                 
                  return response;
				  
			  }
		} catch (Exception e) {
			logger.error("Erro ao adicionar prestacao servico: ", e.getMessage());
			return new PrestarServicoResponse(false, "Falha na presta��o de servi�o", null, Response.ERRO_DESCONHECIDO);
		}	
		 
	 }
	 
	 @RequestMapping(value = "/buscarServicosPorCoordenadasComDistancia", method = RequestMethod.POST)
	 public @ResponseBody PrestarServicoResponse buscarServicosPorCoordenadasComDistancia(
			 @RequestBody PrestarServicoRequest request){
		 
		
		
		 return null;
		 
	 }
	 
	@RequestMapping(value = "/listarPrestarServicoOferecidos", method = RequestMethod.POST) 
	public @ResponseBody PrestarServicoResponse buscarPorUsuario(
			        @RequestParam(value = "idUsuario") String idUsuario){
		
		List<PrestarServico> lista = prestarServicoService.buscarPorUsuario(Long.valueOf(idUsuario));
		
		return null; 
	}
}
