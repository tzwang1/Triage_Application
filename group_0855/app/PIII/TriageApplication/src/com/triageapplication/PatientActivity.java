package com.triageapplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.model.Patient;
import com.triageapplication.R.id;

public class PatientActivity extends Activity {

	private Patient patient;
	private String userType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_patient);
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
		System.out.println("patientActivity userType: " + userType);
		
		TextView tv = (TextView) findViewById(id.patient_information);
		patient = (Patient) intent.getExtras().getSerializable("patient");
		// Displays the patient's information such as name, health card number,
		// and DoB
		tv.setText(patient.getName());
		tv = (TextView) findViewById(id.health_card_number);
		tv.setText(patient.getHealthCardNumber());
		tv = (TextView) findViewById(id.dob);
		Calendar dob = patient.getDob();
		// Formats the Calendar object from patient to a readable string
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		tv.setText(sdf.format(dob.getTime()));
		try {
			// This prevents the patient object from being overwritten by
			// whatever is in the log file if some changes have already been
			// made. That is, if this activity was reached by an activity other
			// than the MainActivity.
			if (!intent.getExtras().containsKey("fromHome")) {
				load();
			}
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
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
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}

	public void viewVisitRecords(View view) {
		Intent intent = new Intent(this, VisitRecords.class);
		intent.putExtra("patient", patient);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}

	// The saved patient objects are outputted to some location in the internal
	// memory of the device. The whereabouts of this location are of no
	// importance as FileOutputStream and FileInputStream seem to work with the
	// same default location.

	public void save(View view) throws IOException {
		// The save function works by replacing the older .log file of a
		// respective patient with a more updated version.
		FileOutputStream fos = this.openFileOutput(
				patient.getHealthCardNumber() + ".log", Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(patient);
		os.close();
		Toast.makeText(PatientActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
//		TextView tv = (TextView) findViewById(id.save_status);
		//tv.setText("Successfully saved!");
	}

	private void load() throws OptionalDataException, ClassNotFoundException,
			IOException {
		FileInputStream fis = this.openFileInput(patient.getHealthCardNumber()
				+ ".log");
		ObjectInputStream is = new ObjectInputStream(fis);
		Patient pat = (Patient) is.readObject();
		is.close();
		patient.setVisits(pat.getAllPatientVisits());
	}
}
