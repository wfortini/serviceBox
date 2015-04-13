package br.com.mobilenow.dao;

import br.com.mobilenow.GcmIntentService;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	
	private static final String TAG = DataBaseHelper.class.getSimpleName();
	private static final String BANCO_DADOS = "ServiceBoxDB";
	private static int VERSAO = 1;
	
	
	public DataBaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + NotificacaoDAO.TABELA_NOTIFICACAO + " (_id INTEGER PRIMARY KEY," +
				" mensagem TEXT, idSolicitante INTEGER, idSolicitado INTEGER, " +
				" idPrestacao INTEGER, fotoPrefil TEXT, tipoSolicitacao INTEGER, dataSolicitacao DATE );");
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
