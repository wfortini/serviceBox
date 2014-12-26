package br.com.mobilenow;

import java.nio.charset.Charset;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.Switch;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
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
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.net.Response;
import br.com.servicebox.common.net.ServicoResponse;

public class PrestarServicoActivity extends CommonActivity {

	public static final String TAG = PrestarServicoActivity.class.getSimpleName();
    public static final String PRESTAR_SERVICO = "PRESTAR_SERVICO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new PrestarServicoUiFragment())
                    .commit();
        }
    }
    
    public static class PrestarServicoUiFragment extends CommonFragment {
    	
    	Switch prestarSimNao;
    	private ProgressDialog progressDialog;
    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_prestar_servico, container, false);
            return v;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            init(view);
        }
        void init(View v)
        {          

            Button confirmarBtn = (Button) v.findViewById(R.id.confirmarBtn);
            prestarSimNao = (Switch) v.findViewById(R.id.prestar_servico_switch);
            
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
    							   .concat(":8080/projetoWeb/adicionarServico.json");
    					
    					RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
    					
    					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();                   
    					
    					 map.add("login", ServiceBoxApplication.getUsuario().getLogin());
    					 map.add("disponibilizarServico", String.valueOf(prestarSimNao.isChecked()));
    					 map.add("tipoServico", TipoServico.CARONA.getCodigo().toString());
    					 
    		             HttpHeaders headers = new HttpHeaders();
    		             headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    		             HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
    	                    		            
    		             if (GuiUtils.checkOnline()){
    					         response = restTemplate.postForObject(url, entity, ServicoResponse.class);
    		             }
    		             
    		             return response;
    					
    				}catch(ResourceAccessException rae){
    					CommonUtils.error(TAG, rae.getMessage());
    					response = new ServicoResponse(false, "Falha no cadastro do usuário \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
    				} catch (Exception e) {
    					Log.e(TAG, e.getMessage());
    					response = new ServicoResponse(false, "Fallha na inclusão do serviço, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
    				}
    				
    				return response;
    			}
    			
               public void retornoRegistro(Response response){							
					
					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
						GuiUtils.alert(response.getMessage());
						// getActivity().finish();					
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
