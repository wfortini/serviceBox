package br.com.webnow.domain;

import static org.neo4j.graphdb.Direction.INCOMING;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.data.neo4j.template.Neo4jOperations;

import br.com.servicebox.common.domain.Itinerario;
import br.com.servicebox.common.domain.Planejamento;
import br.com.webnow.util.ServiceBoxWebUtil;

@NodeEntity
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 179224882566814808L;

	@GraphId
	private Long nodeId;	
	
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
	
	@RelatedTo(type = "AMIGO", direction = Direction.BOTH)
	private Set<Usuario> amigos;
	
	@RelatedTo(type = "SERVICO_DISPONIVEL", direction = INCOMING)
	@Fetch
	private Set<Servico> servicosDisponiveis;
	
	@RelatedToVia(type = "PRESTA_SERVICO")
	@Fetch
	private Iterable<PrestarServico> prestarServicos;
	
	public Set<Usuario> getAmigos() {
		return amigos;
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
    
    public PrestarServico iniciarPrestacao(Neo4jOperations template, Servico servico, Itinerario itinerario, Planejamento planejamento) {
        PrestarServico prestar = template.createRelationshipBetween(this, servico, PrestarServico.class, "PRESTA_SERVICO", false);
        prestar.setAtiva(true);
        prestar.setData(new Date());
        prestar = ServiceBoxWebUtil.preencherPrestacao(prestar, itinerario, planejamento);        
        return template.save(prestar);
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
	
	
	public Set<Servico> getServicosDisponiveis() {
		return servicosDisponiveis;
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
