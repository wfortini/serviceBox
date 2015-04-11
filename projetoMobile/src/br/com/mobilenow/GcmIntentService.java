package br.com.mobilenow;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import br.com.mobilenow.R;
import br.com.servicebox.android.common.util.CommonUtils;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {	
	  
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	
	static final String GOOGLE_PROJECT_ID = "994547673653";
	static final String MESSAGE_KEY = "mensagem";
	private String msg;

	public GcmIntentService() {
		super(GOOGLE_PROJECT_ID);
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		
		CommonUtils.debug(TAG, "Iniciando GCMNotificationIntentService");
		
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);
		
		 msg = extras.getString("title");

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				
				sendNotification("Error: " + extras.toString());
				
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				
				sendNotification("Deletado: " + extras.toString());
				
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				
				Log.i(TAG, "Mensagem " + extras.get(MESSAGE_KEY));

				sendNotification("Message Received from Google GCM Server: "
						+ extras.get(MESSAGE_KEY));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
		
		CommonUtils.debug(TAG, "Finalizando GCMNotificationIntentService");
	}

	/**
	 * cria notificação
	 * @param msg
	 */
	private void sendNotification(String msg) {
		
		Log.d(TAG, "Preparando mensagem de notificação...: " + msg);
		
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.gcm_cloud)
				.setContentTitle(getString(R.string.serviceBox_notificacao))
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		
		Log.d(TAG, "Mensagem de notificação processada com sucesso.");
	}


}
