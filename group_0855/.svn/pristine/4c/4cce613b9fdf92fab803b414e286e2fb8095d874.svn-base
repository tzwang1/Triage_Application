package com.triageapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.model.User;

public class UserLogin extends Activity {

	private ArrayList<User> users;
	private SQLiteDatabase userDB = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		Intent intent = this.getIntent();
		if ((intent.hasExtra("reset"))
				&& (intent.getExtras().containsKey("reset"))) {
			Toast.makeText(UserLogin.this, "User database has been reset.",
					Toast.LENGTH_SHORT).show();
		}
		TextView tv = (TextView) findViewById(R.id.loginResult);
		tv.setText("");

		if (!intent.hasExtra("fromAdmin")) {
			users = new ArrayList<User>();
			AssetManager am = this.getAssets();
			BufferedReader is = null;
			try {
				is = new BufferedReader(new InputStreamReader(
						am.open("passwords.txt")));
				generateUsers(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	@Override
	public void onBackPressed() {
	}

	private void generateUsers(BufferedReader is) {
		userDB = openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		if (userDB.rawQuery("SELECT name from sqlite_master WHERE type='table' AND name='users'", null).getCount() == 0) {
			userDB.execSQL("CREATE TABLE IF NOT EXISTS users(username VARCHAR NOT NULL,password VARCHAR NOT NULL,credential VARCHAR NOT NULL,PRIMARY KEY(username));");

			String lineRead;
			try {
				lineRead = is.readLine();
				while (lineRead != null) {
					String[] splitLine = lineRead.split(",");
					Cursor c = userDB.rawQuery(
							"SELECT * FROM users WHERE username='"
									+ splitLine[0] + "'", null);
					if (c.getCount() == 0) {

						userDB.execSQL("INSERT INTO users(username, password, credential) VALUES ('"
								+ splitLine[0]
								+ "', '"
								+ splitLine[1]
								+ "', '"
								+ splitLine[2] + "');");
					}
					lineRead = is.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
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

	public void login(View view) {
		boolean found = false;
		Intent intent = null;
		EditText et = (EditText) findViewById(R.id.userId);
		String username = et.getText().toString();
		et = (EditText) findViewById(R.id.password);
		String password = et.getText().toString();
		Cursor c = userDB.rawQuery("SELECT * FROM users WHERE username='"
				+ username + "' AND password='" + password + "'", null);
		if (c.getCount() == 0) {
			Toast.makeText(UserLogin.this, "Invalid user ID/password.", Toast.LENGTH_SHORT).show();
			return;
		}
		TextView tv = (TextView) findViewById(R.id.loginResult);
		c.moveToFirst();
		if (c.getString(2).equals("nurse")) {
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("userType", "nurse");
			// Change this if Doctor should open a different activity than
			// MainActivity
		} else if (c.getString(2).equals("physician")) {
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("userType", "physician");
		} else if (c.getString(2).equals("admin")) {
			intent = new Intent(this, AdminLogin.class);
		}
		userDB.close();
		startActivity(intent);
	}
}
