package br.com.mobilenow.domain;

import br.com.servicebox.common.domain.TipoServico;
import android.os.Parcel;

public class Carona extends Servico{	
	
	public Carona() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Integer getTipoServico() {		
		return TipoServico.CARONA.getCodigo();
	}
	
	public Carona(Parcel source) {
	    super(source);
	    //mString = source.readString();
	}
	
	public  String obterMensagem(){
		return "Você pediu uma Carona.";
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	    dest.writeInt(TipoServico.CARONA.getCodigo());
	    super.writeToParcel(dest, flags);
	    //dest.writeString(mString);
	}

	@Override
	public String toString() {
	    return super.toString().concat("SErvico: " + TipoServico.CARONA.getDescricao());
	}
	
	

}
