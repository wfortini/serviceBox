package br.com.webnow.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.webnow.boundingCoordinates.GeoLocation;
import br.com.webnow.domain.Carona;
import br.com.webnow.domain.Estacionamento;
import br.com.webnow.domain.Reboque;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.TipoServico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.service.prestarservico.ServicoService;


@Controller
public class UsuarioController {
	
	 private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	 
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;
	 
	 @Autowired
	 private ServicoService prestarServicoService;

	    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
	    public String profile(Model model) {
	        
	        return "/home";
	    }
	    
	    
	    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
	    public String registrar(
	            @RequestParam(value = "login") String login,
	            @RequestParam(value = "nome") String nome,
	            @RequestParam(value = "password") String password,
	            @RequestParam(value = "sobrenome") String sobreNome,
	            @RequestParam(value = "apelido") String apelido,
	            @RequestParam(value = "radioSexo") String sexo,
	            Model model) {

	        try {
	        	
	        	
	        	double earthRadius = 6371.01;
				GeoLocation myLocation = GeoLocation.fromRadians(-22.7099105, -43.56431449999999);
				double distance = 1000;
				
				GeoLocation[] boundingCoordinates = myLocation.boundingCoordinates(distance, earthRadius);
				
				System.out.println(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				
				System.out.println("Latitude Minima 1" + boundingCoordinates[0].getLatitudeInRadians());
				System.out.println("Latitude Maxima 2" + boundingCoordinates[1].getLatitudeInRadians());
				System.out.println("Longitude Minima 3" + boundingCoordinates[0].getLongitudeInRadians());
				System.out.println("Longitude Maxima 4" + boundingCoordinates[1].getLongitudeInRadians());
				System.out.println("Latitude busca 5" + myLocation.getLatitudeInRadians());
				System.out.println("Latitude busca 6" + myLocation.getLatitudeInRadians());
				System.out.println("Longitude busca 7" + myLocation.getLongitudeInRadians());
				System.out.println("Distancia 8" + distance / earthRadius);
	        	
	        	//Usuario usuario = new Usuario(login, password, nome, sobreNome, sexo, apelido);
	        	//usuario = usuarioRepository.registrar(usuario);
	        	Usuario u = usuarioRepository.findByLogin("wellington");
	        	
	        	
	        	usuarioRepository.save(u);
	        	
	        	System.out.println(u);
	        	
	        	for(Servico s : u.getServicosDisponiveis()){
					
					System.out.println("=================" + s);
					
				}
	        	
	        	
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
	        	
	        	prestarServicoService.addServico(u, servicoc);
	        	prestarServicoService.addServico(u, servicor);
	        	prestarServicoService.addServico(u, servicoe);
	        	
                for(Servico s : u.getServicosDisponiveis()){
					
					System.out.println("=================" + s);
					
				}
	        	
	        	
	        	return  "/home";
	        } catch(Exception e) {
	        	e.printStackTrace();
	            model.addAttribute("login",login);
	            model.addAttribute("nome",nome);
	            model.addAttribute("error",e.getMessage());
	            return "/home";
	        }
	    }
	    
	   
}
