package br.com.webnow.returno.cypher;

import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Usuario;

public interface IServicoLocalizado {
	
	Usuario getUsuario();
	PrestarServico getPrestarServico();
	GeoDestino getGeoDestino();
	GeoPartida getGeoPartida();
	Long getCodigo();
	void setCodigo(Long param);

}
