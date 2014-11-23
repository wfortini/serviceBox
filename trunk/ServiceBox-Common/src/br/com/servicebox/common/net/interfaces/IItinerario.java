package br.com.servicebox.common.net.interfaces;


public interface IItinerario {
	
	public IPartida getPartida();

	public void setPartida(IPartida partida);

	public IDestino getDestino(); 

	public void setDestino(IDestino destino);

	public boolean isSoAmigos(); 

	public void setSoAmigos(boolean soAmigos); 

	public boolean isSoAmigosDosAmigos();

	public void setSoAmigosDosAmigos(boolean soAmigosDosAmigos); 

	public boolean isTodos();

	public void setTodos(boolean todos);

}
