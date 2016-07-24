package br.com.webnow.geo.spatial;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class Neo4jRestClientFactory {
	
	
	private static String databaseUser;
	
	private static RestTemplate restTemplate;

	  private static String databasePassword;

	  @Value("${database_usr}")
	  public void setDatabaseUser(String databaseUser)
	  {
	    Neo4jRestClientFactory.databaseUser = databaseUser;
	  }

	  @Value("${database_pwd}")
	  public void setDatabasePassword(String databasePassword)
	  {
	    Neo4jRestClientFactory.databasePassword = databasePassword;
	  }

	  public static RestTemplate getInstance()
	  {
		  
		if(restTemplate == null){
			    CredentialsProvider credsProvider = new BasicCredentialsProvider();
		
			    credsProvider.setCredentials(
			            new AuthScope(null, -1),
			            new UsernamePasswordCredentials(databaseUser, databasePassword));
		
			    HttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		
			    restTemplate = new RestTemplate();
		
			    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
		}
		
	    return restTemplate;
	  }

}
