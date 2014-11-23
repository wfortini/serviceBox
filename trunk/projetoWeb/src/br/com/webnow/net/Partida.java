package br.com.webnow.net;

import br.com.servicebox.common.net.interfaces.IPartida;

public class Partida implements IPartida{
	
	private String enderecoPartida;
	private Double latitude;
	private Double longitude;
	public String getEnderecoPartida() {
		return enderecoPartida;
	}
	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	

}
