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
import br.com.mobilenow.util.Info;
import br.com.mobilenow.util.ServiceBoxMobileUtil;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Esta é uma classe adaptador personalizada que fornece dados para exibição de lista. 
 * Em outras palavras, torna o layout_row.xml na lista de pré-preenchimento de informação adequado.
 * 
 * @author wpn0510
 *
 */
public class PrestarServicoListAdapter extends ArrayAdapter<Info>{
	
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<Info> lista = new ArrayList<Info>();	
	private String url;
	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();

	public PrestarServicoListAdapter(Activity activity, List<Info> list) {
		super(activity, android.R.layout.simple_list_item_1, list);
		this.activity = activity;
		this.lista = list;		
		
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Info getItem(int location) {
		return lista.get(location);
	}
	

	public List<Info> getLista() {
		return lista;
	}

	public void setLista(List<Info> lista) {
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
			convertView = inflater.inflate(R.layout.lista_item_prestarservico, null);
		
		if (imageLoader == null)
			imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
		
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.imagem_perfil);
		
		Info info = lista.get(position);	
		// thumbnail image
				thumbNail.setImageUrl(ServiceBoxMobileUtil.getUrlImagemPerfil(
						getUrl(), info.getFotoPerfilUsuario(), info.getLoginUsuario()), imageLoader);
		
		TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
		
		//TODO: classificação sera estrelas, tera uma regra de a cada 10 recmoendações recebera uma estrala
		TextView classificacao = (TextView) convertView.findViewById(R.id.classificacao);
		TextView recomendacao = (TextView) convertView.findViewById(R.id.recomendacoes);
		TextView data = (TextView) convertView.findViewById(R.id.dataCriacao);
		TextView nomeUsuario = (TextView) convertView.findViewById(R.id.nomeUsuario);
		TextView dadosPartida = (TextView) convertView.findViewById(R.id.dadosPartida);
		TextView dadosDestino = (TextView) convertView.findViewById(R.id.dadosDestino);
			
		
		dadosPartida.setText(info.getItinerario().getPartida().getEnderecoPartida());
		dadosDestino.setText(info.getItinerario().getDestino().getEnderecoDestino());
		descricao.setText(info.getDescricao() != null ? info.getDescricao() : "Descrição default");
		classificacao.setText("Text fixo  no código");
		recomendacao.setText("100 vezes recomendado!");
		nomeUsuario.setText(info.getNomeUsuario());
		data.setText("Data de criação: "+ServiceBoxMobileUtil.dateToString(info.getData(), DateFormat.FULL));
		
		

		return convertView;
	}


}
