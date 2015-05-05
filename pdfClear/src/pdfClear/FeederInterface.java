package pdfClear;

import java.util.ArrayList;
import java.util.List;



public interface FeederInterface{
	//CONSTRUCTOR
	//public Feeder(String sourcefolder, String workfolder, String donefolder,  int threads, int outtype);
	/*
	 * takes array of strings of base names (no path, no extension) and processes them.
	 */			
	public void processList(String[] names);    
}