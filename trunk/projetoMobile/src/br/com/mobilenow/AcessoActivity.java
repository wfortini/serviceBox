package br.com.mobilenow;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.util.CommonUtils;

public class AcessoActivity extends CommonActivity {
	
	private static final String TAG = AcessoActivity.class.getSimpleName();
	private ImageButton facebook_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso);
		
		ServiceBoxApplication.setSocialAdapter(new SocialAuthAdapter(new ResponseListener()));
		 
		 facebook_button = (ImageButton)findViewById(R.id.bt_fb_login);	     
	    
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
		
       //Intent intent = new Intent(this, LoginActivity.class);
       //startActivity(intent);
	}
	
	
	private final class ResponseListener implements DialogListener 
	{
	   public void onComplete(Bundle values) {	          
		 
		   CommonUtils.debug(TAG, "Passou por onComplete FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		   Profile user = ServiceBoxApplication.getSocialAdapter().getUserProfile();
		   
		   System.out.println(user.getDisplayName());
	                       
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
		   CommonUtils.debug(TAG, "Passou por onError FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
			
		}
	}
}
