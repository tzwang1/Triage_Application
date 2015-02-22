package com.triageapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewUser extends Activity {

	private SQLiteDatabase userDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		Spinner spinner = (Spinner) findViewById(R.id.user_types);
		ArrayList<String> user_types = new ArrayList<String>();
		userDB = openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		user_types.add("Nurse");
		user_types.add("Physician");
		user_types.add("Admin");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, user_types);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
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

	public void create(View view) {
		Intent intent = new Intent(this, AdminLogin.class);
		TextView tv = (TextView) findViewById(R.id.new_username);
		String username = tv.getText().toString();
		tv = (TextView) findViewById(R.id.new_password);
		String password = tv.getText().toString();
		tv = (TextView) findViewById(R.id.confirm_new_password);
		String confirmPassword = tv.getText().toString();
		Spinner spinner = (Spinner) findViewById(R.id.user_types);
		String credential = null;
		switch (spinner.getSelectedItemPosition()) {
		case 0:
			credential = "nurse";
			break;
		case 1:
			credential = "physician";
			break;
		case 2:
			credential = "admin";
			break;
		}
		Cursor c = userDB.rawQuery("SELECT * FROM users WHERE username='"
				+ username + "'", null);
		if (c.getCount() != 0) {
			Toast.makeText(NewUser.this, "Username already exists.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!password.equals(confirmPassword)) {
			Toast.makeText(NewUser.this, "Passwords do not match.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		userDB.execSQL("INSERT INTO users(username, password, credential) VALUES ('"
				+ username + "', '" + password + "', '" + credential + "')");
		startActivity(intent);
	}

	public void back(View view) {
		Intent intent = new Intent(this, AdminLogin.class);
		startActivity(intent);
	}
}
