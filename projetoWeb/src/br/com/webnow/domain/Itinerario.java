package br.com.webnow.domain;


public class Itinerario{	
	
	private GeoPartida partida;
	private GeoDestino destino;
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	private Double distanciaPartidaDestino;
	private Double distanciaMaxima;
	
    public Itinerario() {
		// TODO Auto-generated constructor stub
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


	public GeoPartida getPartida() {
		return partida;
	}

	public void setPartida(GeoPartida partida) {
		if(partida != null)
		   this.partida = partida;
	}

	public GeoDestino getDestino() {
		return destino;
	}

	public void setDestino(GeoDestino destino) {
		if (destino != null)
		    this.destino = destino;
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
