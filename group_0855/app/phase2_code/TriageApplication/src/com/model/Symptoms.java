package com.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * A class to store the symptoms of a patient at a given time.
 */
public class Symptoms implements Serializable {

	private static final long serialVersionUID = -4718468175897824911L;

	/**
	 * A description of the patient's symptom.
	 */
	private final String symptoms;

	/**
	 * The time when this symptom was recorded.
	 */
	private final Calendar date;

	/**
	 * Create a new symptom with a text description at the current time.
	 * 
	 * @param symptoms
	 *            the text description of the symptom.
	 */
	public Symptoms(String symptoms) {
		this.symptoms = symptoms;
		this.date = Calendar.getInstance();
	}

	/**
	 * The description of the symptom.
	 * 
	 * @return the symptom.
	 */
	public String getSymptoms() {
		return symptoms;
	}

	/**
	 * Get the time when the symptom was recorded.
	 *
	 * @return the time when the symptom was put into records.
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Returns all symptom information in String form. Such as symptom
	 * description and time recorded.
	 */
	@Override
	public String toString() {
		String s = "\nSYMPTOM";

		s += "\nDATERECORDED = " + date.getTimeInMillis();
		s += "\nDESCRIPTION = " + symptoms;

		s += "\nENDSYMPTOM";

		return s;
	}
}
