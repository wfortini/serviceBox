package br.com.mobilenow.componente;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Watson.OnCreateOptionsMenuListener;
import android.support.v4.app.Watson.OnOptionsItemSelectedListener;
import android.support.v4.app.Watson.OnPrepareOptionsMenuListener;
import android.view.View;
import android.widget.TextView;
import br.com.mobilenow.ListaNotificacoesActivity;
import br.com.mobilenow.R;
import br.com.mobilenow.dao.NotificacaoDAO;
import br.com.mobilenow.util.NotificacaoMenuItemListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.SupportMapFragment;

 
public class SherlockMapFragment extends SupportMapFragment implements OnCreateOptionsMenuListener, OnPrepareOptionsMenuListener, OnOptionsItemSelectedListener {
    private SherlockFragmentActivity mActivity;
 
    public SherlockFragmentActivity getSherlockActivity() {
        return mActivity;
    }
 
    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof SherlockFragmentActivity)) {
            throw new IllegalStateException(getClass().getSimpleName() + " must be attached to a SherlockFragmentActivity.");
        }
        mActivity = (SherlockFragmentActivity)activity;        
        super.onAttach(activity);
    }
 
    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }
 
    @Override
    public final void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
        onCreateOptionsMenu(new MenuWrapper(menu), mActivity.getSupportMenuInflater());
    }
 
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Nothing to see here.
    }
 
    @Override
    public final void onPrepareOptionsMenu(android.view.Menu menu) {
        onPrepareOptionsMenu(new MenuWrapper(menu));
    }
 
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //Nothing to see here.
    }
 
    @Override
    public final boolean onOptionsItemSelected(android.view.MenuItem item) {
        return onOptionsItemSelected(new MenuItemWrapper(item));
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Nothing to see here.
        return false;
    }
   
    /**
     * Este metodo cria, mantem e executa ação de abrir uma tela de notificações. 
     * O icone de notificações é exibido no menu e é atualizando quanto ao numero de 
     * notificações que exite para o usuario.
     * 
     * @param menu Menu
     * @param activity Context
     * @param txtNumNotificacoes TextView 
     * @param handlerNotificacao handle para tratar a thread da GUI
     */
   public void manterNotificacoes(Menu menu, Context activity, 
    		TextView txtNumNotificacoes, Handler handlerNotificacao){    	
    	
    	NotificacaoDAO dao = null;
		final View menuNumNotificacoes = menu.findItem(R.id.menu_notificacao).getActionView();
	    txtNumNotificacoes = (TextView) menuNumNotificacoes.findViewById(R.id.lbl_notificao);
	    try{
	    	dao = new NotificacaoDAO(getActivity());
	    	atualizarContadorDeNotificacao(dao.getCount(), txtNumNotificacoes, handlerNotificacao);
	    }finally{
	       dao.close();	
	    }
	    
	    new NotificacaoMenuItemListener(menuNumNotificacoes, "Show hot message") {
	        @Override
	        public void onClick(View v) {
	        	Intent i = new Intent(getActivity(), ListaNotificacoesActivity.class);                 
                startActivity(i);
	        }
	    };
    	
    }
    
    private void runOnUiThread(Runnable runna, Handler handlerNotificacao){
		handlerNotificacao.post(runna);
	}
	
	
    private void atualizarContadorDeNotificacao(final int new_hot_number, 
    		final TextView txtNumNotificacoes, Handler handlerNotificacao) {
	    
	    if (txtNumNotificacoes == null) return;
	    runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	            if (new_hot_number == 0)
	                txtNumNotificacoes.setVisibility(View.INVISIBLE);
	            else {
	                txtNumNotificacoes.setVisibility(View.VISIBLE);
	                txtNumNotificacoes.setText(Integer.toString(new_hot_number));
	            }
	        }
	    }, handlerNotificacao);
	}
}