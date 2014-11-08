package br.com.servicebox.common.net;

import android.os.Parcel;
import android.os.Parcelable;

public class Destino implements Parcelable{

	
	private String enderecoDestino;
	
	public Destino() {
		// TODO Auto-generated constructor stub
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
		
	}
	
    private Destino(Parcel in) {
        this.enderecoDestino = in.readString();
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
