package br.com.webnow.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.net.LoginResponse;
import br.com.servicebox.net.Response;
import br.com.webnow.domain.Usuario;
import br.com.webnow.exception.UsuarioException;
import br.com.webnow.repository.AutorizarRepository;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.util.FileUtil;

@Controller
public class AutorizarController {
	
	private final static Logger logger = LoggerFactory.getLogger(AutorizarController.class);
	 
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;

	 @Autowired
	 private AutorizarRepository autenticarRepository;
	 
	 @RequestMapping(value = "/autenticar", method = RequestMethod.POST)
	 public @ResponseBody LoginResponse autenticar(
			 @RequestParam(value = "login") String login,
	         @RequestParam(value = "pwd") String pwd){
		 
		 LoginResponse loginResponse = null;
		 try {
			Usuario usuario = autenticarRepository.autenticar(login, pwd);
			
			if (usuario != null && usuario.getNodeId() != null){
						
				loginResponse.setUsuario(usuarioResponse);
				loginResponse.setmCredenciais(new Credenciais[]{new Credenciais()});
				loginResponse.setCode(Response.SUCESSO);
				loginResponse.setMessage("Usuário autorizado");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	 }
	 
	 
	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
    public @ResponseBody Response registrarUsuario(
            @RequestParam(value = "file") MultipartFile foto,
            @RequestParam(value = "login") String login, 
            @RequestParam(value = "nome") String nome, 
            @RequestParam(value = "sobrenome") String sobrenome, 
            @RequestParam(value = "senha") String senha, 
            @RequestParam(value = "sexo") String sexo, 
            @RequestParam(value = "imagemPerfil") String imagemPerfil) 
    
    {
    	
    	try {
    		if(foto.getSize() <= 0) throw new UsuarioException("Imagem inválida");
    		
	    	Usuario usuario = new Usuario(login,senha,nome,sobrenome,sexo,null);
	    	usuario.setDataCadastro(new Date());
	    	          // não estou tratando se o arquivo da foto já exite
	    	usuario = FileUtil.salvarArquivoLocal(usuario, foto);    	
	    	
	    	usuario = usuarioRepository.registrar(usuario);		    	
	    	return new Response(true, "Usuário cadastrado com sucesso.", usuario.getNodeId(), Response.SUCESSO);
    	}catch(UsuarioException ux){
    		return new Response(false, ux.getMessage(), null, ux.getCode());
		} catch (Exception e) {
			logger.error("Erro ao tentar registrar usuario Android: ", e.getMessage());
			return new Response(false, "Falha no cadastro do usuário", null, Response.ERRO_DESCONHECIDO);
		}
    }

}
