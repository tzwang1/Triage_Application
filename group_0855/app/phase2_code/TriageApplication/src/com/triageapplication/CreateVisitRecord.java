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
import com.model.PatientVisit;
import com.triageapplication.R.id;

public class CreateVisitRecord extends Activity {

	private DatePickerDialog arrivalDatePickerDialog;
	private TimePickerDialog arrivalTimePickerDialog;
	private Patient patient;
	private Calendar chosenTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		patient = (Patient) intent.getExtras().getSerializable("patient");
		setContentView(R.layout.activity_create_visit_record);
		chosenTime = Calendar.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_visit_record, menu);
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
	
	public void setArrivalTime(View view) {
		Calendar newCalendar = Calendar.getInstance();
		arrivalTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay,
                    int minute) {
				TextView tv = (TextView) findViewById(id.chosen_arrival_time); 
				DecimalFormat df = new DecimalFormat("00");
                chosenTime.set(Calendar.HOUR, hourOfDay);
                chosenTime.set(Calendar.MINUTE, minute);
                tv.setText(df.format(hourOfDay) + ":" + df.format(minute));
            }
		}, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
		arrivalTimePickerDialog.show();
	}
	
	public void setArrivalDate(View view) {
		Calendar newCalendar = Calendar.getInstance();
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		arrivalDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                chosenTime.set(year, monthOfYear, dayOfMonth);
                TextView tv = (TextView) findViewById(id.chosen_arrival_date);
                tv.setText(dateFormatter.format(newDate.getTime()));
                
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		arrivalDatePickerDialog.show();
	}
	
	public void submitArrivalInfo(View view) {
		Intent intent = new Intent(this, VisitRecords.class);
		patient.addNewPatientVisit(new PatientVisit(chosenTime));
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
	
	public void cancel(View view) {
		Intent intent = new Intent(this, VisitRecords.class);
		intent.putExtra("patient", patient);
		startActivity(intent);
	}
}
