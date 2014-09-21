package br.com.servicebox.common.net;

import java.util.Date;

public class ServicoResponse extends Response{

	private static final long serialVersionUID = 6931501729491768343L;
	
	private Boolean servicoDisponivel;
	private Date dataInicialPrestacao;	
	private Integer tipoServico;
	
	public ServicoResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public ServicoResponse(boolean sucesso, String message, Long nodeId, int code){
		this.setSucesso(sucesso);
		this.setMessage(message);
		this.setNodeId(nodeId);
		this.setCode(code);
	}

	public ServicoResponse(boolean sucesso, String message, Long nodeId,
			int code, Long nodeId2, Boolean servicoDisponivel,
			Date dataInicialPrestacao, Integer tipoServico) {
		super(sucesso, message, nodeId, code);		
		this.servicoDisponivel = servicoDisponivel;
		this.dataInicialPrestacao = dataInicialPrestacao;
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

	public Integer getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(Integer tipoServico) {
		this.tipoServico = tipoServico;
	}
	
	
	
	

}
