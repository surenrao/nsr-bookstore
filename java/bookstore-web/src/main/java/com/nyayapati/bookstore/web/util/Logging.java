package com.nyayapati.bookstore.web.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logging 
{
	private static FileWriter outFile=null;
	private static Logging logging=null;
	
	private Logging(String fileName){
		try 
		{
		    outFile = new FileWriter(fileName);			
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static Logging getInstance()
	{
		if(logging!=null)
			return logging;
		else
		{
			return new Logging("");
		}
	}
	
	public static void DEBUG(String text){
		try {
			PrintWriter out = new PrintWriter(outFile);		
			// Write text to file
			out.println(text);			
			out.close();
			out=null;
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
	public static void SHUTDOWN(){
		try {
			outFile.close();
			outFile =null;
			logging =null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
