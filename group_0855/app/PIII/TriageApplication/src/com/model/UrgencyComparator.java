package com.model;

import java.util.Comparator;

public class UrgencyComparator implements Comparator<Patient> {

	@Override
	public int compare(Patient p1, Patient p2) {
		
		try {
			
			if(p1.getUrgency() < p2.getUrgency())
				
				return -1;
		
			else if(p1.getUrgency() > p2.getUrgency())
				
				return 1;
			
		} catch (NoRecordedDataException e){
			System.err.println("THIS SHOULD NOT HAPPEN!");
			e.printStackTrace();
		}
		
		return 0;
		
	}
}