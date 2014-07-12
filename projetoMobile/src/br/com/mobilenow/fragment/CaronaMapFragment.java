package br.com.mobilenow.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.R;
import br.com.mobilenow.componente.SherlockMapFragment;

import com.actionbarsherlock.view.MenuItem;

public class CaronaMapFragment extends SherlockMapFragment{
	
	 public static CaronaMapFragment newInstance(int position,String title) {
	    	CaronaMapFragment fragment = new CaronaMapFragment();
	        Bundle bundle = new Bundle();
	        bundle.putInt("position", position);
	        bundle.putString("title", title);
	        fragment.setArguments(bundle);
	        return fragment;
	    }
	    
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//handler.postDelayed(runner, random.nextInt(2000));
			
			View view = super.onCreateView(inflater, container, savedInstanceState);
			//googleMap = getMap();
			//googleMap.setMyLocationEnabled(true);
			
			//ViewUtils.initializeMargin(getActivity(), view);

			return view;
		}
		
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			  if (item.getItemId() == R.id.action_bar_cadastro) {
				  startActivityForResult(new Intent(getActivity(), UsuarioActivity.class), UsuarioActivity.RESULT_CODE);
			  } 
			  
		      return true;
		}	

}
