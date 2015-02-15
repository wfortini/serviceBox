package br.com.servicebox.common.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Classe Usuario Modulo Commons
 * @author wpn0510
 *
 */
public class Usuario {	
	
	private Long nodeId;
	
	private String login;
	
	private String password;	
	
	private String nome;
	private String sobreNome;
	private String sexo;
	private String apelido;
	private String fotoPerfil;
	private Date dataCadastro;	
	private Set<Usuario> amigos;	
	private Set<Servico> servicosDisponiveis;	
	
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public boolean addServico(Servico servico){
		if(this.servicosDisponiveis != null){
			return this.servicosDisponiveis.add(servico);
		}else{
			this.servicosDisponiveis = new HashSet<Servico>();
			return this.servicosDisponiveis.add(servico);	
		}
    	
    }
	
	public boolean addAllServico(Collection<? extends Servico> servicos){
    	
    	if(this.servicosDisponiveis != null){
    		return this.servicosDisponiveis.addAll(servicos);
		}else{
			this.servicosDisponiveis = new HashSet<Servico>();
			return this.servicosDisponiveis.addAll(servicos);
		}
    }
    
    public boolean isPrestaServico(Servico servico){
    	return servico != null && getServicosDisponiveis().contains(servico);
    }
    

	public Set<Servico> getServicosDisponiveis() {
		return servicosDisponiveis;
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

	public Set<Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(Set<Usuario> amigos) {
		this.amigos = amigos;
	}

	public Usuario(String login, String password, String nome,
			String sobreNome, String sexo, String apelido) {
		super();
		this.login = login;
		this.password = password;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.sexo = sexo;
		this.apelido = apelido;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario user = (Usuario) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(user.nodeId);

    }
	
	@Override
    public int hashCode() {

        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }
	
	@Override
	public String toString() {
		
		return "Node Id: " + this.getNodeId() + ", Login: " + this.getLogin() + ", Nome: " + this.getNome();
	}

}
