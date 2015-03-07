package br.com.mobilenow.util;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.mobilenow.domain.Itinerario;
import br.com.mobilenow.domain.Planejamento;
import br.com.mobilenow.domain.Usuario;

import com.google.android.gms.plus.model.people.Person.PlacesLived;

public class Info implements Parcelable{
	
	private Usuario usuario;
	private Itinerario itinerario;
	private Planejamento planejamento;
	
	public Info() {
		// TODO Auto-generated constructor stub
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
		dest.writeParcelable(usuario, flags);
		dest.writeParcelable(itinerario, flags);
		dest.writeParcelable(planejamento, flags);
		
	}
	
	private Info(Parcel in) {
		usuario = in.readParcelable(getClass().getClassLoader());
        itinerario = in.readParcelable(getClass().getClassLoader());  
        planejamento = in.readParcelable(getClass().getClassLoader());
        
    }
	
	 public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
	        @Override
	        public Info createFromParcel(Parcel in) {
	            return new Info(in);
	        }

	        @Override
	        public Info[] newArray(int size) {
	            return new Info[size];
	        }
	    };

	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Itinerario getItinerario() {
		return itinerario;
	}


	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}


	public Planejamento getPlanejamento() {
		return planejamento;
	}


	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}

	    
	    
}
