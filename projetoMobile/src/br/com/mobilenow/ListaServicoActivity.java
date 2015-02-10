package br.com.mobilenow;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.mobilenow.adapter.ListarServicoAdapter;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.common.domain.PrestarServico;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class ListaServicoActivity extends CommonActivity {
	
	public static final String TAG = ListaServicoActivity.class.getSimpleName();
	public static final String LISTAR_SERVICO = "LISTAR_SERVICO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new ListaServicoFragment())
	                .commit();
	    }
	}
	
	
	public static class ListaServicoFragment extends CommonFragment implements
              OnItemClickListener {
		
		private ListarServicoAdapter mAdapter;
		final String url = getString(R.string.ip_servidor_servicebox)
				   .concat(":8080/projetoWeb/listarPrestarServicoOferecidos.json");
		private ProgressDialog progressDialog;
		private List<PrestarServico> prestarLista = new ArrayList<PrestarServico>();
		
		 @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);	            
        }

		@Override
		public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
				ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_lista_servico, container, false);
            return v;
		}
		
		void init(View v, Bundle savedInstanceState){
			
			mAdapter = new ListarServicoAdapter(getActivity(), prestarLista);
            ListView list = (ListView) v.findViewById(R.id.list);
            list.setAdapter(mAdapter);
            list.setOnItemClickListener(this);
            
            
            progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle(R.string.aguarde_por_favor);
			progressDialog.setMessage(CommonUtils.getStringResource(R.string.processando));
			progressDialog.setCancelable(false);
			progressDialog.show();

    		// changing action bar color
    		//getActionBar().setBackgroundDrawable(
    		//		new ColorDrawable(Color.parseColor("#1b1b1b")));

    		// Creating volley request obj
    		JsonArrayRequest servicoReq = new JsonArrayRequest(url,
    				new Response.Listener<JSONArray>()
    				{
    					@Override
    					public void onResponse(JSONArray response) 
    					{
    						Log.d(TAG, response.toString());
    						hidePDialog();

    						// Parsing json
    						for (int i = 0; i < response.length(); i++) {
    							try {

    								JSONObject obj = response.getJSONObject(i);
    								PrestarServico prestar = new PrestarServico(); 
    								prestar.setDescricao(obj.getString("descricao"));
    								prestar.setData(ServiceBoxMobileUtil.
    										stringToDate(obj.getString("data"), DateFormat.MEDIUM));
    								prestarLista.add(prestar);

    							} catch (JSONException e) {
    								e.printStackTrace();
    							}catch(ParseException pe){
    								pe.printStackTrace();
    							}

    						}

    						// notifying list adapter about data changes
    						// so that it renders the list view with updated data
    						mAdapter.notifyDataSetChanged();
    					}
    				}, new Response.ErrorListener() {
    					@Override
    					public void onErrorResponse(VolleyError error) 
    					{
    						VolleyLog.d(TAG, "Error: " + error.getMessage());
    						hidePDialog();

    					}
    				}
    				
    			) {
    			 
	    		    @Override
	    		    protected Map<String, String> getParams() 
	    		    {  
	    		            Map<String, String>  params = new HashMap<String, String> ();  
	    		            params.put("idUsuario", ServiceBoxApplication.getUsuario().getNodeId().toString());  
	    		           
	    		            return params; 
	    		    }
    		    }; // fim classe anonima

    		// Adding request to request queue
    		ServiceBoxApplication.getInstance().addToRequestQueue(servicoReq);
    	} // fim metodo init      
            
			
			
		
		 @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            
        }
		 
		 @Override
		 public void onDestroy() {
			super.onDestroy();
			hidePDialog();
		}
		 
		 private void hidePDialog() {
				if (progressDialog != null) {
					progressDialog.dismiss();
					progressDialog = null;
				}
			}
 
		 @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
           
            Activity activity = getSupportActivity();
           
        } 
		 
	 }

	
}
