package br.com.servicebox.common.domain;

import java.io.Serializable;


public class Partida implements Serializable{
	
	
	private static final long serialVersionUID = -7444625527145174350L;
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
