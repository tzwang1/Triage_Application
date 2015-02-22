package com.triageapplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.model.Patient;
import com.model.PatientVisit;
import com.triageapplication.R.id;

public class PatientActivity extends Activity {
	
	private Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_patient);
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		TextView tv = (TextView) findViewById(id.patient_information);
		//tv.setText(intent.getStringExtra("foo"));
//		tv.setText(String.valueOf(intent.hasExtra("patient")));
		patient = (Patient) intent.getExtras().getSerializable("patient");
		tv.setText(patient.getName());
		tv = (TextView) findViewById(id.health_card_number);
		tv.setText(patient.getHealthCardNumber());
		tv = (TextView) findViewById(id.dob);
		Calendar dob = patient.getDob();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		tv.setText(sdf.format(dob.getTime()));
		try {
			load();
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
		startActivity(intent);
	}
	
	public void viewVisitRecords(View view) {
		Intent intent = new Intent(this, VisitRecords.class);
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
	
	public void save(View view) throws IOException {
		FileOutputStream fos = this.openFileOutput(patient.getHealthCardNumber() + ".log", Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(patient);
		os.close();
	}
	
	private void load() throws OptionalDataException, ClassNotFoundException, IOException {
		FileInputStream fis = this.openFileInput(patient.getHealthCardNumber() + ".log");
		ObjectInputStream is = new ObjectInputStream(fis);
		Patient pat = (Patient) is.readObject();
		is.close();
		patient.setVisits(pat.getAllPatientVisits());
	}
}
