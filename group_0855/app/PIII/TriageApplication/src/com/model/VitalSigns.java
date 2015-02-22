package com.model;

import java.io.Serializable;
import java.util.Calendar;

public class VitalSigns implements Serializable {

	private static final long serialVersionUID = -6588354223067729273L;

	/**
	 * The patients systolic blood pressure.
	 */
	private int bloodPressureSystolic;
	
	/**
	 * The patients diastolic blood pressure.
	 */
	private int bloodPressureDiastolic;
	
	/**
	 * The patients temperature.
	 */
	private float temperature;
	
	/**
	 * The patients heart rate.
	 */
	private int heartRate;
	
	/**
	 * The time at which the patients vital signs were recorded.
	 */
	private final Calendar time;

	/**
	 * Creates a VitalSigns object using the patients systolic blood pressure,
	 * diastolic blood pressure, temperature, and the time at which their vital
	 * signs were recorded.
	 * 
	 * @param bloodPressureSystolic
	 *            the patient's systolic blood pressure.
	 * @param bloodPressureDiastolic
	 *            the patient's diastolic blood pressure.
	 * @param temperature
	 *            the patient's body temperature in Celsius.
	 * @param heartRate
	 *            the patient's heart rate.
	 */
	public VitalSigns(int bloodPressureSystolic, int bloodPressureDiastolic,
			float temperature, int heartRate) {

		this.bloodPressureSystolic = bloodPressureSystolic;
		this.bloodPressureDiastolic = bloodPressureDiastolic;
		this.temperature = temperature;
		this.heartRate = heartRate;
		this.time = Calendar.getInstance();
	}

	/**
	 * Return the patient's Systolic blood pressure.
	 * 
	 * @return the vital sign systolic blood pressure.
	 */
	public int getBloodPressureSystolic() {
		return bloodPressureSystolic;
	}

	/**
	 * Sets the vital sign systolic blood pressure.
	 * 
	 * @param bloodPressureSystolic
	 *            the systolic blood pressure to set.
	 */
	public void setBloodPressureSystolic(int bloodPressureSystolic) {
		this.bloodPressureSystolic = bloodPressureSystolic;
	}

	/**
	 * Return the patient's diastolic blood pressure.
	 * 
	 * @return the vital sign diastolic blood pressure.
	 */
	public int getBloodPressureDiastolic() {
		return bloodPressureDiastolic;
	}

	/**
	 * Sets the vital sign diastolic blood pressure.
	 * 
	 * @param bloodPressureDiastolic
	 *            the diastolic blood pressure to set.
	 */
	public void setBloodPressureDiastolic(int bloodPressureDiastolic) {
		this.bloodPressureDiastolic = bloodPressureDiastolic;
	}

	/**
	 * Return the patient's body temperature in Celsius.
	 * 
	 * @return the vital sign temperature.
	 */
	public float getTemperature() {
		return temperature;
	}

	/**
	 * Sets the vital sign temperature.
	 * 
	 * @param temperature
	 *            the temperature to set.
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	/**
	 * The patient's heart rate.
	 * 
	 * @return the vital sign heart rate.
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * Sets the vital sign heart rate.
	 * 
	 * @param heartRate
	 *            the heartRate to set.
	 */
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	/**
	 * The time when the vital sign was recorded.
	 * 
	 * @return the time at which the vital signs were recorded.
	 */
	public Calendar getTime() {
		return time;
	}

	/**
	 * Returns all vital signs information in String form. Such as date
	 * recorded, temperature, etc.
	 */
	@Override
	public String toString() {
		String s = "\nVITAL";

		s += "\nDATERECORDED = " + time.getTimeInMillis();
		s += "\nHEARTRATE = " + heartRate;
		s += "\nTEMPERATURE = " + temperature;
		s += "\nPRESSUREDIAS = " + bloodPressureDiastolic;
		s += "\nPRESSURESYST = " + bloodPressureSystolic;

		s += "\nENDVITAL";

		return s;
	}

}
