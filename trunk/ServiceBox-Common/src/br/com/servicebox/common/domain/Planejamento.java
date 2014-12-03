package br.com.servicebox.common.domain;

import java.io.Serializable;


public class Planejamento implements Serializable{
	
	
	private static final long serialVersionUID = -8718877526125098226L;
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
	
	

}
