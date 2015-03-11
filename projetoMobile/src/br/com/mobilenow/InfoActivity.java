package br.com.mobilenow;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class InfoActivity extends CommonActivity {	
	
	public static final String TAG = InfoActivity.class.getSimpleName();
	public static final String INFO_SERVICO = "INFO_SERVICO";
    public static final int RESULT_CODE = 456;   
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new InfoUiFragment())
	                .commit();
	    }
	}
    
    
    public static class InfoUiFragment extends CommonFragment{    	
    	
    	private android.widget.TextView tvNome;;
    	private TextView tvDescricao;
    	private TextView tvPartida;
    	private TextView tvDestino;
    	
    	private TextView tvDom;
    	private TextView tvSeg;
    	private TextView tvTer; 
    	private TextView tvQua; 
    	private TextView tvQui; 
    	private TextView tvSex; 
    	private TextView tvSab;
    	
    	private TextView labelEntre;
    	private TextView labelE;
    	private TextView tvHoraInicial;
    	private TextView tvHoraFinal;
    	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
    	
    	
    	private Info info;
    	
    	private Button btConfirma;
    	
    	@Override
    	public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		if (savedInstanceState != null) {
    			info = savedInstanceState.getParcelable(INFO_SERVICO);
            } else {
            	info = getActivity().getIntent().getParcelableExtra(INFO_SERVICO);
            }
    	}
    	
    	 @Override
         public void onSaveInstanceState(Bundle outState) {
             super.onSaveInstanceState(outState);
             outState.putParcelable(INFO_SERVICO, info);
         }
    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_info, container, false);
            return v;
        }
    	
    	 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
             init(view);
         }
    	 
    	private void init(View v){
    		 
    		tvDom =(TextView) v.findViewById(R.id.dom);
        	tvSeg =(TextView) v.findViewById(R.id.seg);
        	tvTer =(TextView) v.findViewById(R.id.ter);
        	tvQua =(TextView) v.findViewById(R.id.qua); 
        	tvQui =(TextView) v.findViewById(R.id.qui);
        	tvSex =(TextView) v.findViewById(R.id.sex); 
        	tvSab =(TextView) v.findViewById(R.id.sab);
        	tvNome = (TextView) v.findViewById(R.id.nome);
        	tvDescricao = (TextView) v.findViewById(R.id.descricao);
        	
        	tvHoraInicial = (TextView) v.findViewById(R.id.horaInicial);
        	tvHoraFinal = (TextView) v.findViewById(R.id.horarioFinal);
        	labelEntre = (TextView) v.findViewById(R.id.labelEntre);
        	labelE = (TextView) v.findViewById(R.id.labelE);
        	
        	tvPartida = (TextView) v.findViewById(R.id.dadosPartida);
        	tvDestino = (TextView) v.findViewById(R.id.dadosDestino);
        	
        	if(info.getPlanejamento().isDomingo())
        		tvDom.setLinkTextColor(Color.BLUE);
        	
        	tvHoraInicial.setText(info.getPlanejamento().getHoraEntre());
        	tvHoraFinal.setText(info.getPlanejamento().getHoraE());
        	
        	tvNome.setText(info.getNomeUsuario());
        	tvDescricao.setText(info.getDescricao());
        	
        	tvPartida.setText(info.getItinerario().getPartida().getEnderecoPartida());
        	tvDestino.setText(info.getItinerario().getDestino().getEnderecoDestino());
        	
        	NetworkImageView thumbNail = (NetworkImageView) v.findViewById(R.id.imagem_perfil);
        	
        	// thumbnail image
    		thumbNail.setImageUrl(ServiceBoxMobileUtil.getUrlImagemPerfil(
    				getString(R.string.ip_servidor_servicebox), info.getFotoPerfilUsuario(), 
    				info.getLoginUsuario()), imageLoader);
    		 
    		 btConfirma = (Button) v.findViewById(R.id.registrar);
    		 btConfirma.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					confirmar();
					
				}
			});
    		 
    		 
      }   	 
    	 
    	 /**
          * Confirma que prestar o serviço
          *         
          */
         public void confirmar() {
        	 
             getActivity().finish();
        	 
        	 
         	
         }
	 
   } // fim classe interna Fragment
    
    
    
}
