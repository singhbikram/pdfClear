package pdfClear;

import java.util.List;


public interface ImportFileInterace{
	/*Constructor*/
	public Void ImportFileInterace();		
	/**import single page .hocr file
	 * @return list of word objects */
	public List<Object> readHOCR(String pagePath);	
}