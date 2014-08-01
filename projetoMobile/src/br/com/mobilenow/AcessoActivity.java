package br.com.mobilenow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AcessoActivity extends Activity {
	
	private static final String TAG = AcessoActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso);
	}
	
	public void accountLoginButtonAction(View view) {
        //CommonUtils.debug(TAG, "Start account login button action");
        //TrackerUtils.trackButtonClickEvent("account_login_button", AccountActivity.this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
