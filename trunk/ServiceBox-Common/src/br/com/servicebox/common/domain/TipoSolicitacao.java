package br.com.servicebox.common.domain;

public enum TipoSolicitacao {
	
	 CARONA("Carona", 1),
	 REBOQUE("Reboque", 2),
	 ESTACIONAMENTO("Estacionamento", 3),
	 AMIZADE("Amizade", 4);
	 
	 private final String descricao;
	 private final Integer codigo;
	 
	 TipoSolicitacao(String descricao, Integer codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
	 
	 public String getDescricao() {
			return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	 
	 public static TipoSolicitacao getTipoSolicitacao(Integer codServico){
			
			for(TipoSolicitacao t : TipoSolicitacao.values()){
				if(t.getCodigo().equals(codServico)){
					return t;
				}
			}
			return null;
		}

}
