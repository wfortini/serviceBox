package br.com.webnow.domain;

public enum TipoRelacionamento {
	
	 AMIGO("amigo"),
	 COMENTARIO("comentario"),
	 PRESTA_SERVICO("prestaServico");
	 
	 private final String valor;
	 
	 TipoRelacionamento(String valor) {
		this.valor = valor;
	}
	 
	 public String getValor(){
		 return this.valor;
	 }

}
