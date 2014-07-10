package br.com.webnow.controller;

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

import br.com.webnow.domain.Usuario;
import br.com.webnow.repository.UsuarioRepository;


@Controller
public class UsuarioController {
	
	 private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	 
	 @Autowired
	 private UsuarioRepository usuarioRepository;

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
	        	Usuario usuario = new Usuario(login, password, nome, sobreNome, sexo, apelido);
	        	usuario = usuarioRepository.registrar(usuario);
	        	System.out.println(usuario);
	            //return "forward:/usuario/"+login;
	        	return  "/registrar";
	        } catch(Exception e) {
	            model.addAttribute("login",login);
	            model.addAttribute("nome",nome);
	            model.addAttribute("error",e.getMessage());
	            return "/registrar";
	        }
	    }
	    
	    @RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	    public @ResponseBody Usuario registrarUsuario(@RequestBody  Usuario usuario){
	    	
	    	   try {
				return usuarioRepository.registrar(usuario);
			} catch (Exception e) {
				return usuario;
			}
	    }

}
