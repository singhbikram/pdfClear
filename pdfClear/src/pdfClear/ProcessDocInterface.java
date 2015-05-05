package pdfClear;

import java.util.ArrayList;
import java.util.List;



public interface ProcessDocInterface{
	/*
	 * produces structure for single page
	 */
	public ArrayList<WordBlock> processPage(ArrayList<Word> wordList);
}