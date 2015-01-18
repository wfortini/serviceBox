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
import br.com.webnow.domain.Partida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Reboque;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.TipoServico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
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
	        	
	        	
	        	
	        	Usuario usuario = new Usuario("wellington", "12345", "Wellington", "Nascimento", "M", "wellington");
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
	        	request.setEnderecoPartida("Rua Janira");
	        	request.setHoraE("00:00");
	        	request.setHoraEntre("00:00");
	        	request.setHoraFixa("00:00");
	        	request.setLatitudeDestino(new Double("-22.9033746"));
	        	request.setLatitudePartida(new Double("-22.7099105"));
	        	request.setLogin("wellington");
	        	request.setLongitudeDestino(new Double("-43.1776887"));
	        	request.setLongitudePartida(new Double("-43.5643145"));
	        	request.setQuarta(true);
	        	request.setQuinta(true);
	        	request.setSabado(true);
	        	request.setSegunda(true);
	        	request.setServicoPrestado(TipoServico.CARONA.getCodigo());
	        	request.setSexta(true);
	        	request.setSoAmigos(true);
	        	request.setTerca(true);
	        	request.setNodeId(u.getId());
	        	
	        	PrestarServico prestar = null; 
	        	
	        	prestar = prestarServicoService.prestarServico(request.getNodeId(), 
						  request.getServicoPrestado(), ServiceBoxWebUtil.preencherObjetoItinerario(request), 
						  ServiceBoxWebUtil.preencherObjetoPlanejamento(request));
	        	
	        	
	        	
	        	
	        	return  "/home";
	        } catch(Exception e) {
	        	e.printStackTrace();	            
	            return "/home";
	        }
	    }
	 
	 @RequestMapping(value = "/buscarLocalizacaoTeste", method = RequestMethod.POST)
	 public String  buscarLocalizacaoPorDistancia(){
		 
		List<Partida> lista =  prestarServicoService.prestarServico(new Double("-22.7099105"), 
				new Double("-43.5643145"), new Double("1.000"));
		
		for(Partida p : lista){
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + p);
		}
		 return "/home";
		 
	 }

}
