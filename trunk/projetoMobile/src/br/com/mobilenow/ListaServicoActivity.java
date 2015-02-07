package br.com.mobilenow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.fragment.CommonFragment;

public class ListaServicoActivity extends CommonActivity {
	
	public static final String TAG = ListaServicoActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new ListaServicoFragment())
	                .commit();
	    }
	}
	
	
	 public static class ListaServicoFragment extends CommonFragment implements
     OnItemClickListener {
		 
		 @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);	            
        }

		@Override
		public View onCreateView(org.holoeverywhere.LayoutInflater inflater,
				ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_lista_servico, container, false);
            return v;
		}
		 
		 @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            
        }
 
		 @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
           
            Activity activity = getSupportActivity();
           
        } 
		 
	 }

	
}
