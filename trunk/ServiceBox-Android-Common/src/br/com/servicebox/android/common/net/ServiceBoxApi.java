package br.com.servicebox.android.common.net;

/**
 * 
 * @author wpn0510
 *
 */
public class ServiceBoxApi implements IServiceBoxApi{
	
	private static IServiceBoxApi api;
	
	public static IServiceBoxApi createInstance() {
        if (api != null) {
            return api;
        } else {
            return new ServiceBoxApi();
        }
    }
	
	public ServiceBoxApi() {
		super();
	}

}
