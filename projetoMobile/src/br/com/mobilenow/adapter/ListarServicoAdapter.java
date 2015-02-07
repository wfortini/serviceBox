package br.com.mobilenow.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.mobilenow.R;
import br.com.servicebox.common.domain.PrestarServico;

public class ListarServicoAdapter extends BaseAdapter{
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<PrestarServico> lista = new ArrayList<PrestarServico>();

	public ListarServicoAdapter(Activity activity, List<PrestarServico> list) {
		this.activity = activity;
		this.lista = list;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int location) {
		return lista.get(location);
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
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView rating = (TextView) convertView.findViewById(R.id.rating);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

		// getting movie data for the row
		PrestarServico p = lista.get(position);	
		
		

		return convertView;
	}

}
