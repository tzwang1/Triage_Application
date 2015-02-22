package com.triageapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.model.Patient;
import com.model.PatientVisit;
import com.triageapplication.R.id;

public class VisitRecords extends Activity {
	
	private Patient patient;
	private ArrayList<String> dates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit_records);
		Intent intent = this.getIntent();
		dates = new ArrayList<String>();
		patient = (Patient) intent.getExtras().getSerializable("patient");
		TextView tv = (TextView) findViewById(id.patient_arrival_date_title);
		String text = "The visit records for "+ patient.getName() + " are as follows:";
		tv.setText(text);
		Spinner spinner = (Spinner) findViewById(id.visit_records);
		List<String> visitRecord = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (PatientVisit pv : patient.getAllPatientVisits()) {
			visitRecord.add(sdf.format(pv.getArrivalDate().getTime()));
			dates.add(sdf.format(pv.getArrivalDate().getTime()));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visitRecord);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visit_records, menu);
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
	
	public void createVisitRecord(View view) {
		Intent intent = new Intent(this, CreateVisitRecord.class);
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
	
	public void back(View view) {
		Intent intent = new Intent(this, PatientActivity.class);
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
	
	public void viewVisitRecords(View view) {
		Intent intent = new Intent(this, EditVisitRecord.class);
		Spinner spinner = (Spinner) findViewById(id.visit_records);
		int index = dates.indexOf(spinner.getSelectedItem().toString());
		intent.putExtra("record_index", index);
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
}
