package br.com.mobilenow;

import org.holoeverywhere.LayoutInflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import br.com.servicebox.common.activity.CommonActivity;
import br.com.servicebox.common.fragment.CommonFragment;

public class PlanejamentoActivity extends CommonActivity {

	public static final String TAG = PlanejamentoActivity.class.getSimpleName();
    public static final String PLANEJAMENTO = "PLANEJAMENTO"; 
    public static final int RESULT_CODE = 123;   
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    if (savedInstanceState == null)
	    {
	        getSupportFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new PlanejamentoUiFragment())
	                .commit();
	    }
	}
 
    public static class PlanejamentoUiFragment extends CommonFragment{
    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_planejamento, container, false);
            return v;
        }
    	
    	 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
             init(view);
         }
    	 
    	 private void init(View v){
    		 
    	 }
	 
   }
}
