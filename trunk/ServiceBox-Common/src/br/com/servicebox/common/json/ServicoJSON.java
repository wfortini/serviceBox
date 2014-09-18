package br.com.servicebox.common.json;

import java.util.Date;

public class ServicoJSON {
	
	private Long nodeId;
	 private Boolean servicoDisponivel;
	 private Date dataInicialPrestacao;
	 private Integer tipoServico;
	 
	 public ServicoJSON() {
		
	}	 
	 
	public ServicoJSON(Long nodeId, Boolean servicoDisponivel,
			Date dataInicialPrestacao, Integer tipoServico) {
		super();
		this.nodeId = nodeId;
		this.servicoDisponivel = servicoDisponivel;
		this.dataInicialPrestacao = dataInicialPrestacao;
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
	public Integer getTipoServico() {
		return tipoServico;
	}
	public void setTipoServico(Integer tipoServico) {
		this.tipoServico = tipoServico;
	}

}
