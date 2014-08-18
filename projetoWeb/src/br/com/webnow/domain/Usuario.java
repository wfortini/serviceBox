package br.com.webnow.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

import static org.neo4j.graphdb.Direction.INCOMING;

@NodeEntity
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 179224882566814808L;

	@GraphId
	private Long nodeId;
	
	@Indexed(unique = true)
	private String login;
	
	private String password;
	
	@Indexed(indexName = "idx_usuario_nome", indexType = IndexType.FULLTEXT)
	private String nome;
	
	private String sobreNome;
	private String sexo;
	private String apelido;
	private String fotoPerfil;
	private Date dataCadastro;
	
	@RelatedTo(type = "AMIGO", direction = Direction.BOTH)
	private Set<Usuario> amigos;
	
	@RelatedTo(type = "PRESTA_SERVICO", direction = INCOMING)
	private Set<Servico> servicosPrestados;
	
	public Set<Usuario> getAmigos() {
		return amigos;
	}

    public void addServico(Servico servico){
    	this.servicosPrestados.add(servico);
    }
    
    public boolean isPrestaServico(Servico servico){
    	return servico != null && getServicosPrestados().contains(servico);
    }
    
	public void addAmigos(Usuario amigo) {
		this.amigos.add(amigo);
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
	
	
	public Set<Servico> getServicosPrestados() {
		return servicosPrestados;
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
