package pdfClear;

import java.util.ArrayList;

public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//work folder hard coded for testing, change this to match your system
		String workDir="/home/chrisc/Documents/School/java/labs/pdfClear/pdfClear/src/pdfClear/jnk/";
		ProcessPage ppage = new ProcessPage(workDir);
		ImportFile fileIn = new ImportFile();
		ArrayList<Word> wordList = fileIn.readHOCR(workDir + "6953718-010.hocr");
		ArrayList<WordBlock> wordBlocks = ppage.processPage( wordList,"6953718-010");
	}

}
