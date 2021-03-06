package pdfClear;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
 class Main {
    private static String sourceFolder;
    private static String doneFolder;
    private static String workFolder;
    private static int numberOfThreads;
    private static int outType;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {	
        loadConfigFile();
       String[] baseName=loadBaseName();
       Feeder feeder=new Feeder(sourceFolder,workFolder,doneFolder,numberOfThreads,outType);
       feeder.processList(baseName);
    }
    private static void loadConfigFile(){
        Properties config = new Properties();
        InputStream input = null;
 
        
        
    	try {
 
    		String filename = "configFile.properties";
    		input = Main.class.getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}
    		
 
    		//load a properties file from class path, inside static method
    		config.load(input);
 
                //get the property value and print it out
                sourceFolder=config.getProperty("sourceFolder");
                sourceFolder=sourceFolder.substring(1,sourceFolder.length()-1);
    	        doneFolder=config.getProperty("doneFolder");
    	        doneFolder=doneFolder.substring(1,doneFolder.length()-1);
                workFolder=config.getProperty("workFolder");
                workFolder=workFolder.substring(1,workFolder.length()-1);
                outType=Integer.parseInt(config.getProperty("outType"));
                numberOfThreads=Integer.parseInt(config.getProperty("threads"));
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
 
    }
    private static String[] loadBaseName(){
    	 File directory = new File(sourceFolder);
         File[] fList = directory.listFiles();
        int i=0;
        ArrayList<String> bName=new ArrayList<String>();
        for(File f: fList){
            String name=f.getName();
            if(name.endsWith(".pdf")){
                bName.add(name.substring(0, name.length()-4));
            }
        }
        String[] baseNames = new String[bName.size()];
        for(int z=0;z<bName.size();z++){
        	baseNames[z]=bName.get(z);
        }
        return  baseNames;
    }
    
}
