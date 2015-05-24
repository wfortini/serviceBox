package br.com.mobilenow;

import java.lang.ref.WeakReference;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.mobilenow.util.LoginUtils;
import br.com.mobilenow.util.LoginUtils.LoginActionHandler;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragmentUtils;
import br.com.servicebox.android.common.fragment.CommonRetainedFragmentWithTaskAndProgress;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.android.common.util.ObjectAccessor;
import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.net.UsuarioResponse;

public class LoginActivity extends CommonActivity {
	
	 private static final String TAG = LoginActivity.class.getSimpleName();
	 private  SocialAuthAdapter adapter; 
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso_login);		
		init();
	}
	
	void init() {
        getLoginFragment();        
        TextView signInInstructions = (TextView) findViewById(R.id.sign_in_instructions);
        signInInstructions.setText(Html.fromHtml(getString(R.string.sign_in_instructions)));
    }

	public void loginButtonAction(View view) {
        CommonUtils.debug(TAG, "Login do usuario");
        //TrackerUtils.trackButtonClickEvent("login_button", AccountLogin.this);

        EditText editText = (EditText) findViewById(R.id.edit_email);
        String email = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_password);
        String password = editText.getText().toString();

        if (!GuiUtils.validateBasicTextData(new String[] {
                email, password
        }, new int[] {
                R.string.campo_email, R.string.campo_password
        })) {
            return;
        }      
        
         // clean up login information
        //Preferences.logout(this);

        getLoginFragment().doLogin(email, password);
	}
	
	/**
     * Get the login fragment
     * 
     * @return
     */
    public LogInFragment getLoginFragment() {
        return CommonFragmentUtils
                .findOrCreateFragment(LogInFragment.class, getSupportFragmentManager());
    }
	
	
	public static class LogInFragment extends CommonRetainedFragmentWithTaskAndProgress implements
    LoginActionHandler {
		private static final String TAG = LogInFragment.class.getSimpleName();
		
		static WeakReference<LogInFragment> sCurrentInstance;
		static ObjectAccessor<LogInFragment> sCurrentInstanceAccessor = new ObjectAccessor<LogInFragment>() {
		    private static final long serialVersionUID = 1L;
		
		    @Override
		    public LogInFragment run() {
		        return sCurrentInstance == null ? null : sCurrentInstance.get();
		    }
		};
		
		boolean mDelayedLoginProcessing = false;
		UsuarioResponse mLastResponse;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    sCurrentInstance = new WeakReference<LogInFragment>(this);
		}
		
		@Override
		public void onDestroy() {
		    if (sCurrentInstance != null) {
		        if (sCurrentInstance.get() == LogInFragment.this
		                || sCurrentInstance.get() == null) {
		            CommonUtils.debug(TAG, "Nullify current instance");
		            sCurrentInstance = null;
		        } else {
		            CommonUtils.debug(TAG,
		                    "Skipped nullify of current instance, such as it is not the same");
		        }
		    }
		    super.onDestroy();
		}
		
		@Override
		public String getLoadingMessage() {
		    return CommonUtils.getStringResource(R.string.logging_in_message);
		}
		
		public void doLogin(String user, String pwd) {
		    startRetainedTask(new LogInUsuarioTask(new Credencial(user, pwd)));
		}
		
		/**
		 * Implementação da Interface LoginActionHandler presente na classe LoginUtil
		 */
		@Override  
		public void processLoginCredentials(Credenciais credentials, UsuarioResponse response) {
		    Activity activity = getSupportActivity();
		    //CredentialsUtils.saveCredentials(activity, credentials);
		    LoginUtils.onLoggedIn(activity, false,response);
		}
		
		void processLoginResonse(Activity activity) {
		    LoginUtils.processarSucessoLoginResultado(mLastResponse, sCurrentInstanceAccessor,
		            activity);
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    if (mDelayedLoginProcessing) {
		        mDelayedLoginProcessing = false;
		        processLoginResonse(getSupportActivity());
		    }
		}
		class LogInUsuarioTask extends RetainedTask {
		    private Credencial credencial;
		    UsuarioResponse responseResult = null;
		
		    public LogInUsuarioTask(Credencial credencial) {
		        this.credencial = credencial;
		    }
		
		    @Override
		    protected Boolean doInBackground(Void... params) {
		    	
		        try {
		        	
                     final String url =  getString(R.string.ip_servidor_servicebox)
                    		          .concat(":8080/projetoWeb/autenticar.json");
					
					RestTemplate restTemplate = ServiceBoxMobileUtil.getRestTemplate();					
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                     
					 map.add("login", credencial.getLogin());
					 map.add("pwd", credencial.getPwd());				
		             
		             HttpHeaders imageHeaders = new HttpHeaders();
		             imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		             HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);

	                
		            if (GuiUtils.checkOnline()){
		               
		            	responseResult = restTemplate.postForObject(url, imageEntity, UsuarioResponse.class);		            	
		                return  ServiceBoxMobileUtil.checkResponseValido(responseResult);
		            }		         
		        
		        } catch (Exception e) {
		            GuiUtils.error(TAG, R.string.errorCouldNotLogin, e);
		            
		        }
		        return false;
		    }
		
		    @Override
		    protected void onSuccessPostExecuteAdditional() {
		        try
		        {
		            mLastResponse = responseResult;
		            Activity activity = getSupportActivity();
		            if (activity != null) {
		                processLoginResonse(activity);
		            } else {
		                //TrackerUtils.trackErrorEvent("activity_null", TAG);
		                mDelayedLoginProcessing = true;
		            }
		        } catch (Exception e)
		        {
		            GuiUtils.error(TAG, e);
		        }
		    }
		}
		
		public class Credencial {
		    private String login;
		    private String pwd;
		
		    public Credencial(String login, String pwd) {
		        this.login = login;
		        this.pwd = pwd;
		    }
		
		    public String getLogin() {
		        return login;
		    }
		
		    public void setLogin(String user) {
		        this.login = user;
		    }
		
		    public String getPwd() {
		        return pwd;
		    }
		
		    public void setPwd(String pwd) {
		        this.pwd = pwd;
		    }
		}
		}
	
	
	
}
