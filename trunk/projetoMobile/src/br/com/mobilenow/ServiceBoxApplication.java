package br.com.mobilenow;


import org.holoeverywhere.app.Application;

import android.content.Context;
import br.com.servicebox.common.util.CommonConfigurationUtils;
import br.com.servicebox.common.util.CommonConfigurationUtils.CommonConfiguration;
import br.com.servicebox.common.util.GuiUtils;

public class ServiceBoxApplication extends Application{
	
	static final String TAG = ServiceBoxApplication.class.getSimpleName();

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
