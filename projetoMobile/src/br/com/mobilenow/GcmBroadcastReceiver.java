package br.com.mobilenow;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import br.com.servicebox.android.common.util.CommonUtils;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	
	public static final String TAG = "GcmBroadcastReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		CommonUtils.debug(TAG, "Iniciando GcmBroadcastReceiver");
		
		ComponentName comp = new ComponentName(context.getPackageName(),
				GcmIntentService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
		
		CommonUtils.debug(TAG, "Finalizando GcmBroadcastReceiver");
	}


}
