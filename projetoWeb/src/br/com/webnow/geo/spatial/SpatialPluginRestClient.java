package br.com.webnow.geo.spatial;

import org.springframework.beans.factory.annotation.Value;
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

}
