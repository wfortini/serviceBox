package br.com.servicebox.common.net.interfaces;

public interface IPlanejamento {
	
	public boolean isSegunda();

	public void setSegunda(boolean segunda);

	public boolean isTerca();

	public void setTerca(boolean terca);

	public boolean isQuarta();

	public void setQuarta(boolean quarta);

	public boolean isQuinta() ;

	public void setQuinta(boolean quinta);

	public boolean isSexta() ;

	public void setSexta(boolean sexta);

	public boolean isSabado();

	public void setSabado(boolean sabado);

	public boolean isDomingo();

	public void setDomingo(boolean domingo);

	public String getHoraFixa();

	public void setHoraFixa(String horaFixa) ;

	public String getHoraEntre();

	public void setHoraEntre(String horaEntre) ;

	public String getHoraE();

	public void setHoraE(String horaE);

}
