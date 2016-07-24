package br.com.webnow.geo.spatial;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpatialPluginRestClient {
	
	@Value("${database_url}")
	private String databaseURL;
	 
	public String findPlugin(){
		
		RestTemplate rest = Neo4jRestClientFactory.getInstance();
		
		String url = databaseURL + "/db/data/ext/SpatialPlugin";
		
		String response = rest.getForObject(url, String.class);
		
		return response;
	}
	
	public void addSimplePointLayer(){
		
		
		String url = this.databaseURL + "/db/data/ext/SpatialPlugin/graphdb/addSimplePointLayer";		
		RestTemplate rest = Neo4jRestClientFactory.getInstance();
		
		String json = "{" 
		   +"\"layer\":\"geom\"" + ","
		    +"\"lat\":\"lat\"" + ","
		    +"\"lon\":\"lon\""
		    +"}";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity entity = new HttpEntity(json, headers);
		
		String response = rest.postForObject(url, entity, String.class);		
		
		
	}
	
	public void getLayer(){
		
         String url = this.databaseURL + "/db/data/ext/SpatialPlugin/graphdb/getLayer"
         		+ "{\"layer\":\"geom\"}";
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("layer", "geom");		
		
		RestTemplate rest = Neo4jRestClientFactory.getInstance();
		
		String response = rest.getForObject(url, String.class);
		
	}
	
	public String addNodeToLayer(String layer, Long nodeId){
		
		
		
		
		
		return null;
	}
	
	public String addEditableLayer(String nameLayer){
		
		
		return null;
	}

}
