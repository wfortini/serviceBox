package br.com.mobilenow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class InfoActivity extends Activity {
	
	public static final String TAG = InfoActivity.class.getSimpleName();
	public static final String INFO_SERVICO = "INFO_SERVICO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	}
}
