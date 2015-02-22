package com.triageapplication;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.model.Patient;
import com.triageapplication.R.id;

public class AddNewDoctorVisit extends Activity {

	private DatePickerDialog visitDatePickerDialog;
	private TimePickerDialog visitTimePickerDialog;
	private Patient patient;
	private int visitIndex;
	private String userType;
	
	private Calendar chosenTime;
	
	TextView visitTimeText;
	TextView visitDateText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_doctor_visit);
		
		initializeElements();
		
		Intent intent = this.getIntent();
		userType = intent.getStringExtra("userType");
		patient = (Patient) intent.getExtras().getSerializable("patient");
		visitIndex = intent.getExtras().getInt("record_index");
		chosenTime = Calendar.getInstance();
		
		DecimalFormat df = new DecimalFormat("00");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
        visitTimeText.setText(df.format(chosenTime.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(chosenTime.get(Calendar.MINUTE)));
		
        visitDateText.setText(dateFormatter.format(chosenTime.getTime()));
		
	}
	
	private void initializeElements() {
		visitTimeText = (TextView) findViewById(id.doctor_visit_time_text);
		visitDateText = (TextView) findViewById(id.doctor_visit_date_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_doctor_visit, menu);
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
	
	@Override
	public void onBackPressed() {
	}
	
	public void setVisitTime(View view){
		Calendar newCalendar = Calendar.getInstance();
		visitTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
                    int minute) {
				DecimalFormat df = new DecimalFormat("00");
                chosenTime.set(Calendar.HOUR, hourOfDay);
                chosenTime.set(Calendar.MINUTE, minute);
                visitTimeText.setText(df.format(hourOfDay) + ":" + df.format(minute));
            }
		}, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
		
		visitTimePickerDialog.show();
	}
	
	public void setVisitDate(View view){
		
		Calendar newCalendar = Calendar.getInstance();
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		visitDatePickerDialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						chosenTime.set(year, monthOfYear, dayOfMonth);
				
						visitDateText.setText(dateFormatter.format(newDate.getTime()));

					}

				}, 
				newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH)
		);
		visitDatePickerDialog.show();
	}
	
	public void submitVisitInfo(View view){
		patient.getVisits().get(visitIndex).addDocVisitTimes(chosenTime);
		Intent intent = new Intent(this, ViewPatientVisitRecord.class);
		intent.putExtras(this.getIntent());
		intent.putExtra("patient", patient);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}
	
	public void cancelSubmission(View view){
		Intent intent = new Intent(this, ViewPatientVisitRecord.class);
		intent.putExtras(this.getIntent());
		intent.putExtra("userType", userType);
		startActivity(intent);
	}
	
}
