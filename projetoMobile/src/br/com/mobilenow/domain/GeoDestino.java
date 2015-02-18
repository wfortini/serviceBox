package br.com.mobilenow.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoDestino implements Parcelable{

	
	private String enderecoDestino;
	private Double latitude;
	private Double longitude;
	
	
	public GeoDestino() {
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
	
    private GeoDestino(Parcel in) {
        this.enderecoDestino = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }
	
	 public static final Parcelable.Creator<GeoDestino> CREATOR = new Parcelable.Creator<GeoDestino>() {
	        @Override
	        public GeoDestino createFromParcel(Parcel in) {
	            return new GeoDestino(in);
	        }

	        @Override
	        public GeoDestino[] newArray(int size) {
	            return new GeoDestino[size];
	        }
	    };

}
