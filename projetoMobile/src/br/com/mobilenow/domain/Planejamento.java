package br.com.mobilenow.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Planejamento implements Parcelable{
	
	private boolean segunda;
	private boolean terca;
	private boolean quarta;
	private boolean quinta;
	private boolean sexta;
	private boolean sabado;
	private boolean domingo;
	private String horaFixa;
	private String horaEntre;
	private String horaE;
	private Integer lugares = 0;
	
	public Planejamento() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (segunda ? 1 : 0));
		dest.writeByte((byte) (terca ? 1 : 0));
		dest.writeByte((byte) (quarta ? 1 : 0));
		dest.writeByte((byte) (quinta ? 1 : 0));
		dest.writeByte((byte) (sexta ? 1 : 0));
		dest.writeByte((byte) (sabado ? 1 : 0));
		dest.writeByte((byte) (domingo ? 1 : 0));
		dest.writeString(horaFixa);
		dest.writeString(horaEntre);
		dest.writeString(horaE);
		dest.writeInt(lugares);
		
	}
	
	private Planejamento(Parcel in) {
		segunda = in.readByte() == 1;
		terca = in.readByte() == 1;
		quarta = in.readByte() == 1;
		quinta = in.readByte() == 1;
		sexta = in.readByte() == 1;
		sabado = in.readByte() == 1;
		domingo = in.readByte() == 1;
		horaFixa = in.readString();
		horaEntre = in.readString();
		horaE = in.readString();
		lugares = in.readInt();
    }
	
	 public static final Parcelable.Creator<Planejamento> CREATOR = new Parcelable.Creator<Planejamento>() {
	        @Override
	        public Planejamento createFromParcel(Parcel in) {
	            return new Planejamento(in);
	        }

	        @Override
	        public Planejamento[] newArray(int size) {
	            return new Planejamento[size];
	        }
	    };


	public boolean isSegunda() {
		return segunda;
	}

	public void setSegunda(boolean segunda) {
		this.segunda = segunda;
	}

	public boolean isTerca() {
		return terca;
	}

	public void setTerca(boolean terca) {
		this.terca = terca;
	}

	public boolean isQuarta() {
		return quarta;
	}

	public void setQuarta(boolean quarta) {
		this.quarta = quarta;
	}

	public boolean isQuinta() {
		return quinta;
	}

	public void setQuinta(boolean quinta) {
		this.quinta = quinta;
	}

	public boolean isSexta() {
		return sexta;
	}

	public void setSexta(boolean sexta) {
		this.sexta = sexta;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}

	public String getHoraFixa() {
		return horaFixa;
	}

	public void setHoraFixa(String horaFixa) {
		this.horaFixa = horaFixa;
	}

	public String getHoraEntre() {
		return horaEntre;
	}

	public void setHoraEntre(String horaEntre) {
		this.horaEntre = horaEntre;
	}

	public String getHoraE() {
		return horaE;
	}

	public void setHoraE(String horaE) {
		this.horaE = horaE;
	}


	public Integer getLugares() {
		return lugares;
	}


	public void setLugares(Integer lugares) {
		this.lugares = lugares;
	}
	    
	    

}
