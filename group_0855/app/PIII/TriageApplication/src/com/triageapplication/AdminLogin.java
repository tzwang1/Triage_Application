package com.triageapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.model.User;
import com.triageapplication.R.id;

public class AdminLogin extends Activity {

	private ArrayList<User> userList;
	private ArrayList<String> userString = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> functions;
	private SQLiteDatabase userDB;
	private View tempView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_login);
		Intent intent = this.getIntent();
		if (intent.hasExtra("deleted")) {
			Toast.makeText(AdminLogin.this, "User has been removed", Toast.LENGTH_SHORT).show();
		}
		userDB = openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		Cursor c = userDB.rawQuery("SELECT username, credential FROM users ORDER BY username", null);
		while (c.moveToNext()) {
			String credential = null;
			if (c.getString(1).equals("nurse")) {
				credential = "Nurse";
			} else if (c.getString(1).equals("physician")) {
				credential = "Physician";
			} else if (c.getString(1).equals("admin")) {
				credential = "Admin";
			}
			userString.add(c.getString(0) + "\n" + credential);
		}

	}
	
	@Override
	public void onBackPressed() {
	}
	
	private void rebuildUserList() {
		Spinner spinner = (Spinner) findViewById(R.id.function);
		ArrayList<String> functions = new ArrayList<String>();
		functions.add("Create new user");
		functions.add("Reset database");
		functions.add("Logout");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, functions);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, userString);
		ListView lv = (ListView) findViewById(id.user_list);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parentView, View childView,
					int position, long id) {
				editUser(position);
			}

			public void onNothingSelected(AdapterView parentView) {
			}
		});
		lv.setAdapter(adapter);

		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_login, menu);
		rebuildUserList();

		
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

	public void newUser() {
		Intent intent = new Intent(this, NewUser.class);
		intent.putExtra("userList", userList);
		userDB.close();
		startActivity(intent);
	}

	public void editUser(int userPos) {
		Intent intent = new Intent(this, EditUser.class);
		intent.putExtra("userList", userList);
		intent.putExtra("selectedUser", userPos);
		userDB.close();
		startActivity(intent);
	}
	
	public void logout(boolean reset) {
		Intent intent = new Intent(this, UserLogin.class);
		userDB.close();
		if (reset) {
			intent.putExtra("reset", true);
		}
		startActivity(intent);
	}
	
	public void resetDatabase() {
		new AlertDialog.Builder(this)
		.setTitle("Reset password")
		.setMessage("Do you really want to reset the user database")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int whichButton) {
		    	userDB.execSQL("DROP TABLE users");
		    	userDB.close();
		    	logout(true);
		    }})
		 .setNegativeButton(android.R.string.no, null).show();
		
	}
	public void execute(View view) {
		Class foo = AdminLogin.class;
		Spinner spinner = (Spinner) findViewById(R.id.function);
		switch (spinner.getSelectedItemPosition()) {
			case 0: newUser();
					break;
			case 1: resetDatabase();
					break;
			case 2: logout(false);
					break;
		}
	}
}
