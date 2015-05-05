package pdfClear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
			Process p = Runtime.getRuntime().exec("pdfimages " + _workFolder + basename + ".pdf " + basename );
			p.waitFor();
		// OCR doc  
			p = Runtime.getRuntime().exec("for i in " + _workFolder + basename + "-*.pbm ; do tesseract $i `basename \"$i\" .pbm` hocr; done;"  );
			p.waitFor();
		// get pageCount  
			p = Runtime.getRuntime().exec("ls " + _workFolder + basename + "-*.hocr | wc -l"  );
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ret = reader.readLine();
			pageCount = Integer.parseInt(ret);
		//create pages array
			_pageList = new ArrayList<ArrayList<Word>>(pageCount);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
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