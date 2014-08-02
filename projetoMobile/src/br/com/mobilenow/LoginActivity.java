package br.com.mobilenow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import br.com.servicebox.common.util.CommonUtils;
import br.com.servicebox.common.util.GuiUtils;

public class LoginActivity extends Activity {
	
	 private static final String TAG = LoginActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acesso_login);
	}

	public void loginButtonAction(View view) {
        CommonUtils.debug(TAG, "Login do usuario");
        //TrackerUtils.trackButtonClickEvent("login_button", AccountLogin.this);

        EditText editText = (EditText) findViewById(R.id.edit_email);
        String email = editText.getText().toString();

        editText = (EditText) findViewById(R.id.edit_password);
        String password = editText.getText().toString();

        if (!GuiUtils.validateBasicTextData(new String[] {
                email, password
        }, new int[] {
                R.string.campo_email, R.string.campo_password
        })) {
            return;
        }
        
        Intent intent = new Intent(this, TabbedActivity.class);
        startActivity(intent);
        
         // clean up login information
        //Preferences.logout(this);

        //getLoginFragment().doLogin(email, password);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
