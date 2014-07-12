package br.com.mobilenow.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import br.com.mobilenow.R;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class UsuarioActivity extends FragmentActivity{
	
	protected static final int RESULT_CODE = 123;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.usuario_fragment);
		
		EditText edLogin = (EditText) findViewById(R.id.login);
		EditText edNome = (EditText) findViewById(R.id.nome);
		EditText edSobrenome = (EditText) findViewById(R.id.sobrenome);
		EditText edTelefone = (EditText) findViewById(R.id.telefone);
		EditText edSenha = (EditText) findViewById(R.id.senha);
		EditText edConfSenha = (EditText) findViewById(R.id.confirma);
		String sexo;
		RadioGroup rgSexo = (RadioGroup) findViewById(R.id.sexo);
		if(rgSexo.getCheckedRadioButtonId() == R.id.masculino){
			sexo = "M";
		}else{
			sexo = "F";
		}
		
		Button btRegistrar = (Button) findViewById(R.id.registrar);
		btRegistrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	

}
