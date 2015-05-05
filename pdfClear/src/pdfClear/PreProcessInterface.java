package pdfClear;

import java.util.ArrayList;
import java.util.List;



public interface PreProcessInterface{
	/*Constructor*/
	public Void setWorkFolder(String workFolder);	
	/**import single page .hocr file
	 * @return list of word objects */
	public ArrayList<ArrayList<Word>> doDoc(String basename);	 
}