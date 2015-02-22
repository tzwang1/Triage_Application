package com.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * @author Afsheen
 */
public class Patient implements Serializable {

	private static final long serialVersionUID = -8664964819401394658L;

	/**
	 * The patient's full name.
	 */
	private String name;

	/**
	 * The patients date of birth
	 */
	private Calendar dob;

	/**
	 * The patients health card number
	 */
	private String healthCardNumber;

	/**
	 * All the times the patient has visited the ER. Each visit contains the
	 * information about the patient's stay (arrival time, vitals, etc).
	 */
	private LinkedList<PatientVisit> visits;

	/**
	 * The patient's urgency rating.
	 */
	private int urgency = -1;

	public Patient() {
		visits = new LinkedList<PatientVisit> ();
	}

	/**
	 * Create a new patient using their name, date of birth, and health card
	 * number. If the patient has visited before, use their old visit logs.
	 * 
	 * @param name
	 *            the patient's name.
	 * @param dob
	 *            the patient's date of birth.
	 * @param healthCardNumber
	 *            the patient's health card number.
	 * @param visits
	 *            the patients previous visit records.
	 */
	public Patient(String name, Calendar dob, String healthCardNumber) {
		this();

		this.name = name;
		this.dob = dob;
		this.healthCardNumber = healthCardNumber;
	}

	/**
	 * Return the patient's full name.
	 * @return the patient's full name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the patient's name.
	 * @param name
	 * 			the patient's new name.
	 */			
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return the patient's date of birth.
	 * 
	 * @return the patient's date of birth.
	 */
	public Calendar getDob() {
		return dob;
	}
	/**
	 * Set the patient's date of birth.
	 * @param dob
	 * 		the patient's date of birth.
	 */
	public void setDob(Calendar dob) {
		this.dob = dob;
	}

	/**
	 * Return the patient's unique health card number.
	 * 
	 * @return the patient's health card number.
	 */
	public String getHealthCardNumber() {
		return healthCardNumber;
	}
	
	/**
	 * Set the patient's health card number.
	 * @param healthCardNumber
	 * 					the patient's health card number.
	 */
	public void setHealthCardNumber(String healthCardNumber) {
		this.healthCardNumber = healthCardNumber;
	}

	/**
	 * Return the list of PatientVisit objects.
	 * @return the list of PatientVisit objects.
	 */
	public LinkedList<PatientVisit> getAllPatientVisits() {
		return visits;
	}
	
	/**
	 * 
	 * @return
	 */
	public LinkedList<PatientVisit> getVisits() {
		return visits;
	}
	
	/**
	 * Set the patient's visit record.
	 * @param visits
	 * 			the list of PatientVisit objects.
	 */
	public void setVisits(LinkedList<PatientVisit> visits) {
		this.visits = visits;
	}
	
	/**
	 * Set the patient's urgency level.
	 * @param urgency
	 * 			the patient's urgency level.
	 */
	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}
	
	/**
	 * Returns the patient's urgency rating based on the ER's urgency rating
	 * scale.
	 * 
	 * @return the urgency level of the patient.
	 * @throws NoRecordedDataException
	 *             if there's information missing, which is required to
	 *             calculate the urgency. Eg: vital signs.
	 */
	public int getUrgency() throws NoRecordedDataException {
		updateUrgency();
		return urgency;
	}

	/**
	 * @param Recaucluates the urgency level of the patient.
	 * @throws NoRecordedDataException
	 */
	private void updateUrgency() throws NoRecordedDataException {

		urgency = 0;

		VitalSigns latestVitals = getLatestPatientVisit().getLatestVitalSigns();

		if (getAgeInYears() < 2)
			urgency++;

		if (latestVitals.getTemperature() <= 39.0)
			urgency++;

		if (latestVitals.getBloodPressureSystolic() >= 140
				|| latestVitals.getBloodPressureDiastolic() >= 90)
			urgency++;

		if (latestVitals.getHeartRate() <= 50
				|| latestVitals.getHeartRate() >= 100)
			urgency++;
	}

	/**
	 * Calculates the patient's age in years and returns it.
	 * 
	 * @return the patient's age in years.
	 */
	private int getAgeInYears() {

		long ageInMilis = Calendar.getInstance().getTimeInMillis()
				- dob.getTimeInMillis();

		ageInMilis /= 3.15569e10; // The number of ms in 1 year.

		return (int) ageInMilis;

	}
	
	/**
	 * Return the latest visit record for the patient.
	 * 
	 * @return the latest visit record of the patient.
	 * @throws NoRecordedDataException 
	 */
	public PatientVisit getLatestPatientVisit() throws NoRecordedDataException {
		
		if(visits.size() == 0)
			throw new NoRecordedDataException("NO VISIT RECORDED.");
		
		return visits.get(0);
	}

	/**
	 * Add a new visit to the patient's record.
	 * 
	 * @param visit
	 *            the visit to add.
	 */
	public void addNewPatientVisit(PatientVisit visit) {
		visits.add(0, visit);
	}
	
	/**
	 * Print out all the patient's information as well as all their previous
	 * visit logs.
	 */
	@Override
	public String toString() {
		String s = "\nPATIENT";

		s += String.format("\nNAME = %s \nDOB = %s \nHEALTHCARDNUM = %s", name,
				dob.getTimeInMillis(), healthCardNumber);
		for (PatientVisit visit : visits) {
			s += visit;
		}

		s += "\nENDPATIENT";
		return s;
	}
	/**
	 * Return the patient's visit record contained in the index 'index'.
	 * @param index
	 * 			the index of the returned visit.
	 * @return the patient's visit record contained in the index.
	 */
	public PatientVisit getPatientVisit(int index) {
		return visits.get(index);
	}
}