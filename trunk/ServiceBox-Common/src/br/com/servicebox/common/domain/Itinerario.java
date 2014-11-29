package br.com.servicebox.common.domain;


public class Itinerario{
	
	private Partida partida;
	private Destino destino;
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	
	public Partida getPartida() {
		return partida;
	}
	public void setPartida(Partida partida) {
		if(partida != null)
		   this.partida =  partida;
	}
	public Destino getDestino() {
		return destino;
	}
	public void setDestino(Destino destino) {
		if(destino != null)
		     this.destino =  destino;
	}
	public boolean isSoAmigos() {
		return soAmigos;
	}
	public void setSoAmigos(boolean soAmigos) {
		this.soAmigos = soAmigos;
	}
	public boolean isSoAmigosDosAmigos() {
		return soAmigosDosAmigos;
	}
	public void setSoAmigosDosAmigos(boolean soAmigosDosAmigos) {
		this.soAmigosDosAmigos = soAmigosDosAmigos;
	}
	public boolean isTodos() {
		return todos;
	}
	public void setTodos(boolean todos) {
		this.todos = todos;
	}
	
	
	

}
