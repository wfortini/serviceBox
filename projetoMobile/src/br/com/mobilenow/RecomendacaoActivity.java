package br.com.mobilenow;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.Switch;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import br.com.mobilenow.PrestarServicoActivity.PrestarServicoUiFragment;
import br.com.mobilenow.domain.Servico;
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.net.Response;
import br.com.servicebox.common.net.ServicoResponse;

public class RecomendacaoActivity extends CommonActivity {

	private static final String TAG = RecomendacaoActivity.class.getSimpleName();
    public static final String PRESTAR_SERVICO = "PRESTAR_SERVICO";
    public static final String INFO_SERVICO_RECOMENDAR = "INFO_SERVICO_RECOMENDAR";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new RecomendacaoUiFragment())
                    .commit();
        }
    }
    
    public static class RecomendacaoUiFragment extends CommonFragment {
    	
    	Switch recomendarSimNao;
    	private ProgressDialog progressDialog;
    	private Integer tipoServico;
    	private EditText comentario;
    	private Info info;
    	
    	@Override
    	public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		if (savedInstanceState != null) {
    			info = savedInstanceState.getParcelable(INFO_SERVICO_RECOMENDAR);
            } else {
            	info = getActivity().getIntent().getParcelableExtra(INFO_SERVICO_RECOMENDAR);
            }
    	}
    	
    	 @Override
         public void onSaveInstanceState(Bundle outState) {
             super.onSaveInstanceState(outState);
             outState.putParcelable(INFO_SERVICO_RECOMENDAR, info);
         }

    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_recomendacao, container, false);
            return v;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            init(view);
        }
        void init(View v)
        {          

        	tipoServico = getActivity().getIntent().getIntExtra(PRESTAR_SERVICO, 0);      	
        	
            Button confirmarBtn = (Button) v.findViewById(R.id.confirmarBtn);
            recomendarSimNao = (Switch) v.findViewById(R.id.recomendar_switch);
            comentario = (EditText) v.findViewById(R.id.edt_comentario);
            
            confirmarBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {                    
                    confirmar();
                }
            });
        }
        
        
        /**
         * Confirma que prestar o serviço
         *         
         */
        public void confirmar() {

        	new RequisicaoTask().execute();

        }
        
        
        /** processamento assincrono **/	
    	private class RequisicaoTask extends AsyncTask<Void, Void, ServicoResponse>{
    			
    		ServicoResponse response = null;
    			
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
    			protected ServicoResponse doInBackground(Void... params) {    				
    				
    				try {
    					final String url = getString(R.string.ip_servidor_servicebox)
    							   .concat(":8080/projetoWeb/recomendar.json");
    					
    					RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
    					
    					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();                   
    					
    					 map.add("idUsuarioRecomenda", ServiceBoxApplication.getUsuario().getNodeId().toString());
    					 map.add("idUsuarioRecomendado", info.getNodeIdUsuario().toString());
    					 map.add("tipoServicoRecomendado", TipoServico.getTipoServico(tipoServico).getCodigo().toString());
    					 map.add("recomenda", String.valueOf(recomendarSimNao.isChecked()));
    					 map.add("comentario", comentario.getText().toString());
    					 
    		             HttpHeaders headers = new HttpHeaders();
    		             headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    		             HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
    	                    		            
    		             if (GuiUtils.checkOnline()){
    					         response = restTemplate.postForObject(url, entity, ServicoResponse.class);
    		             }
    		             
    		             return response;
    					
    				}catch(ResourceAccessException rae){
    					CommonUtils.error(TAG, rae.getMessage());
    					response = new ServicoResponse(false, "Falha na recomendação do usuário \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
    				} catch (Exception e) {
    					Log.e(TAG, e.getMessage());
    					response = new ServicoResponse(false, "Falha na recomendação, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
    				}
    				
    				return response;
    			}
    			
               public void retornoRegistro(Response response){							
					
					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
						GuiUtils.alert(response.getMessage());	
						getActivity().finish();
					}else{
						GuiUtils.alert(response.getMessage());
					}			
			}	
    			
    			@Override
    			protected void onCancelled() {
    				super.onCancelled();
    			}
    			
    			@Override
    			protected void onPostExecute(ServicoResponse result) {				
    				super.onPostExecute(result);
    				progressDialog.dismiss();
    				this.retornoRegistro(result);
    			}
    			
    		}    
    	
    }
}
