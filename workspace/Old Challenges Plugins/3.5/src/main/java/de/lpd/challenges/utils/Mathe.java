package de.lpd.challenges.utils;

import java.util.Random;

public class Mathe {
	
	public static int getRandom(int min, int max) {
		Random r = new Random();
		return r.nextInt(max + 1 - min) + min;
	}
	
}
