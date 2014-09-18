package br.com.servicebox.common.domain;

import java.io.Serializable;

public class Localizacao implements Serializable{

	
	private static final long serialVersionUID = -3476601097807515677L;
	
	private double longitude;
	private double latitude;
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
	

}
