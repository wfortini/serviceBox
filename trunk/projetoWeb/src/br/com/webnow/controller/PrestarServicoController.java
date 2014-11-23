package br.com.webnow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.Response;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.service.prestarservico.ServicoService;

@Controller
public class PrestarServicoController {

	private final static Logger logger = LoggerFactory.getLogger(PrestarServicoController.class);
	
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;
	 
	 @Autowired
	 private ServicoService prestarServicoService;
	 
	 
	 @RequestMapping(value = "/prestarServico", method = RequestMethod.POST)
	 public @ResponseBody Response prestarServico(@RequestBody PrestarServicoRequest request){
		 
		 
		 return null;
		 
	 }
}
