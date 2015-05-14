package pdfClear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.InputStream;

public class PreProcess implements PreProcessInterface {
	private String _workFolder;//="/home/chrisc/pdfClear/workFolder/";
	private ImportFile _importer;
	private ArrayList<ArrayList<Word>> _pageList ;
	
	public PreProcess(String workFolder) {
		_importer = new ImportFile();
		_workFolder = workFolder;
	}

	@Override
	public ArrayList<ArrayList<Word>> doDoc(String basename) {
		int pageCount=0;
		try {
		// Extract Images
			Runtime run = Runtime.getRuntime();
			System.out.println("/bin/sh -c 'pdfimages "+ _workFolder + basename + ".pdf "+ basename +"'" );
			Process p = run.exec("/bin/sh -c cd "+ _workFolder +" && pdfimages "+ basename + ".pdf "+ basename );
			p.waitFor();
			 int exitVal = p.exitValue();
			 System.out.println(exitVal);
			 
			 
		/*	List<String> command = new ArrayList<String>();
//			{"/bin/sh", "-c","pdfimages ", _workFolder + basename + ".pdf ", basename};
			// build my command as a list of strings
			command.add("/bin/bash");
			command.add("-c");
			command.add("pdfimages");
			command.add(_workFolder + basename + ".pdf ");
			command.add(basename);



				    ProcessBuilder builder = new ProcessBuilder(command);
				    Map<String, String> environ = builder.environment();

				    final Process process = builder.start();
				   java.io.InputStream is = process.getInputStream();
				    InputStreamReader isr = new InputStreamReader(is);
				    BufferedReader br = new BufferedReader(isr);
				    String line;
				    while ((line = br.readLine()) != null) {
				      System.out.println(line);
				    }

			*/
		// OCR doc  
//			p = run.exec("/bin/bash for i in " + _workFolder + basename + "-*.pbm ; do tesseract $i `basename \"$i\" .pbm` hocr; done;"  );
//			p.waitFor();
		// get pageCount  
			File directory = new File(_workFolder);
	         File[] fList = directory.listFiles();
	         System.out.println(fList[1].getAbsolutePath());
		/*		p = run.exec("/bin/bash ls " + _workFolder + basename + "-*.hocr | wc -l"  );
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ret = reader.readLine();
			pageCount = Integer.parseInt(ret);
		//create pages array
			_pageList = new ArrayList<ArrayList<Word>>(pageCount);*/
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//loop over ImportFile
		for( int i=1; i<=pageCount; i++){
			System.out.println(_workFolder + basename +"-"+ String.format("%05d", i)+".hocr");
			_pageList.set(i-1, _importer.readHOCR(_workFolder + basename +"-"+ String.format("%05d", i)+".hocr"));
		}
		//return wordList for all pages
		return _pageList;
	}
}