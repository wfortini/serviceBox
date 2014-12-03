package br.com.servicebox.common.domain;

import java.io.Serializable;



public class Destino implements Serializable{
	

	private static final long serialVersionUID = 6518731629464628395L;
	private String enderecoDestino;
	private Double latitude;
	private Double longitude;
	public String getEnderecoDestino() {
		return enderecoDestino;
	}
	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
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
