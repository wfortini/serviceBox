package br.com.mobilenow.fragment;

import java.nio.charset.Charset;
import java.util.Random;

import org.holoeverywhere.app.ProgressDialog;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.ItinerarioActivity;
import br.com.mobilenow.PlanejamentoActivity;
import br.com.mobilenow.PrestarServicoActivity;
import br.com.mobilenow.R;
import br.com.mobilenow.ServiceBoxApplication;
import br.com.mobilenow.componente.SherlockMapFragment;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.net.Itinerario;
import br.com.servicebox.android.common.net.Planejamento;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.Response;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class CaronaMapFragment extends SherlockMapFragment{
	
	 private static final int REQUEST_PRESTA_SERVICO = 1;
	 static final String TAG = CaronaMapFragment.class.getSimpleName();
	
	private Handler handler = new Handler();
	private Itinerario itinerario;
	private ProgressDialog progressDialog;
	private Planejamento planejamento;
	private Random random = new Random();
	private Runnable runner = new Runnable() {
        @Override
        public void run() {
            setHasOptionsMenu(true);
        }
    };
	
	 public static CaronaMapFragment newInstance(int position,String title) {
	    	CaronaMapFragment fragment = new CaronaMapFragment();
	        Bundle bundle = new Bundle();
	        bundle.putInt("position", position);
	        bundle.putString("title", title);
	        fragment.setArguments(bundle);
	        return fragment;
	    }
	    
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			handler.postDelayed(runner, random.nextInt(2000));
			
			View view = super.onCreateView(inflater, container, savedInstanceState);
			//googleMap = getMap();
			//googleMap.setMyLocationEnabled(true);
			
			//ViewUtils.initializeMargin(getActivity(), view);

			return view;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			menu.clear();
			inflater.inflate(R.menu.carona_menu, menu);
			
		}
		
		  @Override
			public void onActivityResult(int requestCode, int resultCode, Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
				if (resultCode==ItinerarioActivity.RESULT_CODE && data.getExtras() != null) {
					itinerario = data.getExtras().getParcelable(ItinerarioActivity.ITINERARIO);
					// essa chamada deve seguir um parametro
					startActivityForResult(new Intent(getActivity(), PlanejamentoActivity.class), PlanejamentoActivity.RESULT_CODE);
				}else if(resultCode==PlanejamentoActivity.RESULT_CODE && data.getExtras() != null){
					planejamento = data.getExtras().getParcelable(PlanejamentoActivity.PLANEJAMENTO);
				}
				if(itinerario != null && planejamento != null){
					new RequisicaoTask().execute();
				}
				
				
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
									.concat(":8080/projetoWeb/prestarServico.json");
							
							RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
							
							PrestarServicoRequest request = ServiceBoxMobileUtil.preencheObjetoPrestarServicoRequest(itinerario, planejamento);    
							request.setNodeId(ServiceBoxApplication.getUsuario().getNodeId());
							request.setLogin(ServiceBoxApplication.getUsuario().getLogin());
							request.setServicoPrestado(TipoServico.CARONA.getCodigo());
							
				             Response response = null;
				             if (GuiUtils.checkOnline()){
							         response = restTemplate.postForObject(url, request, Response.class);
				             }
				             
				             return response;
							
						}catch(ResourceAccessException rae){
							CommonUtils.error(TAG, rae.getMessage());
							response = new Response(false, "Falha no cadastro do usuário \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
						} catch (Exception e) {
							Log.e(TAG, e.getMessage());
							response = new Response(false, "Fallha no cadastro do usuário, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
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
					protected void onPostExecute(Response result) {				
						super.onPostExecute(result);
						progressDialog.dismiss();
						this.retornoRegistro(result);
					}
					
				} 

				  
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			  if (item.getItemId() == R.id.item_menu_oferecr_carona) {
				  
				  startActivityForResult(new Intent(getActivity(), UsuarioActivity.class), UsuarioActivity.RESULT_CODE);
				  
			  } else if (item.getItemId() == R.id.item_menu_pegar_carona) {
				  
				  startActivityForResult(new Intent(getActivity(), ItinerarioActivity.class), ItinerarioActivity.RESULT_CODE);
				  
			  } else if (item.getItemId() == R.id.item_menu_motorista_rodada_carona) {
				  
				  startActivityForResult(new Intent(getActivity(), UsuarioActivity.class), UsuarioActivity.RESULT_CODE);
				  
			  } else  if (item.getItemId() == R.id.item_menu_prestar_carona) {
				  
				  Intent i = new Intent(getActivity(), PrestarServicoActivity.class);
                  i.putExtra(PrestarServicoActivity.PRESTAR_SERVICO, "carona");
                  startActivityForResult(i, REQUEST_PRESTA_SERVICO);
                  
			  } else  if (item.getItemId() == R.id.item_menu_motorista_rodada_carona){
				  
				  Intent i = new Intent(getActivity(), PrestarServicoActivity.class);
                  i.putExtra(PrestarServicoActivity.PRESTAR_SERVICO, "carona");
                  startActivityForResult(i, REQUEST_PRESTA_SERVICO);
				  
			  }
			  
		      return true;
		}
		
		

}
