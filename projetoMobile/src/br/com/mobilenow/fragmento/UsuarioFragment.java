package br.com.mobilenow.fragmento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.R;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class UsuarioFragment extends SherlockFragment{
	
	private static final String ARG_POSITION = "position";
	private Menu menu;
	private int position;
	
	public static UsuarioFragment newInstance(int position,String title) {
		UsuarioFragment fragment = new UsuarioFragment();
		Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.usuario_fragment, container,false);		

		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		this.menu = menu;		
		inflater.inflate(R.menu.main, menu);		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		  		  
	      return true;
	}	

}
