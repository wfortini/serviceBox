package br.com.webnow.domain;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import br.com.servicebox.common.domain.Localizacao;

/**
 * Classe que relaciona um usuario a um servico
 * @author wpn0510
 *
 */
@RelationshipEntity(type = "PRESTA_SERVICO")
public class PrestarServico {
	
	@GraphId
	private Long nodeId;
	
	@StartNode
	private Usuario usuario;
	@EndNode
	private Servico servico;
	
	private Localizacao localizacaoInicial;
	private Localizacao localizacaoFinal;
	private Date data;
	private boolean ativa;
	
	public PrestarServico() {
		
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	

	public Localizacao getLocalizacaoInicial() {
		return localizacaoInicial;
	}

	public void setLocalizacaoInicial(Localizacao localizacaoInicial) {
		this.localizacaoInicial = localizacaoInicial;
	}

	public Localizacao getLocalizacaoFinal() {
		return localizacaoFinal;
	}

	public void setLocalizacaoFinal(Localizacao localizacaoFinal) {
		this.localizacaoFinal = localizacaoFinal;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
	
	

}
