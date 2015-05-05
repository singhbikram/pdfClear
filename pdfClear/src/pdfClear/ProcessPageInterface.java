package pdfClear;

import java.util.ArrayList;
import java.util.List;



public interface ProcessPageInterface{
	/*
	 * produces structure for single page
	 */
	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList);
}