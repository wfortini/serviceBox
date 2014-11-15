package br.com.mobilenow;

import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.TimePickerDialog;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.TimePicker;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import br.com.servicebox.common.activity.CommonActivity;
import br.com.servicebox.common.fragment.CommonFragment;
import br.com.servicebox.common.util.GuiUtils;

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
 
    public static class PlanejamentoUiFragment extends CommonFragment implements OnClickListener{
    	
    	
    	private Switch segunda;
    	private Switch terca;
    	private Switch quarta;
    	private Switch quinta;
    	private Switch sexta;
    	private Switch sabado;
    	private Switch domingo;
    	
    	private RadioButton rbFaixaHorario;
    	private RadioButton rbHoraFixa;
    	
    	private android.widget.TextView tvHoraFixa;
    	private TextView tvFaixaHorarioEntre;
    	private TextView tvFaixaHorarioE;
    	
    	private TextView labelHoraFixa;
    	private TextView labelFaixaEntre;
    	private TextView labelFaixaE;
    	
    	OnCheckedChangeListener listenerRBHoraFixa = null ; 
    	OnCheckedChangeListener listenerRBFaixaHorario = null ;     	
    	private int mYear, mMonth, mDay, mHour, mMinute;
    	private String retornoTime;
    	
    	
    	private Button btConfirma;
    	
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
    		 
    		 segunda = (Switch) v.findViewById(R.id.segunda_switch);
    		 terca = (Switch) v.findViewById(R.id.terca_switch);
    		 quarta = (Switch) v.findViewById(R.id.quarta_switch);
    		 quinta = (Switch) v.findViewById(R.id.quinta_switch);
    		 sexta = (Switch) v.findViewById(R.id.sexta_switch);
    		 sabado = (Switch) v.findViewById(R.id.sabado_switch);
    		 domingo = (Switch) v.findViewById(R.id.domingo_switch);
    		 
    		 rbFaixaHorario = (RadioButton) v.findViewById(R.id.rb_faixaHorario);
    		 rbHoraFixa = (RadioButton) v.findViewById(R.id.rb_horaFixa);
    		 
    		 tvFaixaHorarioE = (TextView) v.findViewById(R.id.tvFaixaHoraE);
    		 tvFaixaHorarioEntre = (TextView) v.findViewById(R.id.tv_faixaHorarioEntre);
    		 tvHoraFixa = (android.widget.TextView) v.findViewById(R.id.tv_horaFixa);
    		 labelFaixaEntre = (TextView) v.findViewById(R.id.label_entre);
    		 labelFaixaE = (TextView) v.findViewById(R.id.labelE);
    		 
    		 btConfirma = (Button) v.findViewById(R.id.confirmar_planejamento);
    		 btConfirma.setOnClickListener(this);
    		 
    		 rbFaixaHorario.setOnCheckedChangeListener(listenerRBFaixaHorario);
    		 rbHoraFixa.setOnCheckedChangeListener(listenerRBHoraFixa);
    		 
    		 tvFaixaHorarioE.setOnClickListener(this);
    		 tvFaixaHorarioEntre.setOnClickListener(this);
    		 tvHoraFixa.setOnClickListener(this);   		
    		 
    		 listenerRBFaixaHorario = new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					rbFaixaHorario.setChecked(true);
					rbHoraFixa.setChecked(false);
					tvHoraFixa.setVisibility(View.INVISIBLE);	
				    tvFaixaHorarioE.setVisibility(View.VISIBLE);
		    		tvFaixaHorarioEntre.setVisibility(View.VISIBLE);
		    	    labelFaixaEntre.setVisibility(View.VISIBLE);
		    		labelFaixaE.setVisibility(View.VISIBLE);
					
				}
			};
			
            listenerRBHoraFixa = new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					rbFaixaHorario.setChecked(false);
					rbHoraFixa.setChecked(true);
					tvHoraFixa.setVisibility(View.VISIBLE);	
				    tvFaixaHorarioE.setVisibility(View.INVISIBLE);
		    		tvFaixaHorarioEntre.setVisibility(View.INVISIBLE);
		    	    labelFaixaEntre.setVisibility(View.INVISIBLE);
		    		labelFaixaE.setVisibility(View.INVISIBLE);
					
				}
			};
    		 
    	 }
    	 
    	@Override 
    	public void onClick(View v) {
				
				switch (v.getId()) {
				
				case R.id.tv_horaFixa:
					tvHoraFixa.setText(timeDialog());
					break;
					
				case R.id.tv_faixaHorarioEntre:
					tvFaixaHorarioEntre.setText(timeDialog());
					break;
					
				case R.id.tvFaixaHoraE:
					tvFaixaHorarioE.setText(timeDialog());
					break;	
					
				case R.id.confirmar_planejamento:
					confirmar();
					break;
				}
				
		}
    	 
    	private String timeDialog(){
    		 
    		 final Calendar c = Calendar.getInstance();
             mHour = c.get(Calendar.HOUR_OF_DAY);
             mMinute = c.get(Calendar.MINUTE);            
             
             TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                     new TimePickerDialog.OnTimeSetListener() {
  
                         @Override
                         public void onTimeSet(TimePicker view, int hourOfDay,
                                 int minute) {                             
                              retornoTime =  hourOfDay + ":" + minute;
                         }
                     }, mHour, mMinute, false);
             tpd.show();
             
             return retornoTime;
    	 }
    	 
    	 
    	 /**
          * Confirma que prestar o serviço
          *         
          */
         public void confirmar() {

        	 GuiUtils.alert("OK");
         	
         }
	 
   } // fim classe interna Fragment
}
