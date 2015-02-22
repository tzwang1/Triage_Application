package com.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class PatientVisit implements Serializable {

	private static final long serialVersionUID = 3733827242488768197L;

	/**
	 * The exact time the patient arrived at the ER.
	 */
	private final Calendar arrivalDate;

	/**
	 * A sorted list of all the patients recorded symptoms. Sorted by date.
	 */
	private final LinkedList<Symptoms> symptoms;

	/**
	 * A sorted list of all the patients recorded vital signs. Sorted by date.
	 */
	private final LinkedList<VitalSigns> vitals;

	/**
	 * A sorted list of all the patients doctor visit times. Sorted by date.
	 */
	private final LinkedList<Calendar> docVisitTimes;

	private Prescription prescription;

	public PatientVisit(Calendar arrivalDate) {

		this.arrivalDate = arrivalDate;

		symptoms = new LinkedList<Symptoms>();
		vitals = new LinkedList<VitalSigns>();
		docVisitTimes = new LinkedList<Calendar>();
		prescription = null;
	}

	/**
	 * @return the date the patient arrived at the ER.
	 */
	public Calendar getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param visitTime
	 *            the new doctor visit time to add to the record.
	 */
	public void addDocVisitTimes(Calendar visitTime) {
		docVisitTimes.add(0, visitTime);
	}

	/**
	 * Add a new symptom to the list. <br>
	 * Assuming the new symptom is the latest symptom recorded on the patient.
	 * 
	 * @param newSymptom
	 *            the new symptom to add.
	 */
	public void addSymptom(Symptoms newSymptom) {
		symptoms.add(0, newSymptom);
	}

	/**
	 * Add a new recording of the patient's vital signs.
	 * 
	 * @param newVital
	 *            the new vital signs to add.
	 */
	public void addVitalSigns(VitalSigns newVital) {
		vitals.add(0, newVital);
	}

	/**
	 * Return the latest symptoms recorded for the patient.
	 * 
	 * @return the latest symptom the patient has on record.
	 * @throws NoRecordedDataException
	 *             this exception is thrown when there are no symptoms in the
	 *             patient's record to return.
	 */
	public Symptoms getLatestSymptoms() throws NoRecordedDataException {
		if (symptoms.size() == 0)
			throw new NoRecordedDataException("NO SYMPTOMS RECORDED.");
		return symptoms.get(0);
	}

	/**
	 * Return a sorted copy of the patient's symptoms. <br>
	 * Any changes made to this copy will not reflect in the patient's record.
	 * 
	 * @return the list of patient's symptoms sorted by most recent -> least
	 *         recent.
	 */
	public List<Symptoms> getSymptoms() {
		// returning a copy so that any changes made to the copy dont affect
		// the patient record.
		return new LinkedList<Symptoms>(symptoms);
	}

	/**
	 * Return the latest vital signs recorded for the patient.
	 * 
	 * @return the latest vital signs the patient has on record.
	 * @throws NoRecordedDataException
	 *             this exception is thrown when there are no vital sigs in the
	 *             patient's record to return.
	 */
	public VitalSigns getLatestVitalSigns() throws NoRecordedDataException {
		if (vitals.size() == 0)
			throw new NoRecordedDataException("NO VITAL SIGNS RECORDED.");
		return vitals.get(0);
	}

	/**
	 * Return a sorted copy of the patient's vital signs. <br>
	 * Any changes made to this copy will not reflect in the patient's record.
	 * 
	 * @return the list of all the patient's recorded vitals sorted by most
	 *         recent -> least recent.
	 */
	public List<VitalSigns> getVitals() {
		return new LinkedList<VitalSigns>(vitals);
	}

	/**
	 * Return a sorted copy of the all the times the patient has been visited by
	 * a doctor. <br>
	 * Any changes made to this copy will not reflect in the patient's record.
	 * 
	 * @return the docVisitTimes.
	 */
	public List<Calendar> getDocVisitTimes() {
		return new LinkedList<Calendar>(docVisitTimes);
	}

	/**
	 * Returns the latest doctor visit time.
	 * 
	 * @return the last time that the patient was visited by a doctor.
	 * @throws NoRecordedDataException
	 *             this exception is thrown when the patient has not been
	 *             visited by a doctor before.
	 */
	public Calendar getLatestDoctorVisit() throws NoRecordedDataException {
		if (docVisitTimes.size() == 0)
			throw new NoRecordedDataException("NO DOCTOR VISITS RECORDED.");

		return docVisitTimes.get(0);
	}

	/**
	 * Replaces the most recent VitalSigns object in the list with a new
	 * VitalSigns object.
	 * 
	 * @param vs
	 */
	public void setLatestVitals(VitalSigns vs) {
		vitals.set(0, vs);
	}

	/**
	 * Set the patient's prescription for the current visit.
	 * 
	 * @param prescription
	 *            set the patient's prescription for the current visit.
	 */
	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	/**
	 * Return the patient's prescription for this visit.
	 * 
	 * @return the patient's prescription for this visit.
	 * @throws NoRecordedDataException
	 *             if there is no prescription recorded for the current visit.
	 */
	public Prescription getPrescription() throws NoRecordedDataException {
		if (prescription == null)
			throw new NoRecordedDataException("NO PRESCRIPTION RECORDED.");

		return prescription;
	}

	@Override
	public String toString() {
		String s = "\nVISIT";

		s += "\nARRIVALDATE = " + arrivalDate.getTimeInMillis();

		s += "\nALLSYMPTOMS";

		for (Symptoms symptom : symptoms) {
			s += "\t" + symptom;
		}

		s += "\nENDALLSYMPTOMS"; // end all symptoms

		s += "\nALLVITALS";

		for (VitalSigns vital : vitals) {
			s += "\t" + vital;
		}

		s += "\nENDALLVITALS"; // end all vitals

		s += "\nALLDOCVISITS";

		for (Calendar time : docVisitTimes) {
			s += "\nTIME = " + time.getTimeInMillis();
		}

		s += "\nENDALLDOCVISITS"; // end all docVisitTimes

		s += "\nENDVISIT"; // end visit

		return s;
	}

}
