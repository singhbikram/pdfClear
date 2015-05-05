package pdfClear;

import java.util.ArrayList;
import java.util.LinkedList;


public interface WordBlockInterface{
	//put out int array of wordList indexes included in this block
	public int[] getIncluded();
	//accepts ordered and sorted lines and words. 
	public Void setLines(ArrayList<LinkedList<Integer>> lines);
}