package br.com.mobilenow;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.LayoutInflater;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.util.GoogleMapUtis;
import br.com.mobilenow.util.Info;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.common.net.Response;

import com.google.android.gms.internal.in;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
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
import com.google.maps.android.PolyUtil;

public class VisualizarMapActivity extends CommonActivity {
	
	public static final String TAG = VisualizarMapActivity.class.getSimpleName();
	public static final String INFO_SERVICO = "INFO_SERVICO";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new VisualizarMapUiFragment())
                    .commit();
        }		
		
	}
	
	public static class VisualizarMapUiFragment extends CommonFragment{
		
		private static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
		private static final JsonFactory JSON_FACTORY = new JacksonFactory();
		
		private GoogleMap googleMap;
		private List<Marker> markers = new ArrayList<Marker>();
		private List<LatLng> latLngs = new ArrayList<LatLng>();
		private Info info;
        private SupportMapFragment MapFragment;

		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {			
			super.onActivityCreated(savedInstanceState);
			
			 
		    FragmentManager fm = getChildFragmentManager();
	        MapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
	        if (MapFragment == null) {
	            MapFragment = SupportMapFragment.newInstance();
	            fm.beginTransaction().replace(R.id.map, MapFragment).commit();
	        }		    
	        googleMap = MapFragment.getMap();
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);	
		    
		    if (savedInstanceState != null) {
    			info = savedInstanceState.getParcelable(INFO_SERVICO);
            } else {
            	info = getActivity().getIntent().getParcelableExtra(INFO_SERVICO);
            }	   
		    
		}
		
		@Override
		public void onResume() {			
			super.onResume();
			if(googleMap == null){
				googleMap = MapFragment.getMap();
			}
			init();
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater,
		        ViewGroup container, Bundle savedInstanceState) {
		    super.onCreateView(inflater, container, savedInstanceState);
		    View v = inflater.inflate(R.layout.activity_visualizar_map, container, false);		    
		    return v;
		}
		
		@Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putParcelable(INFO_SERVICO, info);
        }
		
		 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);            
         }
    	 
    	 void init()
         {
    		 new DirectionsFetcher(info.getItinerario().getPartida().getEnderecoPartida()
    				 ,info.getItinerario().getDestino().getEnderecoDestino()).execute();
         }
		
		
		
		public void addPolylineToMap(List<LatLng> latLngs) {
			PolylineOptions options = new PolylineOptions();
			for (LatLng latLng : latLngs) {
				options.add(latLng);
			}
			googleMap.addPolyline(options);
		}	
		
		public void clearMarkers() {
			googleMap.clear();
	    	markers.clear();		
		}
		
		/** processamento assincrono **/
	   private class DirectionsFetcher extends AsyncTask<URL, Integer, Void> {

		   private String origin;
		   private String destination;
		 
		   public DirectionsFetcher(String origin,String destination) {
			  this.origin = origin;
			  this.destination = destination;
		 }
		 
		 @Override
		 protected void onPreExecute() {
			super.onPreExecute();
			clearMarkers();
			getActivity().setProgressBarIndeterminateVisibility(Boolean.TRUE);
			
		}
		 
		 protected Void doInBackground(URL... urls) {
	    	 try {
	    		 HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
	    			 @Override
	    		     public void initialize(HttpRequest request) {
	    				 request.setParser(new JsonObjectParser(JSON_FACTORY));
	    			 }
	    		     });
	    	 
		    	 GenericUrl url = new GenericUrl("http://maps.googleapis.com/maps/api/directions/json");
		    	 url.put("origin", origin);
		    	 url.put("destination", destination);
		    	 url.put("sensor",false);
	    	 
    		    HttpRequest request = requestFactory.buildGetRequest(url);
    		    HttpResponse httpResponse = request.execute();
    		    DirectionsResult directionsResult = httpResponse.parseAs(DirectionsResult.class);
    		    
    		    String encodedPoints = directionsResult.routes.get(0).overviewPolyLine.points;
    		    latLngs = PolyUtil.decode(encodedPoints);
	    	 } catch (Exception ex) {
	    		 ex.printStackTrace();
	    		 CommonUtils.error(TAG, ex.getMessage());				
	    	 }
	    	 return null;
	     
	     }

	     protected void onProgressUpdate(Integer... progress) {
	     }

	     protected void onPostExecute(Void result) {
	    	 
	    	 addPolylineToMap(latLngs);	    	
	    	 GoogleMapUtis.fixZoomForLatLngs(googleMap, latLngs);	    	    	 
	    	 getActivity().setProgressBarIndeterminateVisibility(Boolean.FALSE);
	     }
	 } // fim processamento assincrono
	   
	   
	   public static class DirectionsResult {

		    @Key("routes")
		    public List<Route> routes;

	  }

	  public static class Route {
			  @Key("overview_polyline")
			  public OverviewPolyLine overviewPolyLine;
			  
	  }

	 public static class OverviewPolyLine {
			  @Key("points")
			  public String points;
			  
	  }
		  
	  @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==InfoActivity.RESULT_CODE) {
			String from = data.getExtras().getString("from");
			String to = data.getExtras().getString("to");
			new DirectionsFetcher(from,to).execute();
		}
	}
		
		
		
	} // fim classe interna
	
	
	
	
	
}
