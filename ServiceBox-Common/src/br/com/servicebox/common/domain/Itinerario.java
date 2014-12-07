package br.com.servicebox.common.domain;

import java.io.Serializable;


public class Itinerario implements Serializable{
	
	
	private static final long serialVersionUID = 9102384632516503737L;
	private Partida partida;
	private Destino destino;
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	private Double distanciaPartidaDestino;
	private Double distanciaMaxima;
	
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
	public Double getDistanciaPartidaDestino() {
		return distanciaPartidaDestino;
	}
	public void setDistanciaPartidaDestino(Double distanciaPartidaDestino) {
		this.distanciaPartidaDestino = distanciaPartidaDestino;
	}
	public Double getDistanciaMaxima() {
		return distanciaMaxima;
	}
	public void setDistanciaMaxima(Double distanciaMaxima) {
		this.distanciaMaxima = distanciaMaxima;
	}
	
	
	

}
