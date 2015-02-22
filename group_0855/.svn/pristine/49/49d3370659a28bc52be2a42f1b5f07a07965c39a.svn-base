package com.triageapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.model.NoRecordedDataException;
import com.model.Patient;
import com.model.VitalSigns;
import com.triageapplication.R.id;

public class NewVitals extends Activity {
	
	private Patient patient;
	private int index = -1;
	private boolean isEdit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_vitals);
		Intent intent = this.getIntent();
		isEdit = intent.getExtras().getBoolean("isEdit");
		index = intent.getExtras().getInt("index");
		patient = (Patient) intent.getExtras().getSerializable("patient");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_vitals, menu);
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
	
	public void submit(View view) {
		Intent intent = new Intent(this, EditVisitRecord.class);
		EditText sbp = (EditText) findViewById(id.systolic_blood_pressure);
		EditText dbp = (EditText) findViewById(id.diastolic_blood_pressure);
		EditText hr = (EditText) findViewById(id.heart_rate);
		EditText temp = (EditText) findViewById(id.temperature);
		if ((sbp.getText().toString().equals("")) || (dbp.getText().toString().equals("")) 
				|| (hr.getText().toString().equals("")) || (temp.getText().toString().equals(""))) {
			TextView tv = (TextView) findViewById(id.invalid_entry);
			tv.setText("Invalid entry");
		}
		if (!isEdit) {
			VitalSigns newVital = new VitalSigns(Integer.valueOf(sbp.getText().
					toString()), Integer.valueOf(dbp.getText().toString()), Float.
					valueOf(temp.getText().toString()), Integer.valueOf(hr.
							getText().toString()));
			patient.getAllPatientVisits().get(index).addVitalSigns(newVital);
		} else {
			VitalSigns vs = null;
			try {
				vs = patient.getAllPatientVisits().get(index).getLatestVitalSigns();
			} catch (NoRecordedDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			vs.setBloodPressureDiastolic(Integer.valueOf(dbp.getText().toString()));
			vs.setBloodPressureSystolic(Integer.valueOf(sbp.getText().toString()));
			vs.setHeartRate(Integer.valueOf(hr.getText().toString()));
			vs.setTemperature(Float.valueOf(temp.getText().toString()));
		}
		intent.putExtra("patient", patient);
		intent.putExtra("index", index);
		startActivity(intent);
	}
	
	public void cancel_new_visit_record_button(View view) {
		Intent intent = new Intent(this, EditVisitRecord.class);
		intent.putExtra("patient", patient);
		intent.putExtra("index", index);
		startActivity(intent);
	}
}
