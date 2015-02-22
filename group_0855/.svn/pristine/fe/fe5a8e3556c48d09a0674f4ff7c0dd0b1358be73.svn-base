package com.model;

import java.io.Serializable;

public class Prescription implements Serializable {

	private static final long serialVersionUID = -3994020477618418222L;

	private final String medicationName;

	private final String instructions;

	/**
	 * Create a new Prescription with the name of the medication and the
	 * instructions for consuming it.
	 * 
	 * @param medicationName
	 *            the name of the patient's medication.
	 * @param instructions
	 *            the instructions for consuming the medication.
	 */
	public Prescription(String medicationName, String instructions) {
		this.medicationName = medicationName;
		this.instructions = instructions;
	}

	/**
	 * Get the name of the medication.
	 * 
	 * @return the medicationName
	 */
	public String getMedicationName() {
		return medicationName;
	}

	/**
	 * Get the instructions for consuming the medication.
	 * 
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Returns all prescription information in String form. This includes
	 * medication name and instructions for consuming the medication.
	 */
	@Override
	public String toString() {
		String s = "\nPRESCRIPTION";

		s += "\nMEDICATION = " + medicationName;
		s += "\nINSTRUCTIONS = " + instructions;

		s += "\nENDPRESCRIPTION";

		return s;
	}

}
