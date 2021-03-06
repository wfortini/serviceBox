package br.com.servicebox.common.net;

import android.os.Parcel;
import android.os.Parcelable;

public class Itinerario implements Parcelable{	
	
	private Partida partida;
	private Destino destino;
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;	
	
    public Itinerario() {
		// TODO Auto-generated constructor stub
	}
    
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		if(partida != null)
		   this.partida = partida;
	}

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		if (destino != null)
		    this.destino = destino;
	}

	public boolean isSoAmigos() {
		return soAmigos;
	}

	public void setSoAmigos(boolean soAmigos) {
		this.soAmigos = soAmigos;
	}

	public boolean isSoAmigosDosAmigos() {
		return soAmigosDosAmigos;
	}

	public void setSoAmigosDosAmigos(boolean soAmigosDosAmigos) {
		this.soAmigosDosAmigos = soAmigosDosAmigos;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
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
		dest.writeParcelable(destino, flags);
		dest.writeParcelable(partida, flags);
		dest.writeByte((byte) (soAmigos ? 1 : 0));
		dest.writeByte((byte) (soAmigosDosAmigos ? 1 : 0));
		dest.writeByte((byte) (todos ? 1 : 0));
		
	}
	
	private Itinerario(Parcel in) {
		destino = in.readParcelable(getClass().getClassLoader());
        partida = in.readParcelable(getClass().getClassLoader());        
        soAmigos = in.readByte() == 1;
        soAmigosDosAmigos = in.readByte() == 1;
        todos = in.readByte() == 1;
    }
	
	 public static final Parcelable.Creator<Itinerario> CREATOR = new Parcelable.Creator<Itinerario>() {
	        @Override
	        public Itinerario createFromParcel(Parcel in) {
	            return new Itinerario(in);
	        }

	        @Override
	        public Itinerario[] newArray(int size) {
	            return new Itinerario[size];
	        }
	    };

}
