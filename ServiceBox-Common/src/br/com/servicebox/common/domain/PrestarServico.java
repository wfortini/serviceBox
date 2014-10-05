package br.com.servicebox.common.domain;

import java.util.Date;

/**
 * Classe que relaciona um usuario a um servico ( Common )
 * @author wpn0510
 *
 */
public class PrestarServico {
	
	
	private Long nodeId;	
	private Usuario usuario;	
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
