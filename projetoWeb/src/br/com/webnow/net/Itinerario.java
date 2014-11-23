package br.com.webnow.net;

import br.com.servicebox.common.net.interfaces.IDestino;
import br.com.servicebox.common.net.interfaces.IItinerario;
import br.com.servicebox.common.net.interfaces.IPartida;

public class Itinerario implements IItinerario{
	
	private Partida partida;
	private Destino destino;
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	
	public Partida getPartida() {
		return partida;
	}
	public void setPartida(IPartida partida) {
		if(partida != null)
		   this.partida = (Partida) partida;
	}
	public Destino getDestino() {
		return destino;
	}
	public void setDestino(IDestino destino) {
		if(destino != null)
		     this.destino = (Destino) destino;
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
