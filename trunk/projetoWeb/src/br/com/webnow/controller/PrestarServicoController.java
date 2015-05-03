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

import br.com.servicebox.common.net.ListaPrestarServicoResponse;
import br.com.servicebox.common.net.ListaServicoResponse;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.PrestarServicoResponse;
import br.com.servicebox.common.net.Response;
import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.exception.ServicoNaoDisponivelException;
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
	 public @ResponseBody Response prestarServico(@RequestBody PrestarServicoRequest request){
		 
		 PrestarServico prestar = null; 
		 Response response = null;
		 try {
			  prestar = prestarServicoService.prestarServico(request.getNodeId(), 
					  request.getServicoPrestado(), request.getDescricao(), ServiceBoxWebUtil.preencherObjetoItinerario(request), 
					  ServiceBoxWebUtil.preencherObjetoPlanejamento(request));
			  if(prestar != null && prestar.getId() != null){
				  
				  response = new Response(true,"Prestação de serviço adicionado com sucesso.",
						  prestar.getId(),Response.SUCESSO);                 
                  return response;
				  
			  }else{
				  
				  response = new Response(true,"Falha ao adicionar Prestação de Serviço.",
						  prestar.getId(),Response.FALHA);                 
                  return response;
				  
			  }
		 }catch(ServicoNaoDisponivelException se){
			 logger.error("Erro ao adicionar prestacao servico: ", se.getMessage());
			 return new Response(false, se.getMessage(), null, Response.SERVICO_NAO_INCLUSO);
		} catch (Exception e) {
			logger.error("Erro ao adicionar prestacao servico: ", e.getMessage());
			return new Response(false, "Falha na prestação de serviço", null, Response.ERRO_DESCONHECIDO);
		}	
		 
	 }
	 
	 @RequestMapping(value = "/buscarServicosPorCoordenadasComDistancia", method = RequestMethod.POST)
	 public @ResponseBody ListaPrestarServicoResponse buscarServicosPorCoordenadasComDistancia(
			 @RequestBody PrestarServicoRequest request){
		 
		 GeoPartida partida = new GeoPartida();
		 GeoDestino destino = new GeoDestino();
		 ListaPrestarServicoResponse response =  new ListaPrestarServicoResponse();
		 
		 
		 try {
			partida.setLatitude(request.getLatitudePartida());
			partida.setLongitude(request.getLongitudePartida());
			destino.setLatitude(request.getLatitudeDestino());
			destino.setLongitude(request.getLongitudeDestino());
			response.setPrestacaoLocalizadas(ServiceBoxWebUtil.preencherPrestacaoLocalizada(
					this.prestarServicoService.buscarServicosPorCoordenadasComDistancia(
					    partida, destino, request.getServicoPrestado(), request.getDistanciaMaxima())));
			
			response.setCode(Response.SUCESSO);
			response.setMessage("Sucesso");
			response.setNodeId(0L);
			response.setSucesso(true);
			
		} catch (Exception e) {
			logger.error("Erro ao adicionar prestacao servico: ", e.getMessage());
			return new ListaPrestarServicoResponse(false, "Falha na busca de itinerário", null, Response.ERRO_DESCONHECIDO);
		}
		
		 return response;
		 
	 }
	 
	@RequestMapping(value = "/listarPrestarServicoOferecidos", method = RequestMethod.POST) 
	public @ResponseBody ListaServicoResponse listarPrestarServicoOferecidos(
			        @RequestParam(value = "idUsuario") String idUsuario){
		
		ListaServicoResponse listaResponse = new ListaServicoResponse();
		List<PrestarServico> lista = null;
		try{
			lista = prestarServicoService.listarPrestarServicoOferecidos(
					   Long.valueOf(idUsuario));			
			
			listaResponse.setPrestarServicoJSON(ServiceBoxWebUtil.preencherPrestarServicoJSON(lista));
			listaResponse.setMessage("Sucesso");
			listaResponse.setSucesso(true);
			listaResponse.setCode(Response.SUCESSO);
			
		}catch(Exception e){
			logger.error("Erro ao listar servico: ", e.getMessage());
			return new ListaServicoResponse(false, "Falha ao listar prestação de serviço", 0L, Response.ERRO_DESCONHECIDO);
		}
		
		return listaResponse; 
	}
}
