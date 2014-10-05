package br.com.servicebox.common.net;

import java.util.Date;
import java.util.Set;

import br.com.servicebox.common.domain.Carona;
import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.domain.Estacionamento;
import br.com.servicebox.common.domain.Reboque;
import br.com.servicebox.common.domain.Servico;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.domain.Usuario;
import br.com.servicebox.common.json.ServicoJSON;

/**
 * Classe utilizada no transporte das informação 
 * do login durante autenticação do usuario (ServiceBox Commons)
 * @author wpn0510
 *
 */
public class LoginResponse extends Response{
	
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
	 private ServicoJSON[] servicoJSONs;
	
	public LoginResponse() {
		
	}    
	
   public LoginResponse(Usuario usuario){
   	
	   	preencherServicoJSON(usuario);			 
	   	this.nodeId = usuario.getNodeId();
	   	this.login = usuario.getLogin();
	   	this.apelido = usuario.getApelido();
	   	this.dataCadastro = usuario.getDataCadastro();
	   	this.fotoPerfil = usuario.getFotoPerfil();
	   	this.nome = usuario.getNome();
	   	this.sexo = usuario.getSexo();   	
	   	
	 }

   private void preencherServicoJSON(Usuario usuario) {
	   
	Set<Servico> lista = usuario.getServicosDisponiveis();
	if(lista != null && lista.size() > 0){
		//this.servicos = lista.toArray(new Servico[lista.size()]);  
		
		int i = 0;
		this.servicoJSONs = new ServicoJSON[lista.size()];
		for(Servico s : lista){
			
			ServicoJSON sj = new ServicoJSON(s.getNodeId(),
					         s.getServicoDisponivel(), s.getDataInicialPrestacao(), s.getTipoServico());
			
			this.servicoJSONs[i] = sj;
			i++;	   			
			
		}		
		
	}
  }      
     /**
      * Monta o Objeto Usuario com seus respectivos atributos List, Set, etc
      * @return Objeto Usuario da pacore br.com.servicebox.common.domain
      */
     public Usuario preencherUsuario(){
    	 
    	 Usuario usuario = new Usuario(this.login, this.password,this.nome,this.sobreNome,this.sexo,this.apelido);
    	 usuario.setNodeId(this.nodeId);
    	 
    	 if (this.servicoJSONs != null){
                 
    		 for(ServicoJSON s : this.servicoJSONs){
        		 
        		 if(s.getTipoServico().equals(TipoServico.CARONA.getCodigo())){
        			 
        			 Carona c = new Carona();
        			 c.setNodeId(s.getNodeId());
        			 c.setDataInicialPrestacao(s.getDataInicialPrestacao());
        			 c.setServicoDisponivel(s.getServicoDisponivel());
        			 c.setTipoServico(s.getTipoServico());
        			 usuario.addServico(c);
        			 
        		 } else if(s.getTipoServico().equals(TipoServico.ESTACIONAMENTO.getCodigo())){
        			 
        			 Estacionamento c = new Estacionamento();
        			 c.setNodeId(s.getNodeId());
        			 c.setDataInicialPrestacao(s.getDataInicialPrestacao());
        			 c.setServicoDisponivel(s.getServicoDisponivel());
        			 c.setTipoServico(s.getTipoServico());
        			 usuario.addServico(c);
        			 
        		 } else if(s.getTipoServico().equals(TipoServico.REBOQUE.getCodigo())){
        			
        			 Reboque c = new Reboque();
        			 c.setNodeId(s.getNodeId());
        			 c.setDataInicialPrestacao(s.getDataInicialPrestacao());
        			 c.setServicoDisponivel(s.getServicoDisponivel());
        			 c.setTipoServico(s.getTipoServico());
        			 usuario.addServico(c);
        		 }
        		 
        	 }
        	 
        
    	 }
    	 
    	 return usuario;
    	 
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
	

}
