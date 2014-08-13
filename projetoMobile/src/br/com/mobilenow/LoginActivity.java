package br.com.mobilenow;

import java.lang.ref.WeakReference;

import org.holoeverywhere.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import br.com.mobilenow.util.LoginUtils;
import br.com.mobilenow.util.LoginUtils.LoginActionHandler;
import br.com.servicebox.common.activity.CommonActivity;
import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.fragment.CommonFragmentUtils;
import br.com.servicebox.common.fragment.CommonRetainedFragmentWithTaskAndProgress;
import br.com.servicebox.common.net.LoginResponse;
import br.com.servicebox.common.util.CommonUtils;
import br.com.servicebox.common.util.GuiUtils;
import br.com.servicebox.common.util.ObjectAccessor;

public class LoginActivity extends CommonActivity {
	
	 private static final String TAG = LoginActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso_login);
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
        
        Intent intent = new Intent(this, TabbedActivity.class);
        startActivity(intent);
        
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
		LoginResponse mLastResponse;
		
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
		
		@Override
		public void processLoginCredentials(Credenciais credentials) {
		    Activity activity = getSupportActivity();
		    //CredentialsUtils.saveCredentials(activity, credentials);
		    LoginUtils.onLoggedIn(activity, false);
		}
		
		void processLoginResonse(Activity activity) {
		    LoginUtils.processSuccessfulLoginResult(mLastResponse, sCurrentInstanceAccessor,
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
		    LoginResponse result;
		
		    public LogInUsuarioTask(Credencial credencial) {
		        this.credencial = credencial;
		    }
		
		    @Override
		    protected Boolean doInBackground(Void... params) {
		        try {
		            if (GuiUtils.checkOnline())
		            {
		                //result = IAccountTroveboxApiFactory.getApi()
		                //        .signIn(credentials.getUser(), credentials.getPwd());
		                return null; //TroveboxResponseUtils.checkResponseValid(result);
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
		            mLastResponse = result;
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
