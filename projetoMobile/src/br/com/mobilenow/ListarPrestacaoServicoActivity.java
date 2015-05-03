package br.com.mobilenow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.mobilenow.adapter.PrestarServicoListAdapter;
import br.com.mobilenow.domain.Itinerario;
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.net.ListaPrestarServicoResponse;
import br.com.servicebox.common.net.ListaServicoResponse;
import br.com.servicebox.common.net.PrestacaoLocalizada;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.Response;

import com.android.volley.toolbox.ImageLoader;

public class ListarPrestacaoServicoActivity extends CommonActivity {
	
	public static final String TAG = ListarPrestacaoServicoActivity.class.getSimpleName();
	public static final String LOCALIZAR_POR_ITINERARIO = "LOCALIZAR_POR_ITINERARIO";
	public static final String PRESTAR_SERVICO = "PRESTAR_SERVICO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new ListarPrestacaoServicoFragment())
	                .commit();
	    }
	}
	
	
	public static class ListarPrestacaoServicoFragment extends CommonFragment implements
        OnItemClickListener {

		private PrestarServicoListAdapter mAdapter;		
		private ProgressDialog progressDialog;
		private List<PrestacaoLocalizada> prestarLista = new ArrayList<PrestacaoLocalizada>();
		private ListView listaPrestacaoServico;
        private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();  	
    	
    	private PrestacaoLocalizada prestacaoLocalizada;
    	private Itinerario itinerario;
    	private Integer tipoServico;
		
		@Override
    	public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		if (savedInstanceState != null) {
    			itinerario = savedInstanceState.getParcelable(LOCALIZAR_POR_ITINERARIO);
            } else {
            	itinerario = getActivity().getIntent().getParcelableExtra(LOCALIZAR_POR_ITINERARIO);
            }
    	}
		
		 @Override
         public void onSaveInstanceState(Bundle outState) {
             super.onSaveInstanceState(outState);
             outState.putParcelable(LOCALIZAR_POR_ITINERARIO, itinerario);
         }
		
		@Override
		public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
				ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
		  View v = inflater.inflate(R.layout.activity_listar_prestacao_servico, container, false);
		  return v;
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		   super.onViewCreated(view, savedInstanceState);
		   init(view, savedInstanceState);
		  
		}
		
		void init(View v, Bundle savedInstanceState){			
			
			listaPrestacaoServico = (ListView) v.findViewById(R.id.listaPrestacaoServico);            
			listaPrestacaoServico.setOnItemClickListener(this);  
			tipoServico = getActivity().getIntent().getIntExtra(PRESTAR_SERVICO, 0);
		    new RequisicaoTask().execute();      
		 
		} // fim metodo init      
		  
		
		/** processamento assincrono **/	
		private class RequisicaoTask extends AsyncTask<Void, Void, Response>{
					
				Response response = null;
				String url;
				
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
				protected Response doInBackground(Void... params) {					
					
					try {
						url = getString(R.string.ip_servidor_servicebox)
				 				   .concat(":8080/projetoWeb/buscarServicosPorCoordenadasComDistancia.json");  
						
						RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
						
						PrestarServicoRequest request = ServiceBoxMobileUtil.
								preencheObjetoPrestarServicoRequest(itinerario, null);    
						request.setNodeId(ServiceBoxApplication.getUsuario().getNodeId());
						request.setLogin(ServiceBoxApplication.getUsuario().getLogin());
						request.setServicoPrestado(tipoServico);
						
			             Response response = null;
			             if (GuiUtils.checkOnline()){
						         response = restTemplate.postForObject(url, request, ListaPrestarServicoResponse.class);
			             }
			             
			             return response;
			           
						
					}catch(ResourceAccessException rae){
						CommonUtils.error(TAG, rae.getMessage());
						response = new Response(false, "Falha na localiza��o das Presta��es de servi�o \n Servidor n�o responde.", null, Response.ERRO_DESCONHECIDO);
						progressDialog.dismiss();
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
						response = new Response(false, "Falha na localiza��o das Presta��es de servi�o, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
						progressDialog.dismiss();
					}
					
					return response;
				}
				
				public void retornoRegistro(Response response){						
						
					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
						
						if(response instanceof ListaPrestarServicoResponse){							
							ListaPrestarServicoResponse lstResponse = (ListaPrestarServicoResponse) response;
							if(lstResponse.getPrestacaoLocalizadas() != null)
							     prestarLista = Arrays.asList(lstResponse.getPrestacaoLocalizadas());
							
							mAdapter = new PrestarServicoListAdapter(getActivity(), prestarLista);
							mAdapter.setUrl(getString(R.string.ip_servidor_servicebox));
							listaPrestacaoServico.setAdapter(mAdapter);
							mAdapter.notifyDataSetChanged();
						}						
						
					}else{
						progressDialog.dismiss();
						GuiUtils.alert(response.getMessage());
					}	
									
				}	
				
				
				@Override
				protected void onCancelled() {
					super.onCancelled();
					progressDialog.dismiss();
				}
				
				
				
				@Override
				protected void onPostExecute(Response result) {				
					super.onPostExecute(result);
					progressDialog.dismiss();
					this.retornoRegistro(result);
					mAdapter.notifyDataSetChanged();
				}
					
		} // Fim	
			
		
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			
		}
		
		
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		 
			PrestacaoLocalizada localizada = mAdapter.getItem(position);
			Info info = ServiceBoxMobileUtil.preencherObjetoInfo(localizada);					
			Intent intent = new Intent(getActivity(), InfoActivity.class);
		    intent.putExtra(InfoActivity.INFO_SERVICO, info);
		    intent.putExtra(InfoActivity.EXIBIR_INFO_NO_MODO, InfoActivity.INFO_MODO_SOLICITAR);
		    intent.putExtra(InfoActivity.PRESTAR_SERVICO, tipoServico);
		    getActivity().startActivity(intent);
		  
		  /**  usar da forma abaixo como no exemplo
		  @Override
		  public void onCreate(Bundle savedInstanceState) {
		      super.onCreate(savedInstanceState);
		      if (savedInstanceState != null) {
		          mCredentials = savedInstanceState.getParcelableArrayList(CREDENTIALS);
		      } else {
		          mCredentials = getActivity().getIntent().getParcelableArrayListExtra(CREDENTIALS);
		      }
		  }
		 
		} 
		
		@Override
		public void onSaveInstanceState(Bundle outState) {
		  super.onSaveInstanceState(outState);
		  outState.putParcelableArrayList(CREDENTIALS, mCredentials);
		}
		**/ 
		}
		
		}
}
