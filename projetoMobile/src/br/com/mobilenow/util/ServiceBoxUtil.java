package br.com.mobilenow.util;

import android.text.TextUtils;
import br.com.mobilenow.R;
import br.com.servicebox.common.net.Response;
import br.com.servicebox.common.util.GuiUtils;

public class ServiceBoxUtil {
	
	private static final String TAG = ServiceBoxUtil.class.getSimpleName();
	
	public static boolean checkResponseValid(Response response)
    {
        boolean result = response.isSucesso();
        if (!result)
        {
            String message = response.getMessage();
            if (!TextUtils.isEmpty(message))
            {
                GuiUtils.alert(message);
            } else
            {
                GuiUtils.alert(R.string.erroNaoConhecido, response.getCode());
            }
        }
        return result;
    }
    

}
