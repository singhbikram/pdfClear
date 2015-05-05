package pdfClear;

import java.util.ArrayList;
import java.util.List;



public interface PostProcessInterface{
	
	//constructor
	//public PostProcess(int outType, String workFolder){
	
	/*
	 * takes wordList and wordLayout, constructs output file, cleans up temporary files
	 */
	public void outputFile(ArrayList<ArrayList<Word>> wordLists,ArrayList<ArrayList<WordBlock>> wordBlocks, String baseName);
}