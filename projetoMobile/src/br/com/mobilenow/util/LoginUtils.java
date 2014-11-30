
package br.com.mobilenow.util;


import org.holoeverywhere.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import br.com.mobilenow.ServiceBoxApplication;
import br.com.mobilenow.TabbedActivity;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.ObjectAccessor;
import br.com.servicebox.common.domain.Credenciais;
import br.com.servicebox.common.net.LoginResponse;

public class LoginUtils
{
    public static final String TAG = LoginUtils.class.getSimpleName();

    public static String LOGIN_ACTION = "com.trovebox.ACTION_LOGIN";

    public static BroadcastReceiver getAndRegisterDestroyOnLoginActionBroadcastReceiver(
            final String TAG, final Activity activity)
    {
        BroadcastReceiver br = new BroadcastReceiver()
        {

            @Override
            public void onReceive(Context context, Intent intent)
            {
                CommonUtils.debug(TAG, "Received login broadcast message");
                activity.finish();
            }
        };
        activity.registerReceiver(br, new IntentFilter(LOGIN_ACTION));
        return br;
    }

    public static void sendLoggedInBroadcast(Activity activity)
    {
        Intent intent = new Intent(LOGIN_ACTION);
        activity.sendBroadcast(intent);
    }

    /**
     * Should be called by external activities/fragments after the logged in
     * action completed
     * 
     * @param activity
     * @param finishActivity whether to finish activity after the MainActivity
     *            started
     */
    public static void onLoggedIn(Activity activity, boolean finishActivity, LoginResponse response) {
        // iniciar nova activity e adiciono o usuario logado na application
    	ServiceBoxApplication.setUsuario(response.preencherUsuario());
        activity.startActivity(new Intent(activity, TabbedActivity.class));
       
        if (finishActivity) {
            activity.finish();
        }
    }

    /**
     * Common successful AccountTroveboxResult processor
     * 
     * @param resultado
     * @param fragmentAccessor
     * @param activity
     */
    public static void processarSucessoLoginResultado(LoginResponse resultado,
            ObjectAccessor<? extends LoginActionHandler> fragmentAccessor, Activity activity) {
        Credenciais[] credenciais = resultado.getmCredenciais();
        if (credenciais.length == 1) {
            CommonUtils.debug(TAG, "processSuccessfulLoginResult: found one login credentials");
            performLogin(fragmentAccessor, credenciais[0], resultado);
        } else {
            CommonUtils
                    .debug(TAG, "processSuccessfulLoginResult: found multiple login credentials");
            //Intent intent = new Intent(activity, SelectAccountActivity.class);
            //intent.putParcelableArrayListExtra(SelectAccountActivity.CREDENTIALS,
            //        new ArrayList<Credenciais>(Arrays.asList(credentials)));
           // activity.startActivity(intent);
        }
    }

    private static void performLogin(ObjectAccessor<? extends LoginActionHandler> loginActionHandlerAccessor,
            Credenciais credentials, LoginResponse response) {
        LoginActionHandler handler = loginActionHandlerAccessor.run();
        if (handler != null) {
            handler.processLoginCredentials(credentials, response);
        } else {
            String error = "Current instance accessor returned null";
            CommonUtils.error(TAG, error);
           // TrackerUtils.trackException(error);
        }
    }

    /**
     * The interface the login/signup fragments should implement to use
     * processSuccessfulLoginResult method
     */
    public static interface LoginActionHandler
    {
        void processLoginCredentials(Credenciais credenciais, LoginResponse response);
    }
}
