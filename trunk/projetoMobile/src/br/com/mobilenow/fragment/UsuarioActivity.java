package br.com.mobilenow.fragment;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import br.com.mobilenow.R;
import br.com.mobilenow.domain.Usuario;

public class UsuarioActivity extends FragmentActivity{
	
	protected static final int RESULT_CODE = 123;
	private EditText edLogin;
	private EditText edNome;
	private EditText edSobrenome;
	private EditText edTelefone;
	private EditText edSenha;
	private EditText edConfSenha;
	private String sexo;
	private RadioGroup rgSexo;
	private Usuario usuario;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.usuario_fragment);
		
		edLogin = (EditText) findViewById(R.id.login);
		edNome = (EditText) findViewById(R.id.nome);
		edSobrenome = (EditText) findViewById(R.id.sobrenome);
		edTelefone = (EditText) findViewById(R.id.telefone);
		edSenha = (EditText) findViewById(R.id.senha);
		edConfSenha = (EditText) findViewById(R.id.confirma);
		rgSexo = (RadioGroup) findViewById(R.id.sexo);
		if(rgSexo.getCheckedRadioButtonId() == R.id.masculino){
			sexo = "M";
		}else{
			sexo = "F";
		}
		
		Button btRegistrar = (Button) findViewById(R.id.registrar);
		btRegistrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(edLogin.getText() == null || edLogin.getText().toString().equals("")){
					edLogin.setError("Login inválido.");
					return;
				}
				if(edNome.getText() == null || edNome.getText().toString().equals("")){
					edNome.setError("Nome inválido");
					return;
				}
				
				if(edSobrenome.getText() == null || edSobrenome.getText().toString().equals("")){
					edSobrenome.setError("Sobrenome inválido.");
				}
				
				if(edTelefone.getText() == null || edTelefone.getText().toString().equals("")){
					edTelefone.setError("Telefone inválido.");
					return;
				}
				if(edSenha.getText() == null || edSenha.getText().toString().equals("")){
					edSenha.setError("Senha inválida.");
					return;
				}
				if(edSenha.getTextSize() < 7){
					edSenha.setError("Senha deve ter no minimo 7 caracteres.");
					return;
				}
				
				if(edConfSenha.getText() == null || edConfSenha.getText().toString().equals("") ||
						      !edSenha.getText().toString().equals(edConfSenha.getText().toString())){
					edConfSenha.setError("Confime sua senha");
					return;
				}
				
				usuario = new Usuario(edLogin.getText().toString(), edSenha.getText().toString(), 
						           edNome.getText().toString(), edSobrenome.getText().toString(), sexo, null);
				
				
				new RequisicaoTask().execute();			
				
				
				
			}
		});
		
	}
	
	
	private class RequisicaoTask extends AsyncTask<Void, Void, Usuario>{

		@Override
		protected Usuario doInBackground(Void... params) {			
			
			try {
				final String url = "http://localhost:8080/projetoWeb/registrarUsuario.json";
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter()); 
				return restTemplate.postForObject(url, usuario, Usuario.class);	
			} catch (Exception e) {
				Log.e("UsuarioActivity", e.getMessage());
			}
			
			return null;
		}
		
		@Override
        protected void onPreExecute() {
			
		}
		
		protected void onPostExecute(Void unused) {
            
            //pDialog.dismiss();
        }
		
	}
	
	

}
