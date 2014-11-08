package br.com.servicebox.common.net;

import android.os.Parcel;
import android.os.Parcelable;

public class Partida implements Parcelable{
	
	private String enderecoPartida;
	
	
	
	public String getEnderecoPartida() {
		return enderecoPartida;
	}

	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}

	public Partida() {
		// TODO Auto-generated constructor stub
	}
	
     private Partida(Parcel in) {
        this.enderecoPartida = in.readString();
    }
	
	 public static final Parcelable.Creator<Partida> CREATOR = new Parcelable.Creator<Partida>() {
	        @Override
	        public Partida createFromParcel(Parcel in) {
	            return new Partida(in);
	        }

	        @Override
	        public Partida[] newArray(int size) {
	            return new Partida[size];
	        }
	    };


	@Override
	public int describeContents() {		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(enderecoPartida);
		
	}
	
	

}
