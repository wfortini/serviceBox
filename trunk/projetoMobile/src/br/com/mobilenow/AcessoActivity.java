package br.com.mobilenow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import br.com.mobilenow.fragment.UsuarioActivity;
import br.com.servicebox.android.common.activity.CommonActivity;
import br.com.servicebox.android.common.util.CommonUtils;

public class AcessoActivity extends CommonActivity {
	
	private static final String TAG = AcessoActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso);
	}
	
	public void contaLoginButtonAction(View view) {
        CommonUtils.debug(TAG, "Iniciando acesso ao usuario ao sistema");
        //TrackerUtils.trackButtonClickEvent("account_login_button", AccountActivity.this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
	
	public void cadastroButtonAction(View view){
		 CommonUtils.debug(TAG, "Iniciando registro do usuario");
        //TrackerUtils.trackButtonClickEvent("account_login_button", AccountActivity.this);
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
	}
}
