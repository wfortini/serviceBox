package br.com.mobilenow.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import br.com.servicebox.android.common.util.CommonUtils;

import com.google.android.gms.maps.model.LatLng;

public class GeoCoding {
	
	public static final String TAG = GeoCoding.class.getSimpleName();

	public LatLng getLatitudeLongitude(String endereco){
		
		      endereco = endereco.replaceAll(" ","%20");   
		
		     String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
				 endereco + "&sensor=false";
		     StringBuilder stringBuilder = new StringBuilder();
		     
		    LatLng latLng = null;
		    try {
		    
		   HttpGet httpGet = new HttpGet(uri);
		   HttpClient client = new DefaultHttpClient();
		   HttpResponse response;
		  
		
		  
		       response = client.execute(httpGet);
		       HttpEntity entity = response.getEntity();
		       InputStream stream = entity.getContent();
		       int b;
		       while ((b = stream.read()) != -1) {
		           stringBuilder.append((char) b);
		       }
		   } catch (ClientProtocolException e) {
			   CommonUtils.error(TAG, e.getMessage());
		       e.printStackTrace();
		   } catch (IOException e) {
			   CommonUtils.error(TAG, e.getMessage());
		       e.printStackTrace();
		   }
		
		   JSONObject jsonObject = new JSONObject();
		   try {
		       jsonObject = new JSONObject(stringBuilder.toString());
		
		       double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
		           .getJSONObject("geometry").getJSONObject("location")
		           .getDouble("lng");
		
		       double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
		           .getJSONObject("geometry").getJSONObject("location")
		           .getDouble("lat");
		
		       Log.d("===========================latitude", "" + lat);
		       Log.d("===========================longitude", "" + lng);
		       
		       latLng = new LatLng(lat, lng);
		       
		       
		   } catch (JSONException e) {
			   CommonUtils.error(TAG, e.getMessage());
		       e.printStackTrace();
		   }
		
		   return latLng;
	}
	
	public LatLng converteEndereco(String address, Context context) {
		double lat = 0.0;
		double lng = 0.0;
	    if (address != null && !address.isEmpty()) {
	        try {
	        	Geocoder geo = new Geocoder(context);
	            List<Address> addressList = geo.getFromLocationName(address, 1);
	            if (addressList != null && addressList.size() > 0) {
	                lat = addressList.get(0).getLatitude();
	                lng = addressList.get(0).getLongitude();
	            }
	            return new LatLng(lat, lng);
	        } catch (Exception e) {
	        	CommonUtils.error(TAG, e.getMessage());
	            e.printStackTrace();
	        } // end catch
	    } // end if
	    
	    return new LatLng(lat, lng);
	}
	
	
}
