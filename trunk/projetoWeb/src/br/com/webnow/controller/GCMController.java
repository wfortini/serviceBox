package br.com.webnow.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Controller
public class GCMController {
	
	private final static Logger logger = LoggerFactory.getLogger(GCMController.class);
	
	private String SENDER_ID = "AIzaSyB5i8wHc2EaRehgBy98hKV3DlTcJ5UEQYE";
	private String collapKey;
	private String message;
	private List<String> listaIdAndroids = new ArrayList<String>();
	private Sender sender;
	
   private void sendGCM(String regId, String mensagem){
  		
		this.sender = new Sender(SENDER_ID);
		
		Result result = null;
		
		Message message = new Message.Builder()
		.collapseKey(this.collapKey)
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", mensagem)
		.build();
		
		try {
			 result = this.sender.send(message, regId, 1);
			if(result != null){
				String id = result.getCanonicalRegistrationId();
			
        } else {
             String error = result.getErrorCodeName();
            System.out.println("Broadcast failure ======================: " + error);
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
   private void sendGCMBroadcast(List<String> listaId, String mensagem){
		
		this.sender = new Sender(SENDER_ID);
		
		Message message = new Message.Builder()
		.collapseKey(this.collapKey)
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", mensagem)
		.build();
		
		try {
			MulticastResult result = this.sender.send(message, listaId, 1);
			if(result.getResults() != null){
				int regId = result.getCanonicalIds();
				if(regId != 0){
			}
        } else {
            int error = result.getFailure();
            System.out.println("Broadcast failure ======================: " + error);
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}



}
