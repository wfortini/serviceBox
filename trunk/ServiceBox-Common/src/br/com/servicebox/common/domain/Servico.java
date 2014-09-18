package br.com.servicebox.common.domain;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;



@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "type")  
	@JsonSubTypes({  
	    @Type(value = br.com.servicebox.common.domain.Carona.class, name = "carona"),  
	    @Type(value = br.com.servicebox.common.domain.Reboque.class, name = "reboque"), 
	    @Type(value = br.com.servicebox.common.domain.Estacionamento.class, name = "estacionamento") 
	    
	}) 
public abstract class Servico implements Serializable{
	
	private static final long serialVersionUID = 7403125160671707264L;
	
	
	private Long nodeId;
	private Boolean servicoDisponivel;
	private Date dataInicialPrestacao;
	
	
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



	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Servico person = (Servico) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(person.nodeId);

    }
	
	@Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }
	
	@Override
    public String toString() {
        return String.format("%s [%s]", TipoServico.getTipoServico(this.tipoServico).getDescricao(), nodeId);
    }
	

}
