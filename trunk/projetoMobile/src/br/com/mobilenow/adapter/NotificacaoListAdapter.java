package br.com.mobilenow.adapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.mobilenow.R;
import br.com.mobilenow.ServiceBoxApplication;
import br.com.mobilenow.domain.Notificacao;
import br.com.mobilenow.util.ServiceBoxMobileUtil;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class NotificacaoListAdapter extends ArrayAdapter<Notificacao>{	
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<Notificacao> lista = new ArrayList<Notificacao>();	
	private String url;
	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();

	public NotificacaoListAdapter(Activity activity, List<Notificacao> list) {
		super(activity, android.R.layout.simple_list_item_1, list);
		this.activity = activity;
		this.lista = list;		
		
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Notificacao getItem(int location) {
		return lista.get(location);
	}
	

	public List<Notificacao> getLista() {
		return lista;
	}

	public void setLista(List<Notificacao> lista) {
		this.lista = lista;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.lista_item_notificacao, null);
		
		if (imageLoader == null)
			imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
		
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.imagem_perfil_notificacao);
		
		Notificacao notificacao = lista.get(position);	
		// thumbnail image
				thumbNail.setImageUrl(ServiceBoxMobileUtil.getUrlImagemPerfil(
						getUrl(), notificacao.getFotoPerfil(), 
						null), imageLoader);
		
		TextView mensagem = (TextView) convertView.findViewById(R.id.mensagem);		
		TextView data = (TextView) convertView.findViewById(R.id.dataMensagem);		
		
		mensagem.setText(notificacao.getMensagem());
		data.setText("Criação em: "+ServiceBoxMobileUtil.dateToString(notificacao.getDataSolicitacao(),
				DateFormat.SHORT));
		
		

		return convertView;
	}

}
