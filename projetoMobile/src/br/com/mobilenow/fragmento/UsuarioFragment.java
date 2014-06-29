package br.com.mobilenow.fragmento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.componente.SherlockMapFragment;

public class UsuarioFragment extends SherlockMapFragment{
	
	public static UsuarioFragment newInstance(int position,String title) {
		UsuarioFragment fragment = new UsuarioFragment();
		Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		

		View view = super.onCreateView(inflater, container, savedInstanceState);
		//View view = inflater.inflate(R.id.fragmentContainer, container,false);

		

		return view;
	}
	


}
