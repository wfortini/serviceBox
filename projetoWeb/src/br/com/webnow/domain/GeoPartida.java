package br.com.webnow.domain;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 * Entidade Partida do Itinerario ( Projeto Web )
 * @author wellington
 *
 */
@NodeEntity
@TypeAlias("GEOPARTIDA")
public class GeoPartida extends BaseEntity implements Serializable{
	
		
	private static final long serialVersionUID = -7444625527145174350L;
	public static final String PARTIDA_GEOSPATIAL_INDEX = "localPartida";
		
	private String enderecoPartida;
	private Double latitude;
	private Double longitude;	
	
	private void updateWkt(){
		Locale enLocale = new Locale("en", "EN");
	    this.wkt = String.format(enLocale, "POINT( %.2f %.2f )", this.getLongitude(), this.getLatitude());
	}
	
	@Indexed(indexName="localPartida", indexType=IndexType.POINT)
	private String wkt;
	
	public void setWkt(double longitude, double latitude) {
		this.setLongitude(longitude);
	    this.setLatitude(latitude);
	    
	    this.updateWkt();
	}
	
	public String getWkt() {
		return wkt;
	}
	
	public String getEnderecoPartida() {
		return enderecoPartida;
	}
	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
		this.updateWkt();
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
		this.updateWkt();
	}
	
	

}
