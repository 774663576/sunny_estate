package com.sunnyestate.utils;


import java.io.*;

public class Base64
{
	public final static char[] BASE64_ALPHABET = 
		{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
		  'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
		  'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
		  'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
		  'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
		  '8', '9', '+', '/' };

	
	public final static char[] BASE64_SAFE_ALPHABET = 
		{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
		  'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
		  'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
		  'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		  'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
		  'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
		  '8', '9', '-', '_' };
	

	public static String toBase64(String data) {
		return toBase64(data, BASE64_ALPHABET);
	}
	
	public static String toBase64(String data, char[] base64) {
		String output = new String();
		
		int[] input = null;
		try {
			byte[] temp = data.getBytes("ISO-8859-1");
			
			input = new int[temp.length];
			for (int i=0 ; i<input.length ; i++) {
				if (temp[i] < 0) {
					input[i] = 128 - temp[i];
				} else {
					input[i] = temp[i];
				}
			}
		} catch(Exception e) {
			System.err.println("Character set not recognized");
		}
		
		int current = 0;
		
		for (int i=0 ; i<input.length ; i++) {
			
			output += base64[current+(input[i] >> ((i%3+1)*2))];
			current = ((input[i] << ((3-i%3)*2)) & 255) >> 2;
			
			if (i%3 == 2) {				
				output += base64[current];
				current = 0;
			}
		}
				
		if (input.length % 3 != 0) {
			
			output += base64[current];
			
			for (int i=0 ; i<(3-(input.length%3)) ; i++) {
				output += "=";
			}
		}
	
		return output;
	}

	public static String fromBase64(String data) {
		return fromBase64(data, BASE64_ALPHABET);
	}
	
	public static String fromBase64(String data, char[] base64) {

		String texte = new String();
		
		int newInt = 0;
		int complet = 0;
			
		for (int i=0 ; i<data.length() ; i++)
		{	
			int value = getBase64Value(data.charAt(i), base64);
						
			if (value != -1) {
				if (complet == 0) {
					newInt += value << 2;
				}
				if (complet == 2) {
					newInt += value;
					texte += (char)newInt;
					newInt = 0;				
				}
				if (complet > 2) {
					newInt += (value >> (complet - 2)) & 255;
					texte += (char)newInt;
					newInt = (value << (10 - complet)) & 255;
				}
				
				complet += 6;
				complet %= 8;
			}	
		}	
		
		if (newInt != 0) {
			texte += (char)newInt;
		}
		
		return texte;
	}

		
	static int getBase64Value(char c, char[] base64)
	{
		for (int i=0 ; i<64 ; i++)
		{
			if (c == base64[i]) 
			{
				return i;
			}
		}
		
		return -1;
	}
}