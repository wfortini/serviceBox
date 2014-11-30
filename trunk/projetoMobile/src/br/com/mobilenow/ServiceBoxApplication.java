package br.com.mobilenow;


import org.holoeverywhere.app.Application;

import android.content.Context;
import br.com.servicebox.android.common.util.CommonConfigurationUtils;
import br.com.servicebox.android.common.util.CommonConfigurationUtils.CommonConfiguration;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.Usuario;

public class ServiceBoxApplication extends Application{
	
	static final String TAG = ServiceBoxApplication.class.getSimpleName();
	
	private static Usuario usuario;

    public ServiceBoxApplication()
    {
        CommonConfigurationUtils.setup(new ServiceBoxConfiguration(), this);
    }

    public static Context getContext()
    {
        return CommonConfigurationUtils.getApplicationContext();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();        
        GuiUtils.setup();
    }   
    
    public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		ServiceBoxApplication.usuario = usuario;
	}




	public static class ServiceBoxConfiguration implements CommonConfiguration {

        @Override
        public boolean isLoggedIn() {
            return false;
        }

        @Override
        public boolean isSelfHosted() {
            return false;
        }

        @Override
        public boolean isV2ApiAvailable() {
            return false;
        }

        @Override
        public boolean isWiFiOnlyUploadActive() {
            return false;
        }

       

        @Override
        public String getServer() {
            return null;
        }

    } 

}
