package br.com.webnow.domain;

import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
@TypeAlias("recomendacao")
public class Recomendacao extends BaseEntity{
	
	
	@RelatedTo(type = "RECOMENDOU_ALGUEM", direction = Direction.BOTH)
	private Usuario usuarioQueRecomenda;
	
	@RelatedTo(type = "RECOMENDOU_ALGO", direction = Direction.BOTH)
	private Servico servicoRecomendado;
	
	private Date dataRecomendacao;
	private boolean recomenda;
	private String comentario;
	
	
	public Recomendacao() {
		
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


	public boolean isRecomenda() {
		return recomenda;
	}


	public void setRecomenda(boolean recomenda) {
		this.recomenda = recomenda;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@Override
	  public boolean equals(Object obj)
	  {
	    if (this == obj)
	    {
	      return true;
	    }

	    if (usuarioQueRecomenda == null || usuarioQueRecomenda.getId() == null 
	    		|| obj == null || !getClass().equals(obj.getClass()))
	    {
	      return false;
	    }
	    
	    return usuarioQueRecomenda.getId().equals(((Recomendacao) obj).getUsuarioQueRecomenda().getId()) & 
	    		servicoRecomendado.getTipoServico().equals(((Recomendacao) obj).getServicoRecomendado().getTipoServico());
	  }

	  @Override
	  public int hashCode()
	  {
		  if(usuarioQueRecomenda == null)
			  return 0;
		  
	    return (usuarioQueRecomenda.getId() == null ? 0 : usuarioQueRecomenda.getId().hashCode()) 
	    		+ (servicoRecomendado.getTipoServico() == null ? 0 : servicoRecomendado.getTipoServico().hashCode());
	  }
	
	
	

}
