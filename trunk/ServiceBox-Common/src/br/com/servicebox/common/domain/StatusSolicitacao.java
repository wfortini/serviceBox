package br.com.servicebox.common.domain;

public enum StatusSolicitacao {
	
	 SOLICITACAO_ENVIADA_PARA_SOLICITADO("Solicita��o enviada para o solicitado", 1),
	 SOLICITACAO_ACEITA_PELO_SOLICITADO("solicita��o aceita pelo solicitado", 2),
	 SOLICITACAO_NEGADA_PELO_SOLICITADO("Solicita��o negada pelo solicitado", 3),
	 SOLICITACAO_NAO_ENVIADA_PARA_SOLICITADO("Solicita��o n�o enviada para o solicitado.", 4),
	 
	 SOLICITACAO_ENVIADA_PARA_SOLICITANTE("Solicita��o enviada para o solicitante", 5),
	 SOLICITACAO_ACEITA_PELO_SOLICITANTE("solicita��o aceita pelo solicitante", 6),
	 SOLICITACAO_NEGADA_PELO_SOLICITANTE("Solicita��o negada pelo solicitante", 7),
	 SOLICITACAO_NAO_ENVIADA_PARA_SOLICITANTE("Solicita��o n�o enviada para o solicitante.", 8);	
	 
	 
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
