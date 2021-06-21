package de.lpd.challenges.utils;

public class Mathe {
	
	public static int getRandom(int min, int max) {
	    if (min > max) {
	        throw new IllegalArgumentException("Min " + min + " greater than max " + max);
	    }      
	    return (int) ( (long) min + Math.random() * ((long)max - min + 1));
	}
	
}
