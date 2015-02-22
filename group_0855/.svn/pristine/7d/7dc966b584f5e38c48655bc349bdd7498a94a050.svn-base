package com.triageapplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.model.NoRecordedDataException;
import com.model.Patient;
import com.model.PatientVisit;
import com.model.VitalSigns;
import com.triageapplication.R.id;

public class EditVisitRecord extends Activity {
	
	private Patient patient;
	private int index = -1;
	private PatientVisit pv = null;
	private int vitalsIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_visit_record);
		Intent intent = this.getIntent();
		patient = (Patient) intent.getExtras().getSerializable("patient");
		index = intent.getExtras().getInt("record_index");
		pv = patient.getAllPatientVisits().get(index);
		if (intent.getExtras().containsKey("vitals_index"))
			vitalsIndex = intent.getExtras().getInt("vitals_index");
		refreshSpinner();
	}
	
	private void refreshSpinner() {
		Spinner spinner = (Spinner) findViewById(id.vital_sign);
		List<String> vitals = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (VitalSigns vs : pv.getVitals()) {
			vitals.add(sdf.format(vs.getTime().getTime()));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vitals);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		TextView tv = null;
		try {
			tv = (TextView) findViewById(id.temperature_text);
			tv.setText("Temperature: " + String.valueOf(pv.getVitals().get(vitalsIndex).getTemperature()));
			tv = (TextView) findViewById(id.diastolic_blood_pressure_text);
			tv.setText("Diastolic blood pressure: " + String.valueOf(pv.getVitals().get(vitalsIndex).getBloodPressureDiastolic()));
			tv = (TextView) findViewById(id.systolic_blood_pressure_text);
			tv.setText("Systolic blood pressure: " + String.valueOf(pv.getVitals().get(vitalsIndex).getBloodPressureSystolic()));
			tv = (TextView) findViewById(id.heart_rate_text);
			tv.setText("Heart rate: " + String.valueOf(pv.getVitals().get(vitalsIndex).getHeartRate()));
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			tv = (TextView) findViewById(id.diastolic_blood_pressure_text);
			tv.setText("No record of visit was found.");
			tv = (TextView) findViewById(id.temperature_text);
			tv.setText("");
			tv = (TextView) findViewById(id.systolic_blood_pressure_text);
			tv.setText("");
			tv = (TextView) findViewById(id.heart_rate_text);
			tv.setText("");
			Button b = (Button) findViewById(id.view_visit_record);
			b.setEnabled(false);
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_visit_record, menu);
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
	
	public void update(View view) {
		Spinner spinner = (Spinner) findViewById(id.vital_sign);
		vitalsIndex = spinner.getSelectedItemPosition();
		refreshSpinner();
	}
	
	public void back (View view) {
		Intent intent = new Intent(this, VisitRecords.class);
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
	
	public void createVitals (View view) {
		Intent intent = new Intent(this, NewVitals.class);
		intent.putExtra("patient", patient);
		intent.putExtra("index", index);
		intent.putExtra("isEdit", false);
		startActivity(intent);
	}
	
	public void editRecentVitals (View view) {
		Intent intent = new Intent(this, NewVitals.class);
		intent.putExtra("patient", patient);
		intent.putExtra("index", index);
		intent.putExtra("isEdit", true);
		startActivity(intent);
	}
}
