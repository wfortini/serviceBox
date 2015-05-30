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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.domain.TipoAcesso;
import br.com.servicebox.common.net.UsuarioResponse;
import br.com.servicebox.common.net.Response;
import br.com.webnow.domain.Usuario;
import br.com.webnow.exception.UsuarioException;
import br.com.webnow.repository.AutorizarRepository;
import br.com.webnow.repository.UsuarioRepository;
import br.com.webnow.repository.servico.ServicoRepository;
import br.com.webnow.util.FileUtil;
import br.com.webnow.util.ServiceBoxWebUtil;

@Controller
public class AutorizarController {
	
	private final static Logger logger = LoggerFactory.getLogger(AutorizarController.class);
	 
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private ServicoRepository servicoRepository;

	 @Autowired
	 private AutorizarRepository autenticarRepository;
	 
	 @RequestMapping(value = "/teste", method = RequestMethod.POST)
	 public String profile(Model model) {
		 
		 String login = "wellington";
		 String pwd = "12345";
		 UsuarioResponse loginResponse = new UsuarioResponse();
		 try {
			Usuario usuario = autenticarRepository.autenticar(login, pwd);		
			
			/**
			if (usuario != null && usuario.getNodeId() != null){						
				
				loginResponse.setmCredenciais(new Credenciais[]{new Credenciais()});
				loginResponse.setCode(Response.SUCESSO);
				loginResponse.setMessage("Usuário autorizado");
				loginResponse.setSucesso(true);
				return "/home";
			}else{
				
				
				loginResponse.setCode(Response.USUARIO_NAO_AUTORIZADO);
				loginResponse.setMessage("Usuário não autorizado.");
				loginResponse.setSucesso(false);
				return "/home";
			} **/
			return "/home";
		} catch (Exception e) {
			logger.error("Erro ao tentar registrar usuario Android: ", e.getMessage());
			loginResponse = new UsuarioResponse();
			loginResponse.setCode(Response.ERRO_DESCONHECIDO);
			loginResponse.setMessage("Erro ao autenticar o usuário");			
			loginResponse.setSucesso(false);
			return "/home";
			
		}
	       
	 }
	 
