package br.com.servicebox.common.domain;

public enum TipoAcesso {
	
	FACEBOOK("FaceBook", 1),
	 SERVICEBOX("ServiceBox", 2),
	 GOOGLEPLUS("Google Plus", 3);
	 
	 private final String descricao;
	 private final Integer codigo;
	 
	 TipoAcesso(String descricao, Integer codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	 
	public static TipoAcesso getTipoServico(Integer codServico){
		
		for(TipoAcesso t : TipoAcesso.values()){
			if(t.getCodigo().equals(codServico)){
				return t;
			}
		}
		return null;
	}

}
