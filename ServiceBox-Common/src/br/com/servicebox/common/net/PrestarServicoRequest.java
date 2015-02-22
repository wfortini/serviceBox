package br.com.servicebox.common.net;



public class PrestarServicoRequest extends Request{	
	
	private static final long serialVersionUID = 8497195528947212068L;
	
	private String descricao;
	
	/**
	 * Planejamento
	 */
	private boolean segunda;
	private boolean terca;
	private boolean quarta;
	private boolean quinta;
	private boolean sexta;
	private boolean sabado;
	private boolean domingo;
	private String horaFixa;
	private String horaEntre;
	private String horaE;
	private Integer lugares;
	
	/**
	 * Itinerario
	 */
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	
	/**
	 * Destino
	 */
	private String enderecoDestino;
	private Double latitudeDestino;
	private Double longitudeDestino;
	
	/**
	 * Partida
	 */
	private String enderecoPartida;
	private Double latitudePartida;
	private Double longitudePartida;
	
	private Double distanciaPartidaDestino;
	private Double distanciaMaxima;
	

	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
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



	public boolean isSegunda() {
		return segunda;
	}



	public void setSegunda(boolean segunda) {
		this.segunda = segunda;
	}



	public boolean isTerca() {
		return terca;
	}



	public void setTerca(boolean terca) {
		this.terca = terca;
	}



	public boolean isQuarta() {
		return quarta;
	}



	public void setQuarta(boolean quarta) {
		this.quarta = quarta;
	}



	public boolean isQuinta() {
		return quinta;
	}



	public void setQuinta(boolean quinta) {
		this.quinta = quinta;
	}



	public boolean isSexta() {
		return sexta;
	}



	public void setSexta(boolean sexta) {
		this.sexta = sexta;
	}



	public boolean isSabado() {
		return sabado;
	}



	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}



	public boolean isDomingo() {
		return domingo;
	}



	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}



	public String getHoraFixa() {
		return horaFixa;
	}



	public void setHoraFixa(String horaFixa) {
		this.horaFixa = horaFixa;
	}



	public String getHoraEntre() {
		return horaEntre;
	}



	public void setHoraEntre(String horaEntre) {
		this.horaEntre = horaEntre;
	}



	public String getHoraE() {
		return horaE;
	}



	public void setHoraE(String horaE) {
		this.horaE = horaE;
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



	public String getEnderecoDestino() {
		return enderecoDestino;
	}



	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}



	public Double getLatitudeDestino() {
		return latitudeDestino;
	}



	public void setLatitudeDestino(Double latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}



	public Double getLongitudeDestino() {
		return longitudeDestino;
	}



	public void setLongitudeDestino(Double longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}



	public String getEnderecoPartida() {
		return enderecoPartida;
	}



	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}



	public Double getLatitudePartida() {
		return latitudePartida;
	}



	public void setLatitudePartida(Double latitudePartida) {
		this.latitudePartida = latitudePartida;
	}



	public Double getLongitudePartida() {
		return longitudePartida;
	}



	public void setLongitudePartida(Double longitudePartida) {
		this.longitudePartida = longitudePartida;
	}



	public PrestarServicoRequest() {
		// TODO Auto-generated constructor stub
	}



	public Integer getLugares() {
		return lugares;
	}



	public void setLugares(Integer lugares) {
		this.lugares = lugares;
	}


	
	
	
	
	

}
