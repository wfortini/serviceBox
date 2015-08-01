package br.com.mobilenow;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.mobilenow.adapter.LazyAdapter;
import br.com.mobilenow.adapter.NotificacaoListAdapter;
import br.com.mobilenow.dao.NotificacaoDAO;
import br.com.mobilenow.domain.Notificacao;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;

import com.android.volley.toolbox.ImageLoader;

public class ListaNotificacoesActivity extends CommonActivity {

	public static final String TAG = ListaNotificacoesActivity.class.getSimpleName();
	public static final String LOCALIZAR_POR_ITINERARIO = "LOCALIZAR_POR_ITINERARIO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new ListarNotificacoesFragment())
	                .commit();
	    }
	}
	
	public static class ListarNotificacoesFragment extends CommonFragment implements
    OnItemClickListener {

	private NotificacaoListAdapter mAdapter;
	//private LazyAdapter	mAdapter;
	//private ProgressDialog progressDialog;
	private List<Notificacao> notificacoes = new ArrayList<Notificacao>();
	private ListView listViewNotificacoes;
   // private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();  	
	
	//private Notificacao notificacao;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (savedInstanceState != null) {
//			itinerario = savedInstanceState.getParcelable(LOCALIZAR_POR_ITINERARIO);
//        } else {
//        	itinerario = getActivity().getIntent().getParcelableExtra(LOCALIZAR_POR_ITINERARIO);
//        }
	}
	
	 @Override
     public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
   //      outState.putParcelable(LOCALIZAR_POR_ITINERARIO, itinerario);
     }
	
	@Override
	public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
	  View v = inflater.inflate(R.layout.activity_lista_notificacoes, container, false);
	  return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	   super.onViewCreated(view, savedInstanceState);
	   init(view, savedInstanceState);
	  
	}
	
	void init(View v, Bundle savedInstanceState){
		
	   NotificacaoDAO dao = null;
	   try{
		listViewNotificacoes = (ListView) v.findViewById(R.id.listaNotificacoes);            
		listViewNotificacoes.setOnItemClickListener(this);
		listViewNotificacoes.setEmptyView(v.findViewById(R.id.elementoVazio));
		
		dao = new NotificacaoDAO(getActivity());
		notificacoes = dao.listarNotificacoesPorStatus();		
		mAdapter = new NotificacaoListAdapter(getActivity(), notificacoes);
		mAdapter.setUrl(getString(R.string.ip_servidor_servicebox));
		listViewNotificacoes.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	   }finally{
		   dao.close();
	   }
	} // fim metodo init      
	  
	
	
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
	 
	
	}
	
	}
	
	
	
	
	
}
