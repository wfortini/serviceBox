package br.com.mobilenow.dao;

import br.com.servicebox.android.common.util.CommonUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	
	private static final String TAG = DataBaseHelper.class.getSimpleName();
	private static final String BANCO_DADOS = "ServiceBoxDB";
	private static int VERSAO = 3;
	
	
	public DataBaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + NotificacaoDAO.TABELA_NOTIFICACAO + " (_id INTEGER PRIMARY KEY," +
				" mensagem TEXT, idSolicitante INTEGER, idSolicitado INTEGER, " +
				" idPrestacao INTEGER, fotoPrefil TEXT, tipoSolicitacao INTEGER, dataSolicitacao DATE, " +
				" statusNotificacao INTEGER, idSolicitacao INTEGER );");
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("ALTER TABLE " + NotificacaoDAO.TABELA_NOTIFICACAO +  " ADD COLUMN idSolicitacao INTEGER");
		
		CommonUtils.debug(TAG, " Tabela alterada com sucessoo ============= ");
	}

}
