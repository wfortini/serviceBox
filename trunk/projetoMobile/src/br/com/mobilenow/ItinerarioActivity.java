package br.com.mobilenow;



import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.AutoCompleteTextView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.mobilenow.domain.GeoDestino;
import br.com.mobilenow.domain.GeoPartida;
import br.com.mobilenow.domain.Itinerario;
import br.com.mobilenow.util.GeoCoding;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import com.google.maps.android.SphericalUtil;

public class ItinerarioActivity extends CommonActivity {

	public static final String TAG = ItinerarioActivity.class.getSimpleName();
    public static final String ITINERARIO = "ITINERARIO"; 
    public static final int RESULT_CODE = 123;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new ItinerarioUiFragment())
                    .commit();
        }
    }
    
    public static class ItinerarioUiFragment extends CommonFragment{
    	
    	private static final String PLACES_API_KEY = "AIzaSyDD6G16yl7-TTfJPtNejEox8RfoUzEAXt8";
    	
    	
    	private static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    	protected static final int RESULT_CODE = 123;
    	private AutoCompleteTextView partida;
    	private AutoCompleteTextView destino;
    	private GeoDestino enderecoDestino;
    	private GeoPartida enderecoPartida;
    	private Itinerario itinerario;
    	private ProgressDialog progressDialog;
    	private LatLng latitudeLongitude;
    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_itinerario, container, false);
            return v;
        }
    	
    	 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
             init(view);
         }
    	 
    	 void init(View v)
         {
             // (getActivity().getIntent().getStringExtra(ITINERARIO));
             Button uploadBtn = (Button) v.findViewById(R.id.confirmar_itinerario);
             partida = (AutoCompleteTextView) v.findViewById(R.id.partida);
     		 destino = (AutoCompleteTextView) v.findViewById(R.id.destino);
     		 
             uploadBtn.setOnClickListener(new OnClickListener() {

                 @Override
                 public void onClick(View v) {
                	 itinerario = new Itinerario();
                	 enderecoDestino = new GeoDestino();
                	 enderecoPartida = new GeoPartida();
                	 
                	 if(destino.getText() != null && !destino.getText().toString().equals("")){
                		 enderecoDestino.setEnderecoDestino(destino.getText().toString()); 
                	 }else{
                		 destino.setError("Entre com um destino.");
                	 }
                	 
                	 if(partida.getText() != null && !partida.getText().toString().equals("")){
                		 enderecoPartida.setEnderecoPartida(partida.getText().toString()); 
                	 }else{
                		 partida.setError("Entre com um local de partida.");
                	 }
                	 
                	 if(enderecoPartida.getEnderecoPartida() != null && 
                			 !enderecoPartida.getEnderecoPartida().equals("")){
                		 
                		 /** pegar latitude / longitude do endereco partida **/
                		 new RequisicaoTask().execute(enderecoPartida.getEnderecoPartida(), "PARTIDA");
                		 
                		 
                	 }
                	 
                	 if(enderecoDestino.getEnderecoDestino() != null && 
                			 !enderecoPartida.getEnderecoPartida().equals("")){
                		 /** pegar latitude / longitude do endereco destino **/
                		 new RequisicaoTask().execute(enderecoDestino.getEnderecoDestino(), "DESTINO");
                		 
                		 
                	 }         	
                	 
                	 
                 }
             }); // fim classe interna 
             
             
            partida.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line));
     		destino.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line));
             
             
         }
    	 
    	 public void finishedClicked() {

             Intent data = new Intent();
             data.putExtra(ITINERARIO, itinerario);
             getActivity().setResult(RESULT_CODE, data);
             getActivity().finish();

         }
    	 

    /** processamento assincrono **/	
	private class RequisicaoTask extends AsyncTask<String, Void, LatLng>{
			
			String obterCoordenadasPara = "";
			
			@Override
	        protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(getActivity());
				progressDialog.setTitle(R.string.aguarde_por_favor);
				progressDialog.setMessage(CommonUtils.getStringResource(R.string.processando));
				progressDialog.setCancelable(false);
				progressDialog.show();
			}

			@Override
			protected LatLng doInBackground(String... params) {			 
				
				// Obter latitude / longitude dos endereço de Partida e destino
				GeoCoding geo = new GeoCoding();
				
				latitudeLongitude = geo.getLatitudeLongitude(params[0]);
				obterCoordenadasPara = params[1];
				
				return latitudeLongitude;
				
			}	
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
			}
			
			@Override
			protected void onPostExecute(LatLng result) {				
				super.onPostExecute(result);
				if(obterCoordenadasPara.equals("PARTIDA")){
					enderecoPartida.setLatitude(latitudeLongitude.latitude);
	       		    enderecoPartida.setLongitude(latitudeLongitude.longitude);	
				}else{
					enderecoDestino.setLatitude(latitudeLongitude.latitude);
	       		    enderecoDestino.setLongitude(latitudeLongitude.longitude);
	       		    
	       		    LatLng de = new LatLng(enderecoPartida.getLatitude(), enderecoPartida.getLongitude());
	       		    LatLng para = new LatLng(enderecoDestino.getLatitude(), enderecoDestino.getLongitude());       		    
	       		    
		       		itinerario.setDestino(enderecoDestino);
	            	itinerario.setPartida(enderecoPartida); 
	            	itinerario.setDistanciaMaxima(0.0);
	            	itinerario.setDistanciaPartidaDestino(SphericalUtil.computeDistanceBetween(de, para));
	                finishedClicked();
				}
				progressDialog.dismiss();
				
			}
			
		} // fim Task

    	 
    	 
    	 private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
 		    private ArrayList<String> resultList;
 		    
 		    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
 		        super(context, textViewResourceId);
 		    }
 		    
 		    @Override
 		    public int getCount() {
 		        return resultList.size();
 		    }

 		    @Override
 		    public String getItem(int index) {
 		        return resultList.get(index);
 		    }

 		    @Override
 		    public Filter getFilter() {
 		        Filter filter = new Filter() {
 		            @Override
 		            protected FilterResults performFiltering(CharSequence constraint) {
 		                FilterResults filterResults = new FilterResults();
 		                if (constraint != null) {
 		                    // Retrieve the autocomplete results.
 		                    resultList = autocomplete(constraint.toString());
 		                    
 		                    // Assign the data to the FilterResults
 		                    filterResults.values = resultList;
 		                    filterResults.count = resultList.size();
 		                }
 		                return filterResults;
 		            }

 		            @Override
 		            protected void publishResults(CharSequence constraint, FilterResults results) {
 		                if (results != null && results.count > 0) {
 		                    notifyDataSetChanged();
 		                }
 		                else {
 		                    notifyDataSetInvalidated();
 		                }
 		            }};
 		        return filter;
 		    }
 		} // Fim classe Interna AutoComplete
    	 
    private static final String PLACES_AUTOCOMPLETE_API = "https://maps.googleapis.com/maps/api/place/autocomplete/json";
    
    private ArrayList<String> autocomplete(String input) {
	    
	  	ArrayList<String> resultList = new ArrayList<String>();
	    
	    try {
	    
   		 	HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
					 @Override
				     public void initialize(HttpRequest request) {
						 request.setParser(new JsonObjectParser(JSON_FACTORY));
					 }
			     }
   		 	);
		    
   		 	GenericUrl url = new GenericUrl(PLACES_AUTOCOMPLETE_API);
	    	url.put("input", input);
	    	url.put("key", PLACES_API_KEY);
	    	url.put("sensor",false);
   	 
		    HttpRequest request = requestFactory.buildGetRequest(url);
		    HttpResponse httpResponse = request.execute();
		    PlacesResult directionsResult = httpResponse.parseAs(PlacesResult.class);

		    List<Prediction> predictions = directionsResult.predictions;
		    for (Prediction prediction : predictions) {
		    	resultList.add(prediction.description);
			}
	    } catch (Exception ex) {
	    	CommonUtils.error(TAG, ex.getMessage());
	    	ex.printStackTrace();
	    }
	    return resultList;
	}	
  
	public static class PlacesResult {
	
		    @Key("predictions")
		    public List<Prediction> predictions;
	
	}
	
	public static class Prediction {
		  @Key("description")
		  public String description;
		  
		  @Key("id")
		  public String id;
		  
	}	 
    	 
    	
    }
}
