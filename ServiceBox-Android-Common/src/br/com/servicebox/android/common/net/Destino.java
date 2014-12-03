package br.com.servicebox.android.common.net;

import android.os.Parcel;
import android.os.Parcelable;

public class Destino implements Parcelable{

	
	private String enderecoDestino;
	private Double latitude;
	private Double longitude;
	
	
	public Destino() {
		// TODO Auto-generated constructor stub
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


	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	/**
	 * Implementação da interface Parcelable
	 */
	@Override
	public int describeContents() {
				return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(enderecoDestino);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		
	}
	
    private Destino(Parcel in) {
        this.enderecoDestino = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }
	
	 public static final Parcelable.Creator<Destino> CREATOR = new Parcelable.Creator<Destino>() {
	        @Override
	        public Destino createFromParcel(Parcel in) {
	            return new Destino(in);
	        }

	        @Override
	        public Destino[] newArray(int size) {
	            return new Destino[size];
	        }
	    };

}
