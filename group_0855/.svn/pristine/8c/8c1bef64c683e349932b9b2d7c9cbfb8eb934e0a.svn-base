package com.model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class is in charge of storing patient information during runtime, and 
 * loading/saving that information when the app is starting/closing.
 */
public class PatientDatabase implements Serializable{
	
	private static final long serialVersionUID = -2642845893021955376L;
	
	/**
	 * List of patient objects.
	 */
	private static ArrayList<Patient> patientList = null;
	private BufferedReader inputStream = null;
	
	/**
	 * Create a new PatientDatabase object which contains a list of all patients
	 * in the hospital's system.
	 * @param dir the directory of the file.
	 * @param fileName the name of the file.
	 * @throws IOException
	 */
	public PatientDatabase(BufferedReader inputStream) throws IOException{
		patientList = new ArrayList<Patient>();
		this.inputStream = inputStream;
		this.populate(this.inputStream);
	}
	
	/**
	 * Fills a list of patients with patient objects.
	 * @param filePath the location of the file.
	 * @throws IOException 
	 * @throws FileNotFoundException thrown when the file is not found/does not
	 * exist in the given location.
	 */
	private void populate(BufferedReader inputStream) throws IOException {
		
		String [] record;
		
		try{
			String characterRead = null;
			
			do {
				characterRead = inputStream.readLine();
				if(characterRead == null) {
					break;
				}
			
				record = characterRead.split(",");
			
				String healthCardNumber = record[0];
				String name = record[1];
				String[] dateString = record[2].split("-");
			
				int year = Integer.parseInt(dateString[0]);
				int month = Integer.parseInt(dateString[1]);
				int day = Integer.parseInt(dateString[2]);
			
				Calendar date = Calendar.getInstance();
			
				date.set(year, month, day);  
			
				Patient patient = new Patient(name, date, healthCardNumber);
			
				patientList.add(patient);
				
			}while (true);
		}
		
		catch (FileNotFoundException e) {
			
			System.out.println("Error opening file.");
			
		}
		
		finally {
			
			if(inputStream != null)
				inputStream.close();
		}
		
	}
	
	/**
	 * Save list of patients.
	 * @param outputStream
	 */
	public void savePatientList(FileOutputStream outputStream){
		try{
			for(Patient p: patientList){
				outputStream.write(p.toString().getBytes());
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
		
	/**
	 * Return the list of patients saved in the patient database.
	 * @return the list of patients saved in the patient database.
	 */
	public ArrayList<Patient> getPatientList(){
		
		return patientList;
		
	}
	
	/**
	 * Return the patient with the specified health card number.
	 * @param healthCardNum the patient's health card number to look up.
	 * @return the patient with the health card number: healthCardNum.
	 * @throws NoRecordedDataException This exception is thrown when a patient 
	 * with the specified health card number does not exist.
	 */
	public Patient retreivePatient(String healthCardNum) throws NoRecordedDataException {
	
		for(Patient p : patientList) {
			if(p.getHealthCardNumber().equals(healthCardNum)){
				return p;
			}
		}
		
		throw new NoRecordedDataException("NO SUCH PATIENT.");
		
	}
	
	/**
	 * Saves all patient's into a master patient folder. Each patient is stored
	 * in their own file, denoted by their unique health card number.
	 */
	public static void savePatientsToFile(String patientFolderPath) {
		FileOutputStream fOut;
		
		String filePath = patientFolderPath; //Some folder path here. For example: C:\\Users\\afsheen\\Desktop\\Patients_Folder
		
		for(Patient p : patientList){
			try{
				fOut = new FileOutputStream(filePath + p.getHealthCardNumber() + ".txt");
			
				ObjectOutputStream objOut = new ObjectOutputStream(fOut);
				
				objOut.writeObject(p);
				
				fOut.close();
				objOut.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	
	}
	
	/**
	 * Populates the patientList from a master patient's folder. 
	 */
	public static void readPatientsFromFolder(String patientFolderPath) {
		FileInputStream fIn;
		
		String filePath = patientFolderPath; //Some folder path here. For example: C:\\Users\\afsheen\\Desktop\\Patients_Folder
		
		File patientFolder = new File(filePath);
		
		String[] patientFiles = patientFolder.list();  
		
		for(String patientFileName : patientFiles){
			try{
				fIn = new FileInputStream(patientFileName);
			
				ObjectInputStream objIn = new ObjectInputStream(fIn);
				
				Object o = objIn.readObject();
				
				patientList.add((Patient) o);
				
				fIn.close();
				objIn.close();
				
			} catch (IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	
	}	
}
		


	

	

