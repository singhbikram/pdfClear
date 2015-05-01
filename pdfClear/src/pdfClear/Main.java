package pdfClear;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
 class Main {
    private static String sourceFolder;
    private static String doneFolder;
    private static String errFolder;
    private static String workFolder;
    private static int numberOfThreads;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {	
        loadConfigFile();
       List baseName=loadBaseName();
       Feeder feeder=new Feeder(baseName,sourceFolder,workFolder,doneFolder,errFolder,numberOfThreads);
       feeder.processList();
    }
    private static void loadConfigFile(){
        Properties config = new Properties();
    	InputStream input = null;
 
    	try {
 
    		String filename = "configFile.properties";
    		input = Main.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}
 
    		//load a properties file from class path, inside static method
    		config.load(input);
 
                //get the property value and print it out
                sourceFolder=config.getProperty("sourceFolder");
    	        doneFolder=config.getProperty("doneFolder");
    	        errFolder=config.getProperty("errFolder");
                workFolder=config.getProperty("workFolder");
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
    private static List loadBaseName(){
         File directory = new File(sourceFolder);
        File[] fList = directory.listFiles();
        List<String> bName=new ArrayList<String>();
        for(File f: fList){
            String name=f.getName();
            if(name.endsWith(".pdf")){
                bName.add(name.substring(0, name.length()-4));
            }
        }
        return bName;
    }
    
}
