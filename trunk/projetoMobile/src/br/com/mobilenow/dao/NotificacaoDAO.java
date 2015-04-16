package br.com.mobilenow.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gms.internal.cu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.mobilenow.domain.Notificacao;
import br.com.servicebox.android.common.util.CommonUtils;

public class NotificacaoDAO {
	
	private static final String TAG = NotificacaoDAO.class.getSimpleName();
	public static final String TABELA_NOTIFICACAO = "notificacao";
	
	private DataBaseHelper helper;
	private SQLiteDatabase db;
	
	public NotificacaoDAO(Context context) {
		this.helper = new DataBaseHelper(context);
	}
	
	public void close(){
		helper.close();
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
		CommonUtils.debug(TAG, "Registro inserido ============= " + resultado);
		return resultado != -1;	
		
	}
	
	public List<Notificacao> listarNotificacoes(){
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor =
		db.rawQuery("SELECT _id, mensagem, idSolicitante, " +
				"idSolicitado, idPrestacao, fotoPrefil, tipoSolicitacao, dataSolicitacao" +
				" FROM " + TABELA_NOTIFICACAO, null);
		
				cursor.moveToFirst();
				List<Notificacao> notificacoes = new ArrayList<Notificacao>();
				
				for (int i = 0; i < cursor.getCount(); i++) {
					
					Notificacao n = new Notificacao();
					n.set_id(cursor.getInt(0));
					n.setMensagem(cursor.getString(1));
					n.setIdSolicitante(cursor.getInt(2));
					n.setIdSolicitado(cursor.getInt(3));
					n.setIdPrestacao(cursor.getInt(4));
					n.setFotoPerfil(cursor.getString(5));
					n.setTipoSolicitacao(cursor.getInt(6));
					n.setDataSolicitacao(new Date(cursor.getLong(7)));
					
					notificacoes.add(n);	
					cursor.moveToNext();
					
				}
				cursor.close();				
				return notificacoes;
	}
	
	

}
