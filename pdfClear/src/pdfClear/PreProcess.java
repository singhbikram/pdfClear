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
			Process p = run.exec("/bin/sh -c cd "+ _workFolder +" && pdfimages "+ basename + ".pdf "+ basename );
			p.waitFor();
			 
		// OCR doc  
			p = run.exec("/bin/sh -c for i in " + _workFolder + basename + "-*.pbm ; do tesseract $i `basename \"$i\" .pbm` hocr; done;" );
			p.waitFor();
			pageCount = 1;
		//create pages array
			_pageList = new ArrayList<ArrayList<Word>>(pageCount);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//loop over ImportFile
		for( int i=0; i<pageCount; i++){
			_pageList.add(i, _importer.readHOCR(_workFolder + basename +"-"+ String.format("%03d", i)+".hocr"));
		}
		//return wordList for all pages
		return _pageList;
	}
}