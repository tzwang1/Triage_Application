package com.triageapplication;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.model.NoRecordedDataException;
import com.model.Patient;
import com.model.PatientVisit;
import com.model.Prescription;
import com.model.VitalSigns;
import com.triageapplication.R.id;

public class ViewPatientVisitRecord extends Activity implements OnItemSelectedListener {

	/**
	 * THe patient who's info we are viewing.
	 */
	private Patient patient;

	/**
	 * The index of the visit inside the patient's visit list.
	 */
	private int visitIndex;

	/**
	 * The patientVisit object we are viewing.
	 */
	private PatientVisit visit;

	/**
	 * The index of the vital signs we are viewing.
	 */
	private int vitalsIndex = 0;
	
	private String userType;

	TextView visitDateLabel;
	
	TextView systolicText;
	TextView diastolicText;
	TextView temperatureText;
	TextView heartRateText;

	/**
	 * The spinner object holding the vital signs record dates.
	 */
	Spinner vitalsSpinner ;

	Button addNewVitalsButton;
	Button editLatestVitalsButton;

	TextView prescMedsText;
	TextView prescInstrText;

	Button prescAddEditButton;
	
	TextView docVisitsText;

	Button addDocVisitsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_patient_visit_record);

		Intent intent = this.getIntent();

		userType = intent.getStringExtra("userType");
		System.out.println("user type: " + userType);
		
		initializeElements();
		
		
		patient = (Patient) intent.getExtras().getSerializable("patient");
		visitIndex = intent.getExtras().getInt("record_index");

		visit = patient.getAllPatientVisits().get(visitIndex);
		
		DecimalFormat df = new DecimalFormat("00");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		String dateTimeStr = "Record for: ";
		
        dateTimeStr += (df.format(visit.getArrivalDate().get(Calendar.HOUR_OF_DAY)) + ":" + df.format(visit.getArrivalDate().get(Calendar.MINUTE)));
		
        dateTimeStr += "  " + (dateFormatter.format(visit.getArrivalDate().getTime()));
		
        visitDateLabel.setText(dateTimeStr);
        
		initializeVitals(intent);

		initializePrescription();

		initializeDocVisitTimes();

	}

	private void initializeElements() {

		visitDateLabel = (TextView) findViewById(R.id.visit_date_label);
		
		systolicText = (TextView) findViewById(R.id.vitals_systolic_pressure_text);
		diastolicText = (TextView) findViewById(R.id.vitals_diastolic_pressure_text);
		temperatureText = (TextView) findViewById(id.vitals_temperature_text);
		heartRateText = (TextView) findViewById(id.vitals_heart_rate_text);

		addNewVitalsButton = (Button) findViewById(id.vitals_add_new_button);
		editLatestVitalsButton = (Button) findViewById(id.vitals_edit_latest_button);

		vitalsSpinner = (Spinner) findViewById(id.vitals_dates_spinner);

		prescMedsText = (TextView) findViewById(id.prescription_meds_text);
		prescInstrText = (TextView) findViewById(id.prescription_instructions_text);

		prescAddEditButton = (Button) findViewById(id.prescription_edit_button);
	
		
		docVisitsText = (TextView) findViewById(id.doctor_visits_text);
		addDocVisitsButton = (Button) findViewById(id.doctor_add_new_visit_button);
		
		if(userType.equals("nurse")) {
			prescAddEditButton.setEnabled(false);
			
		}
		
		if(userType.equals("physician")) {
			addNewVitalsButton.setEnabled(false);
			editLatestVitalsButton.setEnabled(false);
			addDocVisitsButton.setEnabled(false);
			
		}
			
	}

	/**
	 * This block initializes the vital signs and the spinner.
	 * @param intent the intent is used to check if we are returning from 
	 * create vitals.
	 */
	private void initializeVitals(Intent intent) {
		// This preserves the selected vitals on the spinner if the user is
		// returning from the createVitals activity.
		if (intent.getExtras().containsKey("vitals_index")) {
			System.out.println("VITALS PRESERVED");
			vitalsIndex = intent.getExtras().getInt("vitals_index");
		}


		List<String> vitals = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


		for (VitalSigns vs : visit.getVitals()) {
			vitals.add(sdf.format(vs.getTime().getTime()));
		}

		if (vitals.size() == 0)
			editLatestVitalsButton.setEnabled(false);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, vitals);
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vitalsSpinner.setAdapter(dataAdapter);

		refreshVitals();

		//Add a listener to the spinner so that we update the vitals whenever
		//the spinner changes.
		vitalsSpinner.setOnItemSelectedListener(this);
	}

	/**
	 * This block initializes the prescription information.
	 */
	private void initializePrescription() {
		try {
			Prescription presc = visit.getPrescription();

			prescMedsText.setText(presc.getMedicationName());
			prescInstrText.setText(presc.getInstructions());

			prescAddEditButton.setText("Edit prescription");
					

		} catch(NoRecordedDataException nrde){
			prescMedsText.setText("No prescription available.");
			prescAddEditButton.setText("Add prescription");
			prescInstrText.setText("");
		}
	}

	private void initializeDocVisitTimes(){
		
		List<Calendar> visits = patient.getVisits().get(visitIndex).getDocVisitTimes();
		String visitStr = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for(Calendar visit : visits){
			//visitStr += sdf.format(visit) + "\n";
			visitStr += sdf.format(visit.getTime()) + "\n";
		}
		
		visitStr = visits.size() == 0 ? "No doctor visit recorded." : visitStr; 
		
		visitStr.trim();
		
		docVisitsText.setText(visitStr);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_patient_visit_record, menu);
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

	/**
	 * Displays the vitals information based on which date is chosen in the spinner.
	 */
	private void refreshVitals() {

		try{
			VitalSigns selectedVitals = visit.getVitals().get(vitalsIndex);

			systolicText.setText("Systolic blood pressure: " + selectedVitals.getBloodPressureSystolic());
			diastolicText.setText("Diastolic blood pressure: " + selectedVitals.getBloodPressureDiastolic());
			temperatureText.setText("Temperature: " + selectedVitals.getTemperature());
			heartRateText.setText("Heart rate: " + selectedVitals.getHeartRate());

		} catch(IndexOutOfBoundsException ioobe){
			// If this patient has no past vitals recorded, then a
			// "No vitals recorded..." is displayed instead.
			systolicText.setText("No vitals recorded for this visit.");
			diastolicText.setText("");
			temperatureText.setText("");
			heartRateText.setText("");
		}

	}

	public void viewPatientVisitBack(View view) {
		Intent intent = new Intent(this, VisitRecords.class);
		intent.putExtra("patient", patient);
		intent.putExtra("vitals_index", vitalsIndex);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}

	// A listener which updates the TextViews with the proper selected vital
	// signs whenever invoked.
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		vitalsIndex = position;
		refreshVitals();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {}

	public void addNewVitals(View view) {
		Intent intent = new Intent(this, NewVitals.class);
		intent.putExtra("patient", patient);
		intent.putExtra("record_index", visitIndex);
		intent.putExtra("isEdit", false);
		intent.putExtra("vitals_index", vitalsIndex);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}

	public void editLatestVitals(View view) {
		Intent intent = new Intent(this, NewVitals.class);
		intent.putExtra("patient", patient);
		intent.putExtra("record_index", visitIndex);
		intent.putExtra("isEdit", true);
		intent.putExtra("userType", userType);
		startActivity(intent);
	}

	public void editPrescription(View view) {
		Intent intent = new Intent(this, EditPrescription.class);
		intent.putExtra("patient", patient);
		intent.putExtra("record_index", visitIndex);
		intent.putExtra("userType", userType);

		startActivity(intent);
	}

	public void addNewDocVisit(View view) {
		Intent intent = new Intent(this, AddNewDoctorVisit.class);
		intent.putExtra("patient", patient);
		intent.putExtra("record_index", visitIndex);
		intent.putExtra("userType", userType);

		startActivity(intent);
	}
	
}
