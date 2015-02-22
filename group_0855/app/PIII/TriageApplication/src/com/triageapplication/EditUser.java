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
import android.widget.TextView;
import android.widget.Toast;

import com.model.User;

public class EditUser extends Activity {
	
	private ArrayList<User> userList;
	private String username;
	private int userPos;
	private SQLiteDatabase userDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		userDB = openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
		Cursor c = userDB.rawQuery("SELECT username, credential FROM users ORDER BY username", null);
		Intent intent = this.getIntent();
		userList = (ArrayList<User>) intent.getExtras().getSerializable("userList");
		userPos = intent.getExtras().getInt("selectedUser");
		c.moveToPosition(userPos);
		TextView tv = (TextView) findViewById(R.id.userID);
		String credential = "";
		username = c.getString(0);
		if (c.getString(1).equals("nurse")) {
			credential = "Nurse";
		} else if (c.getString(1).equals("physician")) {
			credential = "Physician";
		} else if (c.getString(1).equals("admin")) {
			credential = "Admin";
		}
		tv.setText(username + "\n" + credential);
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_user, menu);
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
	
	public void back(View view) {
		Intent intent = new Intent(this, AdminLogin.class);
		
		intent.putExtra("userList", userList);
		startActivity(intent);
	}
	
	public void resetPassword(View view) {
		new AlertDialog.Builder(this)
		.setTitle("Reset password")
		.setMessage("Do you really want to reset this user's password?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int whichButton) {
		    	userDB.execSQL("UPDATE users SET password='triage' WHERE username='" + username + "'");
		    	userDB.close();
//		    	userList.get(userPos).setPassword("triage");
		        Toast.makeText(EditUser.this, "Password set to \"triage\"", Toast.LENGTH_SHORT).show();
		    }})
		 .setNegativeButton(android.R.string.no, null).show();
	}
	
	public void removeUser(View view) {
		new AlertDialog.Builder(this)
		.setTitle("Reset password")
		.setMessage("Do you really want delete this user?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int whichButton) {
		    	userDB.execSQL("DELETE FROM users WHERE username='" + username + "'");
		    	removeBack();    
		    }})
		 .setNegativeButton(android.R.string.no, null).show();
	}
	
	private void removeBack() {
		Intent intent = new Intent(this, AdminLogin.class);
		intent.putExtra("deleted", true);
		startActivity(intent);
	}
}
