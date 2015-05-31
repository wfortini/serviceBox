package br.com.mobilenow;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.ProgressDialog;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import br.com.mobilenow.exception.GCMException;
import br.com.mobilenow.util.LoginUtils;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.net.Response;
import br.com.servicebox.common.net.UsuarioResponse;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class AcessoActivity extends CommonActivity {
	
	private static final String TAG = AcessoActivity.class.getSimpleName();
	private static final String PROJETO_ID = "994547673653";
	private GoogleCloudMessaging gcm;
	private UsuarioResponse mResponse;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso);
		 
		 if (GuiUtils.checkOnline()){
		    ServiceBoxApplication.setSocialAdapter(new SocialAuthAdapter(new ResponseListener()));
		 }
	}
	
	public void contaLoginButtonAction(View view) {
        CommonUtils.debug(TAG, "Iniciando acesso ao usuario ao sistema");
        //TrackerUtils.trackButtonClickEvent("account_login_button", AccountActivity.this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
	
	public void cadastroButtonAction(View view){
		 CommonUtils.debug(TAG, "Iniciando registro do usuario");
        //TrackerUtils.trackButtonClickEvent("account_login_button", AccountActivity.this);
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
	}
	
	public void contaLoginFacebook(View view){
		CommonUtils.debug(TAG, "Iniciando login facebook");
       //TrackerUtils.trackButtonClickEvent("account_login_button", AccountActivity.this);		
		ServiceBoxApplication.getSocialAdapter().authorize(AcessoActivity.this, Provider.FACEBOOK);	
		
	}
	
	
	private final class ResponseListener implements DialogListener 
	{
	   public void onComplete(Bundle values) {	          
		 
		   /** so entra aqui apos login no facebook **/
		   CommonUtils.debug(TAG, "Executando onComplete ...........................................");
		   ServiceBoxApplication.getSocialAdapter().getUserProfileAsync(new ProfileDataListener());                       
	   }
	   
	   @Override
		public void onBack() {
		   CommonUtils.debug(TAG, "Passou por onBack FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			
		}
	   
	   @Override
		public void onCancel() {
		   CommonUtils.debug(TAG, "Passou por onCancel FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			
		}
	   
	   @Override
		public void onError(SocialAuthError arg0) {
		   CommonUtils.debug(TAG, "Passou por onError ............ " + arg0.getMessage());
			
		}
	}
	
	//----------------------------------------------------------------------------------------------
	private final class ProfileDataListener implements SocialAuthListener<Profile> {

		@Override
		public void onExecute(String provider, Profile t) {
			CommonUtils.debug(TAG, "obtendo profile de forma assincrona ..............................");
			if(t != null && t.getValidatedId() != null)
			     new RequisicaoTask().execute(t);
		}

		@Override
		public void onError(SocialAuthError e) {
			CommonUtils.debug(TAG, "Erro obtendo profile de forma assincrona .........  " + e.getMessage());
		}
	}
	// -------------------------------------------------------------------------------------------------
	
	/** processamento assincrono **/	
   private class RequisicaoTask extends AsyncTask<Profile, Void, UsuarioResponse>{
			
	         UsuarioResponse response = null;
			
	         @Override
		        protected void onPreExecute() {
					super.onPreExecute();
					progressDialog = new ProgressDialog(AcessoActivity.this);
					progressDialog.setTitle(R.string.aguarde_por_favor);
					progressDialog.setMessage(CommonUtils.getStringResource(R.string.processando));
					progressDialog.setCancelable(false);
					progressDialog.show();
				}

			@Override
			protected UsuarioResponse doInBackground(Profile... params) {			 
				
				try {
					final String url = getString(R.string.ip_servidor_servicebox).
							                   concat(":8080/projetoWeb/autenticarRedeSocial.json");
					
					String regGCM = registrarDispositivoGCM();
					persistRegIdGCM(regGCM);
					Profile userSocial = params[0];
					
					RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                     
					 map.add("login", userSocial.getEmail());
					 map.add("nome", userSocial.getFirstName());
					 map.add("sobrenome", userSocial.getLastName());
					 map.add("senha", "");
					 map.add("sexo", userSocial.getGender());
					 map.add("apelido", userSocial.getFullName());
					 map.add("telefone", "");
					 map.add("regIdGCM", regGCM);
					 map.add("imagemPerfil", userSocial.getProfileImageURL());
					 map.add("socialId", userSocial.getValidatedId());
		             
		             HttpHeaders imageHeaders = new HttpHeaders();
		             imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		             HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);

		             
		             if (GuiUtils.checkOnline()){
					         response = restTemplate.postForObject(url, imageEntity, UsuarioResponse.class);
		             }
		             
		             return response;
					
				}catch(ResourceAccessException rae){
					CommonUtils.error(TAG, rae.getMessage());
					response = new UsuarioResponse(false, "Falha no cadastro do usuário \n Servidor não responde.", null, Response.ERRO_DESCONHECIDO);
				}catch(GCMException ge){
					CommonUtils.error(TAG, "Falha no registro GCM " + ge.getMessage());
					response = new UsuarioResponse(false, "Falha no cadastro do usuário, \n falha ao tenta registrar dispositivo no servidor GCM", null, Response.ERRO_DESCONHECIDO);		
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
					response = new UsuarioResponse(false, "Fallha no cadastro do usuário, tente novamente mais tarde.", null, Response.ERRO_DESCONHECIDO);
				}
				
				return response;
			}
			
			public void retornoRegistro(UsuarioResponse response){							
					
					if(Response.SUCESSO == response.getCode() && response.isSucesso()){
						GuiUtils.alert(response.getMessage());
						 Activity activity = AcessoActivity.this;
						 if (activity != null) {
				                LoginUtils.onLoggedIn(activity, true, response);
				         }
						
					}else if(Response.LOGIN_DUPLICADO == response.getCode()){						
						GuiUtils.alert(response.getMessage());
					}else if (Response.ERRO_NOME_INVALIDO == response.getCode()){						
						GuiUtils.alert(response.getMessage());
					}else if(Response.ERRO_PASSWORD_INVALIDO == response.getCode()){						
						GuiUtils.alert(response.getMessage());
					}else{
						GuiUtils.alert(response.getMessage());
					}			
			}		
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
			}
			
			@Override
			protected void onPostExecute(UsuarioResponse result) {				
				super.onPostExecute(result);
				progressDialog.dismiss();
				this.retornoRegistro(result);
			}
			
		}
   
   /**
    * Salva nas preferencias no usuario seu Registro no GCM
    * @param id String
    */
   private void persistRegIdGCM(String id){	   
	   ServiceBoxMobileUtil.storeRegistrationId(ServiceBoxApplication.getContext(), id, 
			   getSharedPreferences(AcessoActivity.class.getSimpleName(), Context.MODE_PRIVATE));	   
   }
   
   /**
	 * Registrar Dispositivo android no GCM e setar o id na variavel RegId
	 * @throws GCMException
	 */
	private String registrarDispositivoGCM() throws GCMException{		
		try{
			String regId = ServiceBoxMobileUtil.getRegistrationId(
					ServiceBoxApplication.getContext(), getSharedPreferences(AcessoActivity.class.getSimpleName(), Context.MODE_PRIVATE));
			if(regId != null && !"".equals(regId)){
				return regId;
			}else{
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(ServiceBoxApplication.getContext());
				}
			}			
			return gcm.register(PROJETO_ID);		
				
		}catch(Exception e){
			Log.e(TAG, e.getMessage());
			throw new GCMException(e.getMessage());
		}
		
	}	
}
