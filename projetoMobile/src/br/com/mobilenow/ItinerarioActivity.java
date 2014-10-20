package br.com.mobilenow;


import org.holoeverywhere.LayoutInflater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.servicebox.common.activity.CommonActivity;
import br.com.servicebox.common.fragment.CommonFragment;

public class ItinerarioActivity extends CommonActivity {

	public static final String TAG = ItinerarioActivity.class.getSimpleName();
    public static final String ITINERARIO = "ITINERARIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new ItinerarioUiFragment())
                    .commit();
        }
    }
    
    public static class ItinerarioUiFragment extends CommonFragment{
    	
    	@Override
        public View onCreateView(LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View v = inflater.inflate(R.layout.activity_itinerario, container, false);
            return v;
        }
    	
    	 @Override
         public void onViewCreated(View view, Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);
             init(view);
         }
    	 
    	 void init(View v)
         {
             // (getActivity().getIntent().getStringExtra(ITINERARIO));
             Button uploadBtn = (Button) v.findViewById(R.id.button_cadastro);
             uploadBtn.setOnClickListener(new OnClickListener() {

                 @Override
                 public void onClick(View v) {                     
                     finishedClicked(v);
                 }
             });
         }
    	 
    	 public void finishedClicked(View v) {

             Intent data = new Intent();             

             data.putExtra(ITINERARIO, "");
             getActivity().setResult(RESULT_OK, data);
             getActivity().finish();

         }
    	 
    	
    }
}
