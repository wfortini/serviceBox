package br.com.webnow.domain;

import org.neo4j.graphdb.RelationshipType;

public enum TipoRelacionamento implements RelationshipType{
	
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
