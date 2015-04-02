package br.com.mobilenow;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.TextView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.net.Response;
import br.com.servicebox.common.net.ServicoResponse;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class InfoActivity extends CommonActivity {	
	
	public static final String TAG = InfoActivity.class.getSimpleName();
	public static final String INFO_SERVICO = "INFO_SERVICO";
    public static final int RESULT_CODE = 456;   
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new InfoUiFragment())
	                .commit();
	    }
	}
    
    
    public static class InfoUiFragment extends CommonFragment{    	
    	
    	private ProgressDialog progressDialog;
    	private android.widget.TextView tvNome;;
    	private TextView tvDescricao;
    	private TextView tvPartida;
    	private TextView tvDestino;
    	
    	private TextView tvDiasDaSemana;    	
    	private TextView tvHorarioPlanejado;
    	
    	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
    	
    	
    	private Info info;
    	
    	private Button btConfirma;
    	
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
         public void onSaveInstanceState(Bundle outState) {
             super.onSaveInstanceState(outState);
             outState.putParcelable(INFO_SERVICO, info);
         }
    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_info, container, false);
            return v;
        }
    	
    	 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
             init(view);
         }
    	 
    	private void init(View v){
    		
    		StringBuilder html = retornaDiasSemana();    		 
    		
    		Spanned result = Html.fromHtml(html.toString());
    		
    		tvDiasDaSemana =(TextView) v.findViewById(R.id.diasDaSemana);
        	
        	tvNome = (TextView) v.findViewById(R.id.nome);
        	tvDescricao = (TextView) v.findViewById(R.id.descricao);
        	
        	tvHorarioPlanejado = (TextView) v.findViewById(R.id.horarioPlanejado);
        	
        	
        	tvPartida = (TextView) v.findViewById(R.id.dadosPartida);
        	tvDestino = (TextView) v.findViewById(R.id.dadosDestino);        	
        	
        	tvDiasDaSemana.setText(result);
        	tvDiasDaSemana.setTextColor(Color.BLUE);        		
        	
        	Spanned resultHorario = retornaHorarios();
        	tvHorarioPlanejado.setText(resultHorario);
        	
        	tvNome.setText(info.getNomeUsuario());
        	tvDescricao.setText(info.getDescricao());
        	
        	tvPartida.setText(info.getItinerario().getPartida().getEnderecoPartida());
        	tvDestino.setText(info.getItinerario().getDestino().getEnderecoDestino());
        	
        	NetworkImageView thumbNail = (NetworkImageView) v.findViewById(R.id.imagem_perfil);
        	
        	// thumbnail image
    		thumbNail.setImageUrl(ServiceBoxMobileUtil.getUrlImagemPerfil(
    				getString(R.string.ip_servidor_servicebox), info.getFotoPerfilUsuario(), 
    				info.getLoginUsuario()), imageLoader);
    		 
    		 btConfirma = (Button) v.findViewById(R.id.bt_solicitar);
    		 btConfirma.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					confirmar();
					
				}
			});
    		 
    		 
      }

		private Spanned retornaHorarios() {
			StringBuilder htmlHorario = new StringBuilder("&nbsp;");
        	if(info.getPlanejamento().getHoraFixa() != null && 
        			!info.getPlanejamento().getHoraFixa().equals("00:00")){
        		htmlHorario.append("<b>").append(info.getPlanejamento().getHoraFixa());
        		htmlHorario.append("</b>");
        	}else{
        		htmlHorario.append("entre&nbsp;<b>").append(info.getPlanejamento().getHoraEntre());
        		htmlHorario.append("</b>&nbsp;e&nbsp;<b>").append(info.getPlanejamento().getHoraE());
        		htmlHorario.append("</b>");
        	}
        	Spanned resultHorario = Html.fromHtml(htmlHorario.toString());
			return resultHorario;
		}

		private StringBuilder retornaDiasSemana() {
			StringBuilder html = new StringBuilder("&nbsp;");
    		if(info.getPlanejamento().isDomingo())
    			html.append("<b>DOM</b>&nbsp;");
    		if(info.getPlanejamento().isSegunda())
    			html.append("<b>SEG</b>&nbsp;");
    		if(info.getPlanejamento().isTerca())
    			html.append("<b>TER</b>&nbsp;");
    		if(info.getPlanejamento().isQuarta())
    			html.append("<b>QUA</b>&nbsp;");
    		if(info.getPlanejamento().isQuinta())
    			html.append("<b>QUI</b>&nbsp;");
    		if(info.getPlanejamento().isSexta())
    			html.append("<b>SEX</b>&nbsp;");
    		if(info.getPlanejamento().isSabado())
    			html.append("<b>SAB</b>&nbsp;");
			return html;
		}   	 
    	 
    	 /**
          * Confirma que prestar o serviço
          *         
          */
         public void confirmar() {
        	 
             getActivity().finish();
        	 
        	 
         	
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
         
         
         
         
	 
   } // fim classe interna Fragment
    
    
    
}
