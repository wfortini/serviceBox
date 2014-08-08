package br.com.webnow.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.webnow.domain.Servico;
import br.com.webnow.domain.TipoServico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;


@Controller
public class UsuarioController {
	
	 private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	 
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;

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

	        try {/**
	        	Usuario usuario = new Usuario(login, password, nome, sobreNome, sexo, apelido);
	        	usuario = usuarioRepository.registrar(usuario);
	        	Usuario u = usuarioRepository.findByLogin("wellington");
	        	System.out.println(u);
	        	
	        	
	        	Carona c = new Carona();
	        	c.setDataInicialPrestacao(new Date());
	        	c.setServicoDisponivel(true);
	        	c.setTipoServico(TipoServico.CARONA.getCodigo());
	        	
	        	servicoRepository.save(c);
	        	
	        	Reboque r = new Reboque();
	        	r.setDataInicialPrestacao(new Date());
	        	r.setServicoDisponivel(true);
	        	r.setTipoServico(TipoServico.REBOQUE.getCodigo());
	        	
	        	servicoRepository.save(r);
	        	
	        	Estacionamento e = new Estacionamento();
	        	e.setDataInicialPrestacao(new Date());
	        	e.setServicoDisponivel(true);
	        	e.setTipoServico(TipoServico.CARONA.getCodigo());
	        	**/
	        	
	        	//servicoRepository.save(e);
	        	
	            Servico s1 =  servicoRepository.findByPropertyValue("tipoServico", TipoServico.CARONA.getCodigo());
	            System.out.println("========================" +s1);
	            Servico s2 =  servicoRepository.findByPropertyValue("tipoServico", TipoServico.REBOQUE.getCodigo());
	            System.out.println("========================" +s2);
	            Servico s3 =  servicoRepository.findByPropertyValue("tipoServico", TipoServico.ESTACIONAMENTO.getCodigo());
	            
	            System.out.println("========================" +s3);
	            Usuario u = usuarioRepository.findByLogin("wellington");
	            u.addServico(s1);
	            u.addServico(s2);
	            u.addServico(s3);
	            
	            usuarioRepository.save(u);
	            
	            
	        	
	        	
	        	
	        	
	        	
	        	return  "/home";
	        } catch(Exception e) {
	            model.addAttribute("login",login);
	            model.addAttribute("nome",nome);
	            model.addAttribute("error",e.getMessage());
	            return "/home";
	        }
	    }
	    @RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	    public @ResponseBody Usuario registrarUsuario(
	            @RequestParam(value = "file") MultipartFile foto,
	            @RequestParam(value = "login") String login, 
	            @RequestParam(value = "nome") String nome, 
	            @RequestParam(value = "sobrenome") String sobrenome, 
	            @RequestParam(value = "senha") String senha, 
	            @RequestParam(value = "sexo") String sexo, 
	            @RequestParam(value = "imagemPerfil") String imagemPerfil) 
	    
	    {
	    	
	    	Usuario usuario = new Usuario(login,senha,nome,sobrenome,sexo,null);
	    	usuario.setDataCadastro(new Date());
	    	usuario.setFotoPerfil(imagemPerfil);
	    	
	    	
	    	try {
	    		 Usuario user = usuarioRepository.registrar(usuario);
	    		 return user;
				
			} catch (Exception e) {
				return new Usuario();
			}
	    }
	    /**
	    @RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	    public @ResponseBody Usuario registrarUsuario(@RequestBody  Usuario usuario){
	    	
	    	   try {
				return usuarioRepository.registrar(usuario);
			} catch (Exception e) {
				return usuario;
			}
	    }
        **/
}
