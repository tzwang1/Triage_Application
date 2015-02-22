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
import com.model.Prescription;
import com.triageapplication.R.id;

public class EditPrescription extends Activity {

	private Patient patient;
	private int visitIndex;
	private String userType;

	EditText medsField;
	EditText instrField;
	
	TextView errorText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_prescription);

		initializeElements();

		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
		patient = (Patient) intent.getExtras().getSerializable("patient");
		visitIndex = intent.getExtras().getInt("visitIndex");

		try{
			//If there is already a prescription, display it. This should make
			//small edits easier.
			Prescription presc = patient.getVisits().get(visitIndex).getPrescription();
			medsField.setText(presc.getMedicationName());
			instrField.setText(presc.getInstructions());
		} catch(NoRecordedDataException nrde){}

	}
	
	@Override
	public void onBackPressed() {
	}

	private void initializeElements(){
		medsField = (EditText) findViewById(id.prescription_meds_field);
		instrField = (EditText) findViewById(id.prescription_instructions_field);
		errorText = (TextView) findViewById(id.prescription_error_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_prescription, menu);
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

	public void submitEditPrescription(View view) {
		Intent intent = new Intent(this, ViewPatientVisitRecord.class);

		intent.putExtra("patient", patient);
		intent.putExtra("record_index", visitIndex);

		
		
		String medName = medsField.getText().toString().trim();
		String instrs = instrField.getText().toString().trim();
		
		if((medName.equals("") || instrs.equals("")) && !(medName.equals("") && instrs.equals("")))
			errorText.setText("Please fill in both fields.\nKeep both fields empty to delete prescription.");
		
		else {
				
			Prescription pres = new Prescription(medName, instrs);
	
			patient.getVisits().get(visitIndex).setPrescription(pres);
			intent.putExtra("userType", userType);
			startActivity(intent);
		}
	}

	public void cancelEditPrescription(View view) {
		Intent intent = new Intent(this, ViewPatientVisitRecord.class);

		intent.putExtra("patient", patient);
		intent.putExtra("userType", userType);
		intent.putExtra("record_index", visitIndex);

		startActivity(intent);
	}
}
