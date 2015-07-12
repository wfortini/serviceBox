package br.com.servicebox.common.net;

import java.util.Date;
import java.util.Set;

import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.json.ServicoJSON;

/**
 * Classe utilizada no transporte das informação 
 * do login durante autenticação do usuario (ServiceBox Commons)
 * @author wpn0510
 *
 */
public class UsuarioResponse extends Response{
	
	private static final long serialVersionUID = -2114605692059685414L;	
	
	 private Credenciais[] mCredenciais;	
	 private Long nodeId;	
	 private String login;	
	 private String password;	
	 private String nome;	
	 private String sobreNome;
	 private String sexo;
	 private String apelido;
	 private String fotoPerfil;
	 private Date dataCadastro;
	 private String telefone;
	 private String regIdGCM;
	 private Long socialId;
	 
	 private ServicoJSON[] servicoJSONs;
	
	public UsuarioResponse() {
		
	} 
	
	public UsuarioResponse(boolean sucesso, String message, Long nodeId, int code){
		this.setSucesso(sucesso);
		this.setMessage(message);
		this.setNodeId(nodeId);
		this.setCode(code);
	}
   
     
    
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Credenciais[] getmCredenciais() {
		return mCredenciais;
	}

	public void setmCredenciais(Credenciais[] mCredenciais) {
		this.mCredenciais = mCredenciais;
	} 
	
	public ServicoJSON[] getServicoJSONs() {
		return servicoJSONs;
	}
	public void setServicoJSONs(ServicoJSON[] servicoJSONs) {
		this.servicoJSONs = servicoJSONs;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getRegIdGCM() {
		return regIdGCM;
	}



	public void setRegIdGCM(String regIdGCM) {
		this.regIdGCM = regIdGCM;
	}

	public Long getSocialId() {
		return socialId;
	}

	public void setSocialId(Long socialId) {
		this.socialId = socialId;
	}
	
	
	
	
	

}
