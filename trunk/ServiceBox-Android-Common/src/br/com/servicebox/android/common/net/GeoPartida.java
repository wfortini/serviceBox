package br.com.servicebox.android.common.net;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoPartida implements Parcelable{
	
	private String enderecoPartida;
	private Double latitude;
	private Double longitude;
	
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

	public String getEnderecoPartida() {
		return enderecoPartida;
	}

	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}

	public GeoPartida() {
		// TODO Auto-generated constructor stub
	}
	
     private GeoPartida(Parcel in) {
        this.enderecoPartida = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }
	
	 public static final Parcelable.Creator<GeoPartida> CREATOR = new Parcelable.Creator<GeoPartida>() {
	        @Override
	        public GeoPartida createFromParcel(Parcel in) {
	            return new GeoPartida(in);
	        }

	        @Override
	        public GeoPartida[] newArray(int size) {
	            return new GeoPartida[size];
	        }
	    };


	@Override
	public int describeContents() {		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(enderecoPartida);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		
	}
	
	

}
