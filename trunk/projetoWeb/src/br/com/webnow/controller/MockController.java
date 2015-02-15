package br.com.webnow.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.webnow.boundingCoordinates.GeoLocation;
import br.com.webnow.domain.Carona;
import br.com.webnow.domain.Estacionamento;
import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Reboque;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.TipoServico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.returno.cypher.ServicoLocalizado;
import br.com.webnow.service.prestarservico.PrestarServicoService;
import br.com.webnow.service.prestarservico.ServicoService;
import br.com.webnow.util.ServiceBoxWebUtil;

@Controller
public class MockController {
	
	@Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;
	 
	 @Autowired
	 private ServicoService servicoService;
	 
	 @Autowired
	 private PrestarServicoService prestarServicoService;
	 
	
	 @RequestMapping(value = "/rodar", method = RequestMethod.POST)
	 public String registrar() {

	        try {        	
	        	
	        	
	        	
	        	Usuario usuario = new Usuario("wellington", "12345", "Wellington", "Nascimento", "M", "Wallace");
	        	usuario = usuarioRepository.registrar(usuario);
	        	Usuario u = usuarioRepository.findByLogin("wellington");        	
	        	
	        	
	        	Carona c = new Carona();
	        	c.setDataInicialPrestacao(new Date());
	        	c.setServicoDisponivel(true);
	        	c.setTipoServico(TipoServico.CARONA.getCodigo());
	        	
	        	c = servicoRepository.save(c);	        	
	        	
	        	Reboque r = new Reboque();
	        	r.setDataInicialPrestacao(new Date());
	        	r.setServicoDisponivel(true);
	        	r.setTipoServico(TipoServico.REBOQUE.getCodigo());
	        	
	        	servicoRepository.save(r);
	        	
	        	Estacionamento e = new Estacionamento();
	        	e.setDataInicialPrestacao(new Date());
	        	e.setServicoDisponivel(true);
	        	e.setTipoServico(TipoServico.ESTACIONAMENTO.getCodigo());
	        	servicoRepository.save(e);
	        	
	        	Servico servicoc = servicoRepository.findByPropertyValue("tipoServico", TipoServico.CARONA.getCodigo());
	        	Servico servicor = servicoRepository.findByPropertyValue("tipoServico", TipoServico.REBOQUE.getCodigo());
	        	Servico servicoe = servicoRepository.findByPropertyValue("tipoServico", TipoServico.ESTACIONAMENTO.getCodigo());
	        	
	        	servicoService.addServico(u, servicoc);
	        	servicoService.addServico(u, servicor);
	        	servicoService.addServico(u, servicoe);
	        	
	        	
	        	// registrar serviço
	        	
	        	PrestarServicoRequest request = new PrestarServicoRequest();
	        	request.setDistanciaMaxima(0d);
	        	request.setDistanciaPartidaDestino(0d);
	        	request.setDomingo(true);
	        	request.setEnderecoDestino("Rua do Ouvidor");
	        	request.setEnderecoPartida("Rua Ten.jeronimo Costa, 206-286 - Fluminense, Queimados - RJ, 26387-276, Brasil");
	        	request.setHoraE("00:00");
	        	request.setHoraEntre("00:00");
	        	request.setHoraFixa("00:00");
	        	
	        	request.setLongitudePartida(new Double("-43.56361485000002"));
	        	request.setLatitudePartida(new Double("-22.709765256646044"));
	        	
	            //request.setLongitudePartida(new Double("-43.56415734999996"));
	        	//request.setLatitudePartida(new Double("-22.71002105737215"));
	        	//request.setEnderecoPartida("Rua Janira, 207-287 - Vila do Tingua, Queimados - RJ, 26383-230, Brasil");
	        	
	        	request.setLogin("wellington");
	        	request.setLongitudeDestino(new Double("-43.1776887"));
	        	request.setLatitudeDestino(new Double("-22.9033746"));
	        	
	        	request.setQuarta(true);
	        	request.setQuinta(true);
	        	request.setSabado(true);
	        	request.setSegunda(true);
	        	request.setServicoPrestado(TipoServico.CARONA.getCodigo());
	        	request.setSexta(true);
	        	request.setSoAmigos(true);
	        	request.setTerca(true);
	        	request.setNodeId(u.getId());
	        	request.setDescricao("Carona para Teste");
	        	
	        	PrestarServico prestar = null; 
	        	
	        	prestar = prestarServicoService.prestarServico(request.getNodeId(), 
						  request.getServicoPrestado(), request.getDescricao(), ServiceBoxWebUtil.preencherObjetoItinerario(request), 
						  ServiceBoxWebUtil.preencherObjetoPlanejamento(request));
	        	
	        	
	        	prestarServicoService.addNoAoIndex();
	        	
	        	return  "/home";
	        } catch(Exception e) {
	        	e.printStackTrace();	            
	            return "/home";
	        }
	    }
	 
	 @RequestMapping(value = "/buscarLocalizacaoTeste", method = RequestMethod.POST)
	 public String  buscarLocalizacaoPorDistancia(){
		 
		 GeoPartida partida = new GeoPartida();
		 GeoDestino destino = new GeoDestino();
		 partida.setLatitude(new Double("-22.71002105737215"));
		 partida.setLongitude(new Double("-43.56415734999996"));
		 destino.setLatitude(new Double("-22.9033746"));
		 destino.setLongitude(new Double("-43.1776887"));
		 Double dist = new Double("1.1");
		 
		 List<ServicoLocalizado> lista = prestarServicoService.buscarServicosPorCoordenadasComDistancia(
				 partida, destino, TipoServico.CARONA.getCodigo(), dist);
		
		 for(ServicoLocalizado s : lista){
			 System.out.println(s.getUsuario());
			 System.out.println(s.getPrestarServico());
			 System.out.println(s.getGeoDestino());
			 System.out.println(s.getGeoPartida());
		 }
				 
		 return "/home";
		 
	 }
	 
	 @RequestMapping(value = "/addNodeIndexSpartial", method = RequestMethod.POST)
	 public String  addNodeIndexSpartial(){
		 
		
		 prestarServicoService.addNoAoIndex();
		 
		 return "/home";
		 
	 }

}
