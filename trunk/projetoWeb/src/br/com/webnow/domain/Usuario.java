package br.com.webnow.domain;

import static org.neo4j.graphdb.Direction.INCOMING;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
@TypeAlias("Usuario")
public class Usuario extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 179224882566814808L;	
	
	@Indexed(unique = true, indexType = IndexType.SIMPLE)
	private String login;
	
	private String password;
	
	@Indexed
	private String nome;
	
	private String sobreNome;
	private String sexo;
	private String apelido;
	private String fotoPerfil;
	private Date dataCadastro;
	private String telefone;	

	@RelatedTo(type = "AMIGO", direction = Direction.BOTH)
	private Set<Usuario> amigos;
	
	@RelatedTo(type = "SERVICO_DISPONIVEL", direction = INCOMING)
	@Fetch
	private Set<Servico> servicosDisponiveis;
	
	@RelatedTo(type = "PRESTA_SERVICO", direction = INCOMING)	
	private Set<PrestarServico> prestarServicos;
		
	@RelatedTo(type = "FOI_RECOMENDADO", direction = INCOMING)
	private Set<Recomentacao> recomendacoes;
	
	public Set<Usuario> getAmigos() {
		return amigos;
	}
	
	public boolean addPrestarServico(PrestarServico servico){
    	return this.prestarServicos.add(servico);
    }

    public boolean addServico(Servico servico){
    	return this.servicosDisponiveis.add(servico);
    }
    
    public boolean removeServico(Servico servico){
    	return this.servicosDisponiveis.remove(servico);
    }
    
    public boolean isPrestaServico(Servico servico){
    	return servico != null && getServicosDisponiveis().contains(servico);
    }   

    public Collection<PrestarServico> getPrestarServicos() {
        return IteratorUtil.asCollection(this.prestarServicos);
    }

    
	public boolean addAmigos(Usuario amigo) {
		return this.amigos.add(amigo);
	}
	
	public boolean isAmigo(Usuario other) {
        return other!=null && getAmigos().contains(other);
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
	
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
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
	
	public Set<Servico> getServicosDisponiveis() {
		return servicosDisponiveis;
	}	
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		
		return "Node Id: " + this.getId() + ", Login: " + this.getLogin() + ", Nome: " + this.getNome();
	}

	public Set<Recomentacao> getRecomendacoes() {
		return recomendacoes;
	}

	public void setRecomendacoes(Set<Recomentacao> recomendacoes) {
		this.recomendacoes = recomendacoes;
	}
	
	

}
