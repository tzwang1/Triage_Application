package com.triageapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.model.NoRecordedDataException;
import com.model.Patient;
import com.model.VitalSigns;
import com.triageapplication.R.id;

public class NewVitals extends Activity {

	private Patient patient;
	private int visitIndex = -1;
	private boolean isEdit = false;
	private int vitalsIndex;
	private String userType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_vitals);
		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
		isEdit = intent.getExtras().getBoolean("isEdit");
		visitIndex = intent.getExtras().getInt("record_index");
		patient = (Patient) intent.getExtras().getSerializable("patient");
		vitalsIndex = intent.getExtras().getInt("vitals_index");
	}

	@Override
	public void onBackPressed() {
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
		Intent intent = new Intent(this, ViewPatientVisitRecord.class);
		EditText sbp = (EditText) findViewById(id.systolic_blood_pressure);
		EditText dbp = (EditText) findViewById(id.diastolic_blood_pressure);
		EditText hr = (EditText) findViewById(id.heart_rate);
		EditText temp = (EditText) findViewById(id.temperature);
		// Checks for any unfilled entries.
		if ((sbp.getText().toString().matches(""))
				|| (dbp.getText().toString().matches(""))
				|| (hr.getText().toString().matches(""))
				|| (temp.getText().toString().matches(""))) {
			Toast.makeText(NewVitals.this, "Invalid entry",Toast.LENGTH_SHORT).show();
//			TextView tv = (TextView) findViewById(id.invalid_entry);
//			tv.setText("Invalid entry");
		} 
		else {
			if (!isEdit) {
				VitalSigns newVital = new VitalSigns(Integer.valueOf(sbp
						.getText().toString()), Integer.valueOf(dbp.getText()
								.toString()), Float.valueOf(temp.getText().toString()),
								Integer.valueOf(hr.getText().toString()));

				patient.getAllPatientVisits().get(visitIndex).addVitalSigns(newVital);

			} 
			else {  //if it is an edit
				VitalSigns vs = null;
				try {
					vs = patient.getAllPatientVisits().get(visitIndex)
							.getLatestVitalSigns();			

				} catch (NoRecordedDataException e) {
					e.printStackTrace();
				}

				vs.setBloodPressureDiastolic(Integer.valueOf(dbp.getText()
						.toString()));
				vs.setBloodPressureSystolic(Integer.valueOf(sbp.getText()
						.toString()));
				vs.setHeartRate(Integer.valueOf(hr.getText().toString()));
				vs.setTemperature(Float.valueOf(temp.getText().toString()));
			}
			intent.putExtra("patient", patient);
			intent.putExtra("record_index", visitIndex);
			intent.putExtra("vitals_index", vitalsIndex);
			intent.putExtra("userType", userType);
			startActivity(intent);
		}
	}

	public void cancel_new_visit_record_button(View view) {
		Intent intent = new Intent(this, ViewPatientVisitRecord.class);
		intent.putExtra("userType", userType);
		intent.putExtra("patient", patient);
		intent.putExtra("record_index", visitIndex);
		intent.putExtra("vitals_index", vitalsIndex);
		startActivity(intent);
	}
}
