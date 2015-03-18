package br.com.webnow.domain;

import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
@TypeAlias("recomendacao")
public class Recomentacao extends BaseEntity{
	
	
	@RelatedTo(type = "RECOMENDOU_ALGUEM", direction = Direction.BOTH)
	private Usuario usuarioQueRecomenda;
	
	@RelatedTo(type = "RECOMENDOU_ALGO", direction = Direction.BOTH)
	private Servico servicoRecomendado;
	
	private Date dataRecomendacao;
	private String recomendacao;
	
	
	public Recomentacao() {
		
	}
	
	
	public Usuario getUsuarioQueRecomenda() {
		return usuarioQueRecomenda;
	}
	public void setUsuarioQueRecomenda(Usuario usuarioQueRecomenda) {
		this.usuarioQueRecomenda = usuarioQueRecomenda;
	}
	public Servico getServicoRecomendado() {
		return servicoRecomendado;
	}
	public void setServicoRecomendado(Servico servicoRecomendado) {
		this.servicoRecomendado = servicoRecomendado;
	}
	public Date getDataRecomendacao() {
		return dataRecomendacao;
	}
	public void setDataRecomendacao(Date dataRecomendacao) {
		this.dataRecomendacao = dataRecomendacao;
	}
	public String getRecomendacao() {
		return recomendacao;
	}
	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
	}
	
	
	

}
