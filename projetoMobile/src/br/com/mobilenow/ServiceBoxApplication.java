package br.com.mobilenow;


import org.holoeverywhere.app.Application;

import android.content.Context;
import android.text.TextUtils;
import br.com.mobilenow.util.LruBitmapCache;
import br.com.servicebox.android.common.util.CommonConfigurationUtils;
import br.com.servicebox.android.common.util.CommonConfigurationUtils.CommonConfiguration;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.Usuario;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ServiceBoxApplication extends Application{
	
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	static final String TAG = ServiceBoxApplication.class.getSimpleName();
	
	private static Usuario usuario;
	private static ServiceBoxApplication mInstance;

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
        mInstance = this;
    }  
    
    public static synchronized ServiceBoxApplication getInstance() {
		return mInstance;
	}
    
    public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		ServiceBoxApplication.usuario = usuario;
	}
	
	
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
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
