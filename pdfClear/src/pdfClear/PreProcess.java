package pdfClear;

import java.util.List;

public class PreProcess implements PreProcessInterface {
	private String _workFolder=null;
	public Void PreProcess() {
		return null;
	}
	public Void setWorkFolder(String workFolder){	
		_workFolder = workFolder;
		return null;
	}

	@Override
	public List<Word> doDoc(String basename) {
		// Extract Images
		// OCR doc
		//loop over ImportFile
		//return wordList
		return null;
	}

}
