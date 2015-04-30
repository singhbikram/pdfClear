package pdfClear;

import java.util.List;


public interface ImportFileInterace{
	/**import single page .hocr file
	 * @return list of word objects */
	public List<Object> readHOCR(String pagePath);	
	//CHANGE 7
}