	 @RequestMapping(value = "/autenticar", method = RequestMethod.POST)
	 public @ResponseBody UsuarioResponse autenticar(
			 @RequestParam(value = "login") String login,
	         @RequestParam(value = "pwd") String pwd){
		
		 UsuarioResponse loginResponse = new UsuarioResponse();
		 try {
			Usuario usuario = autenticarRepository.autenticar(login, pwd);		
			
			if (usuario != null && usuario.getId() != null){						
				
				loginResponse = new UsuarioResponse();
				loginResponse.setmCredenciais(new Credenciais[]{new Credenciais()});
				loginResponse.setNodeId(usuario.getId());
				loginResponse.setNome(usuario.getNome());
				loginResponse.setApelido(usuario.getApelido());
				loginResponse.setDataCadastro(usuario.getDataCadastro());
				loginResponse.setFotoPerfil(usuario.getFotoPerfil());
				loginResponse.setLogin(usuario.getLogin());
				loginResponse.setPassword(usuario.getPassword());
				loginResponse.setSexo(usuario.getSexo());
				loginResponse.setSobreNome(usuario.getSobreNome());
				loginResponse.setServicoJSONs(ServiceBoxWebUtil.preencherServicoJSON(usuario));
				
				
				loginResponse.setCode(Response.SUCESSO);
				loginResponse.setMessage("Usuário autorizado");				
				loginResponse.setSucesso(true);
				return loginResponse;
			}else{				
				
				loginResponse.setCode(Response.USUARIO_NAO_AUTORIZADO);
				loginResponse.setMessage("Usuário não autorizado.");
				loginResponse.setSucesso(false);
				return loginResponse;
			}
		} catch (Exception e) {
			logger.error("Erro ao tentar autenticar usuario Android: ", e);
			loginResponse = new UsuarioResponse();
			loginResponse.setCode(Response.ERRO_DESCONHECIDO);
			loginResponse.setMessage("Erro ao autenticar o usuário");			
			loginResponse.setSucesso(false);
			return loginResponse;
			
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
            @RequestParam(value = "apelido") String apelido,
            @RequestParam(value = "telefone") String telefone,
            @RequestParam(value = "regIdGCM") String regIdGCM,
            @RequestParam(value = "imagemPerfil") String imagemPerfil) 
    
    {
    	
    	try {
    		if(foto.getSize() <= 0) throw new UsuarioException("Imagem inválida");
    		
	    	Usuario usuario = new Usuario(login,senha,nome,sobrenome,sexo,apelido);
	    	usuario.setTelefone(telefone);
	    	usuario.setRegIdGCM(regIdGCM);
	    	
	    	usuario.setDataCadastro(new Date());
	    	          // não estou tratando se o arquivo da foto já exite
	    	usuario = FileUtil.salvarArquivoLocal(usuario, foto);    	
	    	
	    	usuario = usuarioRepository.registrar(usuario);		    	
	    	return new Response(true, "Usuário cadastrado com sucesso.", usuario.getId(), Response.SUCESSO);
    	}catch(UsuarioException ux){
    		return new Response(false, ux.getMessage(), null, ux.getCode());
		} catch (Exception e) {
			logger.error("Erro ao tentar registrar usuario Android: ", e.getMessage());
			return new Response(false, "Falha no cadastro do usuário", null, Response.ERRO_DESCONHECIDO);
		}
    }
	
	/**
	 * Autentica o usuario via rede social caso o usuario exista na base / ou inclui o mesmo
	 * @param login
	 * @param pwd
	 * @return
	 */
	 @RequestMapping(value = "/autenticarRedeSocial", method = RequestMethod.POST)
	 public @ResponseBody UsuarioResponse autenticarRedeSocial(
			 @RequestParam(value = "socialId") String socialId,
			 @RequestParam(value = "login", required=false) String login, 
            @RequestParam(value = "nome") String nome, 
            @RequestParam(value = "sobrenome") String sobrenome, 
            @RequestParam(value = "senha", required=false) String senha, 
            @RequestParam(value = "sexo") String sexo,
            @RequestParam(value = "apelido", required=false) String apelido,
            @RequestParam(value = "telefone", required=false) String telefone,
            @RequestParam(value = "regIdGCM", required=true) String regIdGCM,
            @RequestParam(value = "imagemPerfil") String imagemPerfil) {
		
		 Usuario usuario = null;
		 UsuarioResponse loginResponse = new UsuarioResponse();
		 try {
			
			 usuario = this.verificarSeUsuarioExiste(socialId, login, regIdGCM, imagemPerfil);
			 
			 if(usuario == null || usuario.getId() == null){
				 
				 usuario = new Usuario(login, "", nome, sobrenome, sexo, apelido);
				 usuario.setFotoPerfil(imagemPerfil);
				 usuario.setTelefone(telefone);
				 usuario.setRegIdGCM(regIdGCM);
				 usuario.setLoginRealizadoPor(TipoAcesso.FACEBOOK.getCodigo());
				 usuario.setSocialId(Long.valueOf(socialId));
				 usuario.setDataCadastro(new Date());
				 
				 usuario = usuarioRepository.registrar(usuario);	
			 }		 
				
			
			if (usuario != null && usuario.getId() != null){						
				
				loginResponse = new UsuarioResponse();
				loginResponse.setmCredenciais(new Credenciais[]{new Credenciais()});
				loginResponse.setNodeId(usuario.getId());
				loginResponse.setNome(usuario.getNome());
				loginResponse.setApelido(usuario.getApelido());
				loginResponse.setDataCadastro(usuario.getDataCadastro());
				loginResponse.setFotoPerfil(usuario.getFotoPerfil());
				loginResponse.setLogin(usuario.getLogin());
				loginResponse.setPassword(usuario.getPassword());
				loginResponse.setSexo(usuario.getSexo());
				loginResponse.setSobreNome(usuario.getSobreNome());
				loginResponse.setServicoJSONs(ServiceBoxWebUtil.preencherServicoJSON(usuario));
				
				
				loginResponse.setCode(Response.SUCESSO);
				loginResponse.setMessage("Usuário autorizado");				
				loginResponse.setSucesso(true);
				return loginResponse;
			}else{				
				
				loginResponse.setCode(Response.USUARIO_NAO_AUTORIZADO);
				loginResponse.setMessage("Usuário não autorizado.");
				loginResponse.setSucesso(false);
				return loginResponse;
			}
		} catch (Exception e) {
			logger.error("Erro ao tentar autenticar usuario Android: ", e);
			loginResponse = new UsuarioResponse();
			loginResponse.setCode(Response.ERRO_DESCONHECIDO);
			loginResponse.setMessage("Erro ao autenticar o usuário");			
			loginResponse.setSucesso(false);
			return loginResponse;
			
		}
		 
	 }
	 
	 private Usuario verificarSeUsuarioExiste(String socialId, String emailLogin, String regGCM, String imagePerfil){
		 
		 Usuario usuario = this.usuarioRepository.findBySocialId(Long.valueOf(socialId));
		 if(usuario != null && usuario.getId() != null){
			 return usuario;
		 }
		 
		 usuario = this.usuarioRepository.findByLogin(emailLogin);
		 if(usuario != null && usuario.getId() != null){
			 usuario.setSocialId(Long.valueOf(socialId));
			 usuario = this.usuarioRepository.save(usuario);
			 return usuario;
		 }
		 
		// usuario = this.usuarioRepository.findByRegIdGCM(regGCM);
		 usuario = this.usuarioRepository.findByLogin("wellington"); //TODO: so para teste
		 if(usuario != null && usuario.getId() != null){
			 usuario.setSocialId(Long.valueOf(socialId));
			 usuario.setLogin(emailLogin);
			 usuario = this.usuarioRepository.save(usuario);
			return usuario; 
		 }
		 
		return usuario; 
	 }

}
