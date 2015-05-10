package br.com.mobilenow;

import java.util.Date;

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

import android.content.Intent;
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
import br.com.mobilenow.dao.NotificacaoDAO;
import br.com.mobilenow.domain.Carona;
import br.com.mobilenow.domain.Notificacao;
import br.com.mobilenow.domain.Servico;
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.StatusSolicitacao;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.domain.TipoSolicitacao;
import br.com.servicebox.common.net.Response;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class InfoActivity extends CommonActivity {	
	
	public static final String TAG = InfoActivity.class.getSimpleName();
	public static final String INFO_SERVICO = "INFO_SERVICO";
	public static final String INFO_MODO_SOLICITAR = "INFO_MODO_SOLICITAR";
	public static final String EXIBIR_INFO_NO_MODO = "EXIBIR_INFO_NO_MODO";
    public static final int RESULT_CODE = 456; 
    public static final String PRESTAR_SERVICO = "PRESTAR_SERVICO";
	
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
    	private String exibirNoModo;
    	private Integer tipoServico;
    	
    	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
    	
    	
    	private Info info;
    	
    	private Button btSolicitar;
    	private Button btVisualizar;
    	
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
    		
    		exibirNoModo = getActivity().getIntent().getStringExtra(EXIBIR_INFO_NO_MODO);
    		tipoServico = getActivity().getIntent().getIntExtra(PRESTAR_SERVICO, 0);
    		
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
    		 
    		 btSolicitar = (Button) v.findViewById(R.id.bt_solicitar);
    		 btVisualizar = (Button) v.findViewById(R.id.bt_visualizar);
    		 
    		 if(exibirNoModo != null && exibirNoModo.equals(INFO_MODO_SOLICITAR)){
    			 btSolicitar.setVisibility(View.VISIBLE);
    			 btVisualizar.setVisibility(View.VISIBLE);
    		 }
    		 
    		 
    		 btSolicitar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					  new RequisicaoTask().execute();
					
				}
			});
    		 
    		 btVisualizar.setOnClickListener(new OnClickListener() {
 				
 				@Override
 				public void onClick(View v) {
 					
 					Intent intent = new Intent(getActivity(), VisualizarMapActivity.class);
 	    	        intent.putExtra(VisualizarMapActivity.INFO_SERVICO, info);
 	    	        intent.putExtra(ListarPrestacaoServicoActivity.PRESTAR_SERVICO, tipoServico);
 	    	        getActivity().startActivity(intent);
 	    	        getActivity().finish();
 					
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
    	 
         
         
         /** processamento assincrono **/	
     	private class RequisicaoTask extends AsyncTask<Void, Void, Response>{
     			
     		Response response = null;
     			
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
     					final String url = getString(R.string.ip_servidor_servicebox)
     							   .concat(":8080/projetoWeb/solicitarServico.json");
     					
     					RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
     					
     					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();                   
     					
     					 map.add("idUsuarioSolicitante", ServiceBoxApplication.getUsuario().getNodeId().toString());     					
     					 map.add("idUsuarioSolicitado", info.getNodeIdUsuario().toString());
     					 map.add("tipoSolicitacao", TipoSolicitacao.CARONA.getCodigo().toString());
     					 map.add("idPrestacao", info.getNodeId().toString());     					 
     					 
     		             HttpHeaders headers = new HttpHeaders();
     		             headers.setContentType(MediaType.MULTIPART_FORM_DATA);

     		             HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
     	                    		            
     		             if (GuiUtils.checkOnline()){
     					         response = restTemplate.postForObject(url, entity, Response.class);
     		             }
     		             
     		             return response;
     					
     				}catch(ResourceAccessException rae){
     					CommonUtils.error(TAG, rae.getMessage());
     					response = new Response(false, "Falha na solicitação \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
     				} catch (Exception e) {
     					Log.e(TAG, e.getMessage());
     					response = new Response(false, "Fallha na solicitação, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
     				}
     				
     				return response;
     			}
     			
                public void retornoRegistro(Response response){							
 					
 					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
 						GuiUtils.alert(response.getMessage());
 						incluirNotificacao();				
 					}else{
 						GuiUtils.alert(response.getMessage());
 					}			
 			}
     			
     			
     			
     			@Override
     			protected void onCancelled() {
     				super.onCancelled();
     			}
     			
     			@Override
     			protected void onPostExecute(Response result) {				
     				super.onPostExecute(result);
     				progressDialog.dismiss();
     				this.retornoRegistro(result);
     			}
     			
     		} 
     	
     	
     
     	private void incluirNotificacao(){
     		
     		Servico servico = Servico.getIntance(TipoServico.getTipoServico(tipoServico));
     		
     		NotificacaoDAO dao = null;
				try {
					dao = new NotificacaoDAO(getActivity());
				Notificacao noti = new Notificacao();
				noti.setDataSolicitacao(new Date());
				noti.setIdPrestacao(info.getNodeId().intValue());
				noti.setIdSolicitante(ServiceBoxApplication.getUsuario().getNodeId().intValue());
				noti.setIdSolicitacao(info.getNodeIdUsuario().intValue());
				noti.setMensagem(servico.obterMensagem());
				noti.setFotoPerfil(info.getFotoPerfilUsuario() +"+".concat(
						info.getLoginUsuario()));
				noti.setTipoSolicitacao(servico.getTipoServico());
				noti.setStatusNotificacao(
						StatusSolicitacao.SOLICITACAO_ENVIADA_PARA_SOLICITADO.getCodigo());
				dao.incluir(noti);
			} finally {
				dao.close();
			}	
     		
     	}
         
         
         
         
	 
   } // fim classe interna Fragment
    
    
    
}
