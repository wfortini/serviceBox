package br.com.mobilenow.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.mobilenow.domain.Notificacao;
import br.com.servicebox.android.common.util.CommonUtils;

public class NotificacaoDAO {
	
	private static final String TAG = NotificacaoDAO.class.getSimpleName();
	public static final String TABELA_NOTIFICACAO = "notificacao";
	
	private DataBaseHelper helper;
	
	public NotificacaoDAO(Context context) {
		this.helper = new DataBaseHelper(context);
	}
	
	public boolean incluir(Notificacao notificacao){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("mensagem", notificacao.getMensagem());
		values.put("idSolicitante", notificacao.getIdSolicitante());
		values.put("idSolicitado", notificacao.getIdPrestacao());
		values.put("idPrestacao", notificacao.getIdPrestacao());
		values.put("fotoPrefil", notificacao.getFotoPerfil());
		values.put("tipoSolicitacao", notificacao.getTipoSolicitacao());
		values.put("dataSolicitacao", notificacao.getDataSolicitacao().getTime());
		
		long resultado = db.insert(TABELA_NOTIFICACAO, null, values);
		CommonUtils.debug(TAG, "Registro inserido " + resultado);
		return resultado != -1;	
		
	}
	
	

}
