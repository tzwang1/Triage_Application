//package com.model;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class Test {
//
//	public static void main(String[] args) throws IOException {
//
//		System.out.println("sdf");
//		File file = new File("c:\\Users\\cdl\\Desktop");
//		String fileName = "patient_records.txt";
//		Patient patient1;
//		Patient patient2;
//		FileOutputStream outputStream = new FileOutputStream("patient_visit_records.txt");
//		
//		Symptoms symptom = new Symptoms("sore throat");
//		VitalSigns vital = new VitalSigns(1,2,3,4);
//		
//		PatientVisit visit = new PatientVisit(Calendar.getInstance());
//		visit.addDocVisitTimes(Calendar.getInstance());
//		visit.addSymptom(symptom);
//		visit.addVitalSigns(vital);
//		ArrayList<Patient> patientList = new ArrayList<Patient>();
//		
//		PatientDatabase patientDB = new PatientDatabase(fileName);
//		patientList = patientDB.getPatientList();
//		
//		patient1 = patientList.get(0);
//		patient1.addNewPatientVisit(visit);
//		
//		patient2 = patientList.get(1);
//		patient2.addNewPatientVisit(visit);
//		
//		for(Patient p: patientList) {
//			
//			if(patient1.getHealthCardNumber() == p.getHealthCardNumber()) {
//				
//				patientList.set(patientList.indexOf(p), patient1);
//				
//			}
//			
//			if(patient2.getHealthCardNumber() == p.getHealthCardNumber()) {
//				
//				patientList.set(patientList.indexOf(p), patient2);
//			}
//		}
//		
//		patientDB.savePatientVisitRecords(patientList, outputStream);
//
////		System.out.println(patientDB.getPatientList().toString());
//
//	}
//
//}
