package br.com.webnow.domain;

import java.io.Serializable;

public enum TipoServico implements Serializable{
	
	 CARONA("Carona", 1),
	 REBOQUE("Reboque", 2),
	 ESTACIONAMENTO("Estacionamento", 3);
	 
	 private final String descricao;
	 private final Integer codigo;
	 
	 TipoServico(String descricao, Integer codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	 
	public static TipoServico getTipoServico(Integer codServico){
		
		for(TipoServico t : TipoServico.values()){
			if(t.getCodigo().equals(codServico)){
				return t;
			}
		}
		return null;
	}

}
