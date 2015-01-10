package br.com.webnow.domain;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 * Entidade Destino do Itinerario ( projeto web )
 * @author wellington
 *
 */
@NodeEntity
public class Destino implements Serializable{
	

	private static final long serialVersionUID = 6518731629464628395L;
	
	@GraphId
	private Long nodeId;
	
	private String enderecoDestino;
	private Double latitude;
	private Double longitude;
	
	@Indexed(indexName="localDestino", indexType=IndexType.POINT)
	private String wkt;
	
	private void updateWkt(){
		Locale enLocale = new Locale("en", "EN");
	    this.wkt = String.format(enLocale, "POINT( %.2f %.2f )", this.getLongitude(), this.getLatitude());
	}
	
	
	
	public Long getNodeId() {
		return nodeId;
	}



	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}



	public String getWkt() {
		return wkt;
	}



	public void setWkt(String wkt) {
		this.wkt = wkt;
	}



	public String getEnderecoDestino() {
		return enderecoDestino;
	}
	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
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
