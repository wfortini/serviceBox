package br.com.webnow.returno.cypher;

import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Usuario;

public class ServicoLocalizado implements IServicoLocalizado{
	
	private Usuario usuario;
	private PrestarServico prestarServico;
	private GeoPartida geoPartida;
	private GeoDestino geoDestino;
	private Long codigo;
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public PrestarServico getPrestarServico() {
		return prestarServico;
	}
	public void setPrestarServico(PrestarServico prestarServico) {
		this.prestarServico = prestarServico;
	}
	public GeoPartida getGeoPartida() {
		return geoPartida;
	}
	public void setGeoPartida(GeoPartida geoPartida) {
		this.geoPartida = geoPartida;
	}
	public GeoDestino getGeoDestino() {
		return geoDestino;
	}
	public void setGeoDestino(GeoDestino geoDestino) {
		this.geoDestino = geoDestino;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	

}
