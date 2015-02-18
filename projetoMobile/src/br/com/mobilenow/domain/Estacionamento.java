package br.com.mobilenow.domain;

import android.os.Parcel;
import br.com.servicebox.common.domain.TipoServico;

public class Estacionamento extends Servico{
	
	public Estacionamento() {
		// TODO Auto-generated constructor stub
	}

	public Estacionamento(Parcel source) {
	    super(source);
	    //mString = source.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	    dest.writeInt(TipoServico.ESTACIONAMENTO.getCodigo());
	    super.writeToParcel(dest, flags);
	    //dest.writeString(mString);
	}

	@Override
	public String toString() {
	    return super.toString().concat("SErvico: " + TipoServico.ESTACIONAMENTO.getDescricao());
	}
	
}
