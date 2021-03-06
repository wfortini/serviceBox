package br.com.webnow.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

import br.com.servicebox.common.domain.TipoServico;


@NodeEntity
public abstract class Servico implements Serializable{
	
	private static final long serialVersionUID = 7403125160671707264L;	
	
	@GraphId
	private Long id;
	
	private Boolean servicoDisponivel;
	private Date dataInicialPrestacao;
	
	@Indexed(unique = true,indexName="idServico", indexType= IndexType.SIMPLE)
	private Integer tipoServico;	
	
	public Servico() {
		// TODO Auto-generated constructor stub
	}	
	
	public Integer getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(Integer tipoServico) {
		this.tipoServico = tipoServico;
	}

	public Boolean getServicoDisponivel() {
		return servicoDisponivel;
	}

	public void setServicoDisponivel(Boolean servicoDisponivel) {
		this.servicoDisponivel = servicoDisponivel;
	}

	public Date getDataInicialPrestacao() {
		return dataInicialPrestacao;
	}

	public void setDataInicialPrestacao(Date dataInicialPrestacao) {
		this.dataInicialPrestacao = dataInicialPrestacao;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Servico person = (Servico) o;
        if (tipoServico == null) return super.equals(o);
        return tipoServico.equals(person.tipoServico);

    }
	
	@Override
    public int hashCode() {
        return tipoServico != null ? tipoServico.hashCode() : super.hashCode();
    }
	
	@Override
    public String toString() {
        return String.format("%s [%s]", TipoServico.getTipoServico(this.tipoServico).getDescricao(), this.getId());
    }
	
	public static Servico getInstance(Integer tipoServico){
		
		TipoServico tipo = TipoServico.getTipoServico(tipoServico);
		Servico retorno = null;
		switch (tipo) {
		case CARONA:{
			retorno = new Carona();
			retorno.setTipoServico(tipo.getCodigo());
			break;
		}
		case REBOQUE:{
			retorno = new Reboque();
			retorno.setTipoServico(tipo.getCodigo());
			break;
		}
		case ESTACIONAMENTO:{
			retorno = new Estacionamento();
			retorno.setTipoServico(tipo.getCodigo());
			break;
		}
		default:
			retorno = null;
		}		
		
		return retorno;
		
	}

}
