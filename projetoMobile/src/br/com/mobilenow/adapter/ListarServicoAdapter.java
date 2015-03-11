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
import br.com.mobilenow.domain.PrestarServico;
import br.com.mobilenow.domain.Usuario;
import br.com.mobilenow.util.ServiceBoxMobileUtil;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ListarServicoAdapter extends ArrayAdapter<PrestarServico>{
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<PrestarServico> lista = new ArrayList<PrestarServico>();
	private Usuario usuario;
	private String url;
	private ImageLoader imageLoader = ServiceBoxApplication.getInstance().getImageLoader();

	public ListarServicoAdapter(Activity activity, List<PrestarServico> list) {
		super(activity, android.R.layout.simple_list_item_1, list);
		this.activity = activity;
		this.lista = list;
		this.usuario = ServiceBoxApplication.getUsuario();
		
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public PrestarServico getItem(int location) {
		return lista.get(location);
	}
	

	public List<PrestarServico> getLista() {
		return lista;
	}

	public void setLista(List<PrestarServico> lista) {
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
			convertView = inflater.inflate(R.layout.lista_item_listaservico, null);
		
		if (imageLoader == null)
			imageLoader = ServiceBoxApplication.getInstance().getImageLoader();
		
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);
		
		// thumbnail image
				thumbNail.setImageUrl(ServiceBoxMobileUtil.getUrlImagemPerfil(
						getUrl(), usuario.getFotoPerfil(), usuario.getLogin()), imageLoader);
		
		TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
		TextView classificacao = (TextView) convertView.findViewById(R.id.classificacao);
		TextView aceitacao = (TextView) convertView.findViewById(R.id.aceitacao);
		TextView data = (TextView) convertView.findViewById(R.id.dataCriacao);
		
		
		
		PrestarServico p = lista.get(position);	
		descricao.setText(p.getDescricao());
		classificacao.setText("Text fixo  no código");
		aceitacao.setText("Texto fixo no código");
		data.setText(ServiceBoxMobileUtil.dateToString(p.getData(), DateFormat.FULL));
		
		

		return convertView;
	}

}
