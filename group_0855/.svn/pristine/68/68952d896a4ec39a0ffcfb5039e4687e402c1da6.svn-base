package com.model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PatientDatabase implements Serializable{
	
	/**
	 * 
	 */
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
		this.inputStream = inputStream;

		//if patientList built already, it is not necessary to do it again
		//this.populate(this.inputStream);
		if(patientList == null){
			patientList = new ArrayList<Patient>();
			this.populate(this.inputStream);
		}
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
				if(record.length < 3){
					//empty line, skip it
					continue;
				}
			
				String healthCardNumber = record[0];
				String name = record[1];
				String[] dateString = record[2].split("-");
			
				int year = Integer.parseInt(dateString[0]);
				int month = Integer.parseInt(dateString[1]);
				int day = Integer.parseInt(dateString[2]);
			
				Calendar date = Calendar.getInstance();
			
				date.set(year, month - 1, day);  
			
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
				//outputStream.write((p.toString()+ "\n").getBytes());
				outputStream.write(p.toString().getBytes());
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the global instance of the PatientDatabase.
	 * @return the PatientDatabase instance.
	 * @throws IOException 
	 */
	public List<Patient> getInstance() throws IOException{
		
		if(patientList == null) {
			//Yes, the following line is an error. We would have to code in the
			//file location.
			
			PatientDatabase PB = new PatientDatabase(this.inputStream);
			patientList = PB.getPatientList();
		}
		return patientList;
	}
	
	public ArrayList<Patient> getPatientList(){
		
		return patientList;
		
	}
	
	public void setPatientList(ArrayList<Patient> patientList){
		
		PatientDatabase.patientList = patientList;
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
	
	public Patient retreivePatientByName(String patientName) throws NoRecordedDataException {
		
		for(Patient p : patientList) {
			if(p.getName().equals(patientName)){
				return p;
			}
		}
		
		throw new NoRecordedDataException("NO SUCH PATIENT.");
		
	}
	
	public void savePatientVisitRecords(ArrayList<Patient> patient, FileOutputStream outputStream) throws IOException, NoRecordedDataException{
		
		ArrayList<Patient> patientVisitRecordsList = new ArrayList<Patient>();
		//String patientVisitRecordList = "";
		for(Patient p : patient){
			if(p.getLatestPatientVisit().getArrivalDate() == null) {
				
				System.out.println(p.getLatestPatientVisit().getArrivalDate());
				patient.remove(p);
				
			}else{	
				
				patientVisitRecordsList.add(p);
			}
				
		}
	
		try{
			
			for(Patient p: patientVisitRecordsList) {
				outputStream.write((p.toString() + "\n").getBytes());
				
			} 
		
		}catch (IOException e) {
				
			e.printStackTrace();
		
		}finally{
			
			outputStream.close();
		}
			
	}
	
	public void loadPatientVisitRecords(String fileName) throws IOException{
		
		patientList = new ArrayList<Patient>();
		
		File file = new File(fileName);
		
		if(file.exists()){
			this.populateVisitRecords(file.getPath());
		} else {
			file.createNewFile();
		}	
	}

		
	//I will change this method after the toString method for patients is finished
	private void populateVisitRecords(String fileName)throws IOException {
		
		File file = new File(fileName);
		BufferedReader inputStream = null;	
		String [] record;
		
		try{
			inputStream = new BufferedReader(new FileReader(file));
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
	
	public ArrayList<Patient> sortListByUrgency() {
		 
		ArrayList<Patient> sortedList = new ArrayList<Patient>(patientList);
		
		int len = sortedList.size();
		
		for(int i = len - 1; i >= 0; i--) {			
			
			try{
				//Remove all patients who have been seen by docs
				//System.out.println(sortedList.get(i).getLatestPatientVisit().getDocVisitTimes().size());
				if(sortedList.get(i).getLatestPatientVisit().getDocVisitTimes().size() != 0) {
					System.out.println("Removing " + sortedList.get(i).getName() + " because seen by doctor");
					sortedList.remove(i);
					break;
				}
				
				sortedList.get(i).getUrgency();
				
			}catch(NoRecordedDataException e) {
				System.out.println("Removing " + sortedList.get(i).getName() + " because " + e.getMessage());
				
				sortedList.remove(i);
			}
		}
		
		Collections.sort(sortedList, new UrgencyComparator());
		
		System.out.println("SORTED LIST BEING RETURNED: " + sortedList.toString());
		
		return sortedList;
	
	}
	
	/**
	 * Check whether a patient in the patient database has the same health card number as the argument
	 * Returns true if they do. Returns false otherwise.
	 * @param healthCardNumber
	 * 					the patient's health card number.
	 * @return true if a patient in the patient database has the same health card number. Returns false otherwise.
	 */
	public boolean isDuplicated(String healthCardNumber) {
		for(Patient p: patientList) {
			if(healthCardNumber.equals(p.getHealthCardNumber())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Adds a patient to the patient database object.
	 * @param outFile
	 * 			the File object that contains the information to be saved.
	 * @param patient
	 * 			the patient that should be added to the patient database object.
	 */
	public void addPatient(File outFile, Patient patient) {
		patientList.add(patient);
		
		//Append this patient to patient_records.txt in writable folder
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFile, true)));
		    
		    //222323,Charles Babbage,1954-03-23
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String dob = dateFormatter.format(patient.getDob().getTime());
			String line = String.format("%s,%s,%s\n", patient.getHealthCardNumber(),patient.getName(), dob); 
		    out.println(line);
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Replaces a patient in the patient database object with a new patient.
	 * @param healthCardNumber
	 * 					the health card number of the patient that will be replaced.
	 * @param patient
	 * 			the new patient that will replace the old patient.
	 */
	public void replacePatient(String healthCardNumber, Patient patient) {
		for (int i = 0; i < patientList.size(); i++) {
			if (patientList.get(i).getHealthCardNumber().equals(healthCardNumber)) {
				patientList.remove(patientList.get(i));
				patientList.add(i, patient);
				return;
			}
		}
	}
}

