package pdfClear;

import java.util.ArrayList;

public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//work folder hard coded for testing, change this to match your system
		String workFolder="/home/chrisc/Documents/School/java/labs/pdfClear/pdfClear/src/pdfClear/work/";
		String doneFolder="/home/chrisc/Documents/School/java/labs/pdfClear/pdfClear/src/pdfClear/done/";
		ProcessPage ppage = new ProcessPage(workFolder);
		ImportFile fileIn = new ImportFile();
		PostProcess post = new PostProcess(0, workFolder, doneFolder);
		ArrayList<Word> wordList = fileIn.readHOCR(workFolder + "single-000.hocr");
		ArrayList<WordBlock> wordBlocks = ppage.processPage( wordList,"single-000");
		ArrayList<ArrayList<Word>> pages_wordList= new ArrayList<ArrayList<Word>>();
		ArrayList<ArrayList<WordBlock>> pages_wordBlocks= new ArrayList<ArrayList<WordBlock>>();
		pages_wordList.add(wordList);
		pages_wordBlocks.add(wordBlocks);
		post.outputFile(pages_wordList, pages_wordBlocks,"single" );
		System.out.println("test done");
	}

}
