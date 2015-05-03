package br.com.webnow.domain;

import static org.neo4j.graphdb.Direction.INCOMING;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Solicitacao extends BaseEntity implements Serializable{

	
	private static final long serialVersionUID = -105851659887399602L;
	
	@RelatedTo(type = "SOLICITOU", direction = INCOMING)
	@Fetch
	private Usuario solicitante;
	
	@RelatedTo(type = "SOLICITADO", direction = INCOMING)
	@Fetch
	private Usuario solicitado;
	
	private Integer tipoSolicitacao;
	private Date dataSolicitacao;
	private String mensagem;
	private Boolean ativa;
	private Integer statusSolicitacao;
	
	@RelatedTo(type = "SOLICITOU_PARA", direction = INCOMING)
	private PrestarServico prestarServico;
	
	public Solicitacao() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Usuario getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}
	public Usuario getSolicitado() {
		return solicitado;
	}
	public void setSolicitado(Usuario solicitado) {
		this.solicitado = solicitado;
	}
	public Integer getTipoSolicitacao() {
		return tipoSolicitacao;
	}
	public void setTipoSolicitacao(Integer tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Boolean getAtiva() {
		return ativa;
	}
	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}
	public Integer getStatusSolicitacao() {
		return statusSolicitacao;
	}
	public void setStatusSolicitacao(Integer statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}


	public PrestarServico getPrestarServico() {
		return prestarServico;
	}


	public void setPrestarServico(PrestarServico prestarServico) {
		this.prestarServico = prestarServico;
	}
	
	
	
	
	
	
	
	
	
	
}
