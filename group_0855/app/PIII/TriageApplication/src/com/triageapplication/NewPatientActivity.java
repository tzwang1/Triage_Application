package com.triageapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.model.Patient;
import com.model.PatientDatabase;

public class NewPatientActivity extends Activity {
	Calendar dobDate;
	TextView dobTxt;
	EditText nameTxt;
	EditText cardTxt;
	private DatePickerDialog dobDatePickerDialog;
	private String userType;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
		dobTxt = (TextView) findViewById(R.id.dob);
		nameTxt = (EditText) findViewById(R.id.patient_name);
		cardTxt= (EditText) findViewById(R.id.health_card_number);
	}

	@Override
	public void onBackPressed() {
	}

	public void setDob(View view) {
		Calendar newCalendar = Calendar.getInstance();
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

		dobDatePickerDialog = new DatePickerDialog(this,
				new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				dobDate = Calendar.getInstance();
				dobDate.set(year, monthOfYear, dayOfMonth);

				dobTxt.setText(dateFormatter.format(dobDate.getTime()));
			}

		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
				);

		dobDatePickerDialog.show();
	}

	public void save(View view) {

		String name = nameTxt.getText().toString();
		String health_card_number = cardTxt.getText().toString();
		String dob = dobTxt.getText().toString();

		File outFile = new File(getFilesDir(), MainActivity.PATIENT_RECORDS);
		PatientDatabase patientDatabase;
		//load PatientDatabase from app writable folder (as new patient will be append the outFile 
		//after the main page resumed as the new patient may be added to PATIENT_RECORDS
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(outFile));
			patientDatabase = new PatientDatabase(bufferedReader);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read Patient database");
		}


		//check for unfilled entries
		if(name.length() == 0 || health_card_number.length() == 0 || dob.length() == 0){
			Toast.makeText(this, R.string.new_patient_error_empty, Toast.LENGTH_LONG).show();
		}else if (patientDatabase.isDuplicated(health_card_number)){
			Toast.makeText(this, R.string.new_patient_error_duplicate, Toast.LENGTH_LONG).show();
		}else{
			Calendar now = Calendar.getInstance();
			if(dobDate.after(now)){
				Toast.makeText(this, R.string.new_patient_error_invalid_dob, Toast.LENGTH_LONG).show();
			}else{
				Patient patient = new Patient(name, dobDate, health_card_number);
				patientDatabase.addPatient(outFile, patient);
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("userType", userType);
				startActivity(intent);
			}
		}
	}

	public void cancel(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}
}
