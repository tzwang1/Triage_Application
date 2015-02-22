package com.model;

import java.io.Serializable;

/**
 * @author Kevin
 *
 */
public class User implements Serializable, Comparable {
	
	/**
	 * The user's username.
	 */
	private String username;
	/**
	 * The user's password.
	 */
	private String password;
	/**
	 * The user's crediential.
	 */
	private String credential;
	/**
	 * Create a new user object using the user's username, password, and credential.
	 * @param username
	 * 			the user's username.
	 * @param password
	 * 			the user's password.
	 * @param credential
	 * 			the user's credential.
	 */
	public User(String username, String password, String credential) {
		super();
		this.username = username;
		this.password = password;
		this.credential = credential;
	}
	
	/**
	 * Returns the user's username.
	 * @return the user's username.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the user's username.
	 * @param username
	 * 			the user's new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Return the user's password.
	 * @return the user's password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Set the user's password.
	 * @param password
	 * 				the user's new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns the user's credential.
	 * @return the user's credential.
	 */
	public String getCredential() {
		return credential;
	}
	
	/**
	 * Set the user's credential.
	 * @param credential
	 * 				the user's credential.
	 */
	public void setCredential(String credential) {
		this.credential = credential;
	}

	@Override
	public String toString() {
		return username + "\n" + credential;
	}

	@Override
	public int compareTo(Object user) {
		user = (User) user;
		return username.compareTo(((User) user).getUsername());
	}
}
