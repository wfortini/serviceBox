package br.com.mobilenow.fragment;

import java.util.Random;

import org.holoeverywhere.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mobilenow.ItinerarioActivity;
import br.com.mobilenow.PrestarServicoActivity;
import br.com.mobilenow.R;
import br.com.mobilenow.componente.SherlockMapFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class CaronaMapFragment extends SherlockMapFragment{
	
	 private static final int REQUEST_PRESTA_SERVICO = 1;
	
	private Handler handler = new Handler();
	private Random random = new Random();
	private Runnable runner = new Runnable() {
        @Override
        public void run() {
            setHasOptionsMenu(true);
        }
    };
	
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
			handler.postDelayed(runner, random.nextInt(2000));
			
			View view = super.onCreateView(inflater, container, savedInstanceState);
			//googleMap = getMap();
			//googleMap.setMyLocationEnabled(true);
			
			//ViewUtils.initializeMargin(getActivity(), view);

			return view;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			menu.clear();
			inflater.inflate(R.menu.carona_menu, menu);
			
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			  if (item.getItemId() == R.id.item_menu_oferecr_carona) {
				  startActivityForResult(new Intent(getActivity(), UsuarioActivity.class), UsuarioActivity.RESULT_CODE);
			  } else if (item.getItemId() == R.id.item_menu_pegar_carona) {
				  startActivityForResult(new Intent(getActivity(), ItinerarioActivity.class), ItinerarioActivity.RESULT_CODE);
			  } else if (item.getItemId() == R.id.item_menu_motorista_rodada_carona) {
				  startActivityForResult(new Intent(getActivity(), UsuarioActivity.class), UsuarioActivity.RESULT_CODE);
			  } else  if (item.getItemId() == R.id.item_menu_prestar_carona) {
				  Intent i = new Intent(getActivity(), PrestarServicoActivity.class);
                  i.putExtra(PrestarServicoActivity.PRESTAR_SERVICO, "carona");
                  startActivityForResult(i, REQUEST_PRESTA_SERVICO);				 
			  } 
			  
		      return true;
		}
		
		private void abrirDialog(Integer operacao){
			
			final CharSequence[] items = {
	                getString(R.string.upload_opcao_camera),
	                getString(R.string.upload_opcao_galeria)
	        };
	
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
	                R.style.Theme_ServiceBox_Dialog_Light);
	        builder.setTitle(R.string.titulo_dialog);
	        builder.setItems(items, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {
	                if (handler == null)
	                {
	                    return;
	                }
	                switch (item) {
	                    case 0:
	                        //TrackerUtils.trackContextMenuClickEvent("menu_camera",
	                        //        SelectImageDialogFragment.this);
	                        
	                        return;
	                    case 1:
	                       // TrackerUtils.trackContextMenuClickEvent("menu_gallery",
	                       //         SelectImageDialogFragment.this);
	                        
	                        return;
	                }
	            }
	        });
	        builder.create();
	    }
		

}
