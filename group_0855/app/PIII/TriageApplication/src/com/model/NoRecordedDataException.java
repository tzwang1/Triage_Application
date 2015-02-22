/**
 * This exception is thrown when an attempt is made to access a patient's
 * recorded data when there are no entries in it.
 * @author Afsheen
 */
package com.model;
public class NoRecordedDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRecordedDataException() {
		super();
	}

	public NoRecordedDataException(String message) {
		super(message);
	}

	public NoRecordedDataException(Throwable cause) {
		super(cause);
	}

}
