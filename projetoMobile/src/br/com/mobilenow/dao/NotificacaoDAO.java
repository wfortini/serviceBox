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
		values.put("idSolicitado", notificacao.getIdSolicitado());
		values.put("idPrestacao", notificacao.getIdPrestacao());
		values.put("fotoPrefil", notificacao.getFotoPerfil());
		values.put("tipoSolicitacao", notificacao.getTipoSolicitacao());
		values.put("dataSolicitacao", notificacao.getDataSolicitacao().getTime());
		values.put("statusNotificacao", notificacao.getStatusNotificacao());	
		values.put("idSolicitacao", notificacao.getIdSolicitacao());
		
		long resultado = db.insert(TABELA_NOTIFICACAO, null, values);
		CommonUtils.debug(TAG, "Registro inserido ============= " + resultado);
		return resultado != -1;	
		
	}
	
	public List<Notificacao> listarNotificacoes(){
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor =
		db.rawQuery("SELECT _id, mensagem, idSolicitante, " +
				"idSolicitado, idPrestacao, fotoPrefil, tipoSolicitacao, dataSolicitacao, statusNotificacao," +
				" idSolicitacao" +
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
					n.setStatusNotificacao(cursor.getInt(8));
					n.setIdSolicitacao(cursor.getInt(9));
					
					notificacoes.add(n);	
					cursor.moveToNext();
					
				}
				cursor.close();				
				return notificacoes;
	}
	
    public Notificacao buscarNotificacao(Integer idSolicitante, Integer idSolicitado, Integer idPrestacao){
		
    	String[] param = new String[3];
    	
    	if(idSolicitante != null)
    		param[0] = String.valueOf(idSolicitante);
    	
    	if(idSolicitado != null)
    		param[1] = String.valueOf(idSolicitado);
    	
    	if(idPrestacao != null)
    		param[2] = String.valueOf(idPrestacao);
    	
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor =
		db.rawQuery("SELECT _id, mensagem, idSolicitante, " +
				"idSolicitado, idPrestacao, fotoPrefil, tipoSolicitacao, dataSolicitacao, statusNotificacao," +
				" idSolicitacao FROM " + TABELA_NOTIFICACAO + " WHERE idSolicitante = ? AND" +
						" idSolicitado = ? AND idPrestacao = ?" , param);
		
		        Notificacao n = null;
		        
				if(cursor != null && cursor.getCount() > 0){
					cursor.moveToFirst();
					
					n = new Notificacao();
					n.set_id(cursor.getInt(0));
					n.setMensagem(cursor.getString(1));
					n.setIdSolicitante(cursor.getInt(2));
					n.setIdSolicitado(cursor.getInt(3));
					n.setIdPrestacao(cursor.getInt(4));
					n.setFotoPerfil(cursor.getString(5));
					n.setTipoSolicitacao(cursor.getInt(6));
					n.setDataSolicitacao(new Date(cursor.getLong(7)));
					n.setStatusNotificacao(cursor.getInt(8));
					n.setIdSolicitacao(cursor.getInt(9));
					
					cursor.close();						
				}
							
				return n;
	}
    
    public boolean atualizarStatusNotificacao(Notificacao notificacao){
    	
        SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("statusNotificacao", notificacao.getStatusNotificacao());			
		
		return (db.update(TABELA_NOTIFICACAO, values, "_id" + " = ?",
               new String[] { String.valueOf(notificacao.get_id())})) != -1;		
   	
   }
    
  public boolean atualizarNotificacaoPorIdSolicitacao(Notificacao notificacao){
    	
         SQLiteDatabase db = helper.getWritableDatabase();
         
         String[] param = new String[3];
     	
     	if(notificacao.getIdSolicitante() != null)
     		param[0] = String.valueOf(notificacao.getIdSolicitante());
     	
     	if(notificacao.getIdSolicitado() != null)
     		param[1] = String.valueOf(notificacao.getIdSolicitado());
     	
     	if(notificacao.getIdPrestacao() != null)
     		param[2] = String.valueOf(notificacao.getIdPrestacao());
		
		ContentValues values = new ContentValues();
		values.put("statusNotificacao", notificacao.getStatusNotificacao());
		values.put("mensagem", notificacao.getMensagem());	
		
		return (db.update(TABELA_NOTIFICACAO, values, " idSolicitante = ? AND idSolicitado = ? and idPrestacao = ? ",
                param )) != -1;		
    	
    }
    
    public List<Notificacao> listarNotificacoesPorStatus(){
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor =
		db.rawQuery("SELECT _id, mensagem, idSolicitante, " +
				"idSolicitado, idPrestacao, fotoPrefil, tipoSolicitacao, dataSolicitacao, statusNotificacao," +
				" idSolicitacao" +
				" FROM " + TABELA_NOTIFICACAO + " WHERE (statusNotificacao = 1 Or statusNotificacao = 2" +
						" or statusNotificacao = 3) and fotoPrefil is not null", null);
		
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
					n.setStatusNotificacao(cursor.getInt(8));
					n.setIdSolicitacao(cursor.getInt(9));
					
					notificacoes.add(n);	
					cursor.moveToNext();
					
				}
				cursor.close();				
				return notificacoes;
	}
    
   public void deleteAll() {    	 
        
        SQLiteDatabase db = helper.getReadableDatabase();        
        db.delete(TABELA_NOTIFICACAO, //table name
                null, null); 
       
        db.close();
 
    
 
    }
   
   public void deletePorId(Integer idNotificacao){    	 
       
       SQLiteDatabase db = helper.getReadableDatabase();        
       db.delete(TABELA_NOTIFICACAO, //table name
               "_id = ?", 
               new String[] { String.valueOf(idNotificacao) }); 
      
       db.close();

   

   }
   
   public  int getCount(){
			
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor cursor =
			db.rawQuery("SELECT _id FROM " + TABELA_NOTIFICACAO + " WHERE (statusNotificacao = 1 Or statusNotificacao = 2" +
							" or statusNotificacao = 3) and fotoPrefil is not null", null);
			
					cursor.moveToFirst();
					int retorno = cursor.getCount();
					
					cursor.close();				
					return retorno;
		}
	
	

}
