package br.com.webnow.net;

import br.com.servicebox.common.net.interfaces.IDestino;

public class Destino implements IDestino{
	

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
