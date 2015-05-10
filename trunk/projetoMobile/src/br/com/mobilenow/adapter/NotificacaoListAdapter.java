package br.com.mobilenow.adapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.app.ProgressDialog;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.mobilenow.R;
import br.com.mobilenow.ServiceBoxApplication;
import br.com.mobilenow.dao.NotificacaoDAO;
import br.com.mobilenow.domain.Notificacao;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.StatusSolicitacao;
import br.com.servicebox.common.net.Response;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class NotificacaoListAdapter extends ArrayAdapter<Notificacao>{	
	
	public static final String TAG = NotificacaoListAdapter.class.getSimpleName();
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<Notificacao> lista = new ArrayList<Notificacao>();	
	private String url;
	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
	private ProgressDialog progressDialog;
	private Notificacao notificacaoSelecionada;
	
	public NotificacaoListAdapter(Activity activity, List<Notificacao> list) {
		super(activity, android.R.layout.simple_list_item_1, list);
		this.activity = activity;
		this.lista = list;		
		
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Notificacao getItem(int location) {
		return lista.get(location);
	}
	

	public List<Notificacao> getLista() {
		return lista;
	}

	public void setLista(List<Notificacao> lista) {
		this.lista = lista;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}	
	
	private void processarNotificacao(){
		
		new RequisicaoTask().execute();	
		
		
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.lista_item_notificacao, null);
		
		if (imageLoader == null)
			imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
		
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.imagem_perfil_notificacao);
		
		final Notificacao notificacao = lista.get(position);	
		// thumbnail image
				thumbNail.setImageUrl(ServiceBoxMobileUtil.getUrlImagemPerfil(
						getUrl(), notificacao.getFotoPerfil(), 
						null), imageLoader);
		
		TextView mensagem = (TextView) convertView.findViewById(R.id.mensagem);		
		TextView data = (TextView) convertView.findViewById(R.id.dataMensagem);	
		Button btAceitar = (Button) convertView.findViewById(R.id.bt_notificacao_sim);
		Button btNegar = (Button) convertView.findViewById(R.id.bt_notificacao_nao);
		
		ImageView imageAceito = (ImageView) convertView.findViewById(R.id.imagem_aceita);
		TextView lblAceito = (TextView) convertView.findViewById(R.id.lblAceito);
		ImageView imageNegado = (ImageView) convertView.findViewById(R.id.imagem_negada);
		TextView lblNegou = (TextView) convertView.findViewById(R.id.lblNegou);
		
		if(StatusSolicitacao.SOLICITACAO_ACEITA_PELO_SOLICITADO.getCodigo()
				.equals(notificacao.getStatusNotificacao())){
			imageAceito.setVisibility(View.VISIBLE);
			lblAceito.setVisibility(View.VISIBLE);
		}else if(StatusSolicitacao.SOLICITACAO_NEGADA_PELO_SOLICITADO.getCodigo()
				.equals(notificacao.getStatusNotificacao())){
			imageNegado.setVisibility(View.VISIBLE);
			lblNegou.setVisibility(View.VISIBLE);
			
		}
		
		if(StatusSolicitacao.SOLICITACAO_ENVIADA_PARA_SOLICITADO.getCodigo().equals(
				notificacao.getStatusNotificacao()) && 
				        ServiceBoxApplication.getUsuario().getNodeId().intValue() != notificacao.getIdSolicitante()){
			btAceitar.setVisibility(View.VISIBLE);
			btNegar.setVisibility(View.VISIBLE);
			
		}
		
		btAceitar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				notificacaoSelecionada = notificacao;
				notificacaoSelecionada.setStatusNotificacao(StatusSolicitacao.SOLICITACAO_ACEITA_PELO_SOLICITADO.getCodigo());
				processarNotificacao();
				
			}
		});
		
		btNegar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				notificacaoSelecionada = notificacao;
				notificacaoSelecionada.setStatusNotificacao(StatusSolicitacao.SOLICITACAO_NEGADA_PELO_SOLICITADO.getCodigo());
				processarNotificacao();
				
			}
		});
		
		mensagem.setText(notificacao.getMensagem());
		data.setText("Criação em: "+ServiceBoxMobileUtil.dateToString(notificacao.getDataSolicitacao(),
				DateFormat.SHORT));
		
		

		return convertView;
	}
	
	/** processamento assincrono **/	
 	private class RequisicaoTask extends AsyncTask<Void, Void, Response>{
 			
 		Response response = null;
 			
 			@Override
 	        protected void onPreExecute() {
 				super.onPreExecute();
 				progressDialog = new ProgressDialog(activity);
 				progressDialog.setTitle(R.string.aguarde_por_favor);
 				progressDialog.setMessage(CommonUtils.getStringResource(R.string.processando));
 				progressDialog.setCancelable(false);
 				progressDialog.show();
 			}

 			@Override
 			protected Response doInBackground(Void... params) {    				
 				
 				try {
 					final String urlTemp = url.concat(":8080/projetoWeb/responderSolicitacao.json");
 					
 					RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
 					
 					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();                   
 					
 					 map.add("idSolicitacao", notificacaoSelecionada.getIdSolicitacao().toString());     					
 					 map.add("respostaSolicitacao", notificacaoSelecionada.getStatusNotificacao().toString());
 										 
 		             HttpHeaders headers = new HttpHeaders();
 		             headers.setContentType(MediaType.MULTIPART_FORM_DATA);

 		             HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
 	                    		            
 		             if (GuiUtils.checkOnline()){
 					         response = restTemplate.postForObject(urlTemp, entity, Response.class);
 		             }
 		             
 		             return response;
 					
 				}catch(ResourceAccessException rae){
 					CommonUtils.error(TAG, rae.getMessage());
 					response = new Response(false, "Falha na responda da notificação \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
 				} catch (Exception e) {
 					Log.e(TAG, e.getMessage());
 					response = new Response(false, "Fallha na responda da notificação, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
 				}
 				
 				return response;
 			}
 			
            public void retornoRegistro(Response response){							
					
					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
						GuiUtils.alert(response.getMessage());
						NotificacaoDAO dao = null;
						try {
							dao = new NotificacaoDAO(activity);
						    dao.atualizarStatusNotificacao(notificacaoSelecionada);
					} finally {
						dao.close();
					}					
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

}
