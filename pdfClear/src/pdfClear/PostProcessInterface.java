package pdfClear;

import java.util.ArrayList;
import java.util.List;



public interface PostProcessInterface{
	/*
	 * takes wordList and wordLayout, constructs output file, cleans up temporary files
	 */
	public void outputFile(ArrayList<Word> wordList,ArrayList<WordBlock> wordLayout, String baseName);
}