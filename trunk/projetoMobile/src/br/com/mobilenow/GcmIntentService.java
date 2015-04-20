package br.com.mobilenow;

import java.util.Date;

import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import br.com.mobilenow.dao.NotificacaoDAO;
import br.com.mobilenow.domain.Notificacao;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.common.domain.StatusSolicitacao;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {	
	  
	static final String TAG = GcmIntentService.class.getSimpleName();
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	
	static final String GOOGLE_PROJECT_ID = "994547673653";
	static final String MESSAGE_KEY = "mensagem";
	private String msg;
	private JSONObject json;
	private Bundle extras;

	public GcmIntentService() {
		super(GOOGLE_PROJECT_ID);
	}	

	@Override
	protected void onHandleIntent(Intent intent) {
		
		CommonUtils.debug(TAG, "Iniciando GCMNotificationIntentService");
		
		extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);
		

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				
				sendNotification("Error: " + extras.toString());
				
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				
				sendNotification("Deletado: " + extras.toString());
				
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {				
			
				Notificacao notificacao = this.getNotificacao(extras.get(MESSAGE_KEY).toString());
				if(this.processarNotificacao(notificacao))
				     sendNotification(notificacao.getMensagem());			
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
				new Intent(this, AcessoActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.gcm_cloud)
				.setContentTitle(getString(R.string.serviceBox_notificacao))
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		
		Log.d(TAG, "Mensagem de notificação processada com sucesso.");
	}
	
	public boolean processarNotificacao(Notificacao notificacao){
		
		NotificacaoDAO dao = new NotificacaoDAO(this);
		boolean retorno = false;
		
		Notificacao not = dao.buscarNotificacao(notificacao.getIdSolicitante(),
				notificacao.getIdSolicitado(), notificacao.getIdPrestacao());
		
		if(not != null && not.getIdSolicitacao() != null){
		   retorno = dao.atualizarNotificacao(notificacao);	
		   dao.close();
		   return retorno;
			
		}else{
			retorno = dao.incluir(notificacao);
			dao.close();
			return retorno;
		}
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	private Notificacao getNotificacao(String strJSON) {
		
		Notificacao notificacao = new Notificacao();
		
		try {
			json = new JSONObject(strJSON);
			notificacao.setMensagem(json.getString("mensagem"));;
			notificacao.setIdSolicitante(json.getInt("idSolicitante"));
			notificacao.setIdSolicitado(json.getInt("idSolicitado"));
			notificacao.setIdPrestacao(json.getInt("idPrestacao"));
			notificacao.setFotoPerfil(json.getString("imagemPefil"));
			notificacao.setTipoSolicitacao(json.getInt("tipoSolicitacao"));
			notificacao.setDataSolicitacao(new Date());
			notificacao.setStatusNotificacao(json.getInt("status"));
			notificacao.setIdSolicitacao(json.getInt("idSolicitacao"));
			
			return notificacao;
		} catch (Exception e) {
			CommonUtils.error(TAG, "Erro parse Json for notificação: " + e.getMessage());
		}
		
		return notificacao;
		
	}


}
