package br.com.servicebox.common.domain;

public enum StatusSolicitacao {
	
	 SOLICITACAO_ENVIADA("Solicita��o enviada", 1),
	 SOLICITACAO_ACEITA("solicita��o aceita", 2),
	 SOLICITACAO_NEGADA("Solicita��o negada", 3);
	 
	 
	 private final String descricao;
	 private final Integer codigo;
	 
	 StatusSolicitacao(String descricao, Integer codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
	 
	 public String getDescricao() {
			return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	 
	 public static StatusSolicitacao getStatusSolicitacao(Integer codServico){
			
			for(StatusSolicitacao t : StatusSolicitacao.values()){
				if(t.getCodigo().equals(codServico)){
					return t;
				}
			}
			return null;
		}

}
