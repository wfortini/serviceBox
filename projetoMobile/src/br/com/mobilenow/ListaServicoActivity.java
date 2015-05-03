package br.com.mobilenow;

import java.util.ArrayList;
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
import br.com.mobilenow.adapter.ListarServicoAdapter;
import br.com.mobilenow.domain.PrestarServico;
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.net.ListaServicoResponse;
import br.com.servicebox.common.net.Response;


public class ListaServicoActivity extends CommonActivity {
	
	public static final String TAG = ListaServicoActivity.class.getSimpleName();
	public static final String LISTAR_SERVICO = "LISTAR_SERVICO";
    public static final String PRESTAR_SERVICO = "PRESTAR_SERVICO";

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
		private ProgressDialog progressDialog;
		private List<PrestarServico> prestarLista = new ArrayList<PrestarServico>();
		private ListView list;
		private Integer tipoServico;
		
		@Override
		public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
				ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_lista_servico, container, false);
            return v;
		}
		
		 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
             init(view, savedInstanceState);
            
         }
		
		void init(View v, Bundle savedInstanceState){
			
			tipoServico = getActivity().getIntent().getIntExtra(PRESTAR_SERVICO, 0);
			
            list = (ListView) v.findViewById(R.id.list);            
            list.setOnItemClickListener(this);            
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
				 				   .concat(":8080/projetoWeb/listarPrestarServicoOferecidos.json");  
						
						RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
						
						MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	                     
						 map.add("idUsuario", ServiceBoxApplication.getUsuario().getNodeId().toString());						 			
			             //TODO: dicionar o tipo de serviço tbm
			             HttpHeaders headers = new HttpHeaders();
			             headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			             HttpEntity<MultiValueMap<String, Object>> entity = 
			            		     new HttpEntity<MultiValueMap<String, Object>>(map, headers);

						
			             Response response = null;
			             if (GuiUtils.checkOnline()){
						         response = restTemplate.postForObject(url, entity, ListaServicoResponse.class);
			             }
			             
			             return response;
						
					}catch(ResourceAccessException rae){
						CommonUtils.error(TAG, rae.getMessage());
						response = new Response(false, "Falha na listagem das Prestações de serviço \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
						progressDialog.dismiss();
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
						response = new Response(false, "Falha na listagem das Prestações de serviço, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
						progressDialog.dismiss();
					}
					
					return response;
				}
				
				public void retornoRegistro(Response response){						
						
					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
						
						if(response instanceof ListaServicoResponse){							
							ListaServicoResponse lstResponse = (ListaServicoResponse) response;
							prestarLista = ServiceBoxMobileUtil.preencherServico(lstResponse);
							mAdapter = new ListarServicoAdapter(getActivity(), prestarLista);
							mAdapter.setUrl(getString(R.string.ip_servidor_servicebox));
							list.setAdapter(mAdapter);
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
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            
        }
		 
		 @Override
		 public void onDestroy() {
			super.onDestroy();
			
		}
		
 
		 @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
           
			PrestarServico servicoSelecionad = mAdapter.getItem(position);
			Info info = new Info(servicoSelecionad, ServiceBoxApplication.getUsuario());			
			Intent intent = new Intent(getActivity(), InfoActivity.class);
            intent.putExtra(InfoActivity.INFO_SERVICO, info);
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